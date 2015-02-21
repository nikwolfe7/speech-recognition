package mlsp.cs.cmu.edu.sampling;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import mlsp.cs.cmu.edu.audio.RecordContext;
import mlsp.cs.cmu.edu.microphone.Microphone;
/**
 * This class is to interface with a hardware device and poll data
 * from it as it becomes available, while avoiding overflows. This
 * is mostly to ensure that we can get an AudioInputStream from a
 * Mixer attached to a microphone... 
 * 
 * @author nwolfe
 *
 */
public class Recorder extends Thread implements Sampleable {

  private ByteArrayOutputStream audioOutput;

  private boolean stopCapture = false;

  private TargetDataLine targetDataLine;

  private AudioFormat audioFormat;

  private byte[] audioBuffer;

  public Recorder(Microphone mic, Integer buffersize) {
    RecordContext.registerRecorder(this);
    this.audioOutput = new ByteArrayOutputStream();
    this.targetDataLine = mic.getOpenMicrophone();
    this.audioFormat = targetDataLine.getFormat();
    this.audioBuffer = new byte[buffersize];
    if (!targetDataLine.isOpen()) {
      try {
        targetDataLine.open();
      } catch (LineUnavailableException e) {
        System.out.println("Couldn't open Microphone!");
        e.printStackTrace();
      }
    } else if (targetDataLine.isActive()) {
      System.out.println("Microphone opened... Recording audio!");
    } else {
      System.out.println("Microphone opened... Not yet recording audio...");
    }
  }

  public void stopRecording() {
    stopCapture = true;
    synchronized (audioOutput) {
      // wake up sleeping threads to terminate...
      audioOutput.notifyAll();
    }
  }

  public void run() {
    stopCapture = false;
    System.out.println("Recording started!");
    while(!stopCapture && !Thread.currentThread().isInterrupted()) {
      synchronized (audioOutput) {
        Integer count = targetDataLine.read(audioBuffer, 0, audioBuffer.length);
        if (count > 0) {
          audioOutput.write(audioBuffer, 0, count);
          audioOutput.notify();
          try {
            audioOutput.wait();
          } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
          }
        }
      }
    }
    // We're done...
    try {
      audioOutput.close();
      System.out.println("Microphone closed.");
    } catch (IOException e) {
      System.out.println("Unable to close audio ouptut!");
      e.printStackTrace();
    }
  }

  public AudioInputStream sample() throws IOException {
    byte[] audioSample = new byte[] { 0 };
    synchronized (audioOutput) {
      audioSample = audioOutput.toByteArray();
      audioOutput.reset();
      audioOutput.notify();
      try {
        audioOutput.wait();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
    InputStream inStream = new ByteArrayInputStream(audioSample);
    AudioInputStream audioInputStream = new AudioInputStream(inStream, audioFormat,
            audioSample.length / audioFormat.getFrameSize());
    return audioInputStream;
  }

}
