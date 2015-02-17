import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;


public class Recorder extends Thread {
	
	private ByteArrayOutputStream audioOutput;
	private boolean stopCapture = false;
	private TargetDataLine targetDataLine;
	private AudioFormat audioFormat;
	private byte[] audioBuffer;
	
	public Recorder(Microphone mic) {
	  this.audioOutput = new ByteArrayOutputStream();
		this.targetDataLine = mic.getOpenMicrophone();
		this.audioFormat = targetDataLine.getFormat();
		this.audioBuffer = new byte[buffersize];
		if(!targetDataLine.isOpen()) {
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
	}
	
	public void run() {
	  stopCapture = false;
		System.out.println("Recording started!");
		while (!this.stopCapture && !Thread.currentThread().isInterrupted()) {
			Integer count = targetDataLine.read(audioBuffer, 0, audioBuffer.length);
			if(count > 0) {
			  synchronized (audioOutput) {
  				audioOutput.write(audioBuffer, 0, count);
  				System.out.println("Audio output: " + audioOutput.size());
  				/*for(byte b : audioBuffer) {
  				  System.out.println("Byte: " + b);
  				}*/
  				try {
  				  notifyAll();
            audioOutput.wait();
          } catch (InterruptedException e) {
            // move on to check the loop and exit gracefully. 
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
    byte[] audioSample = new byte[] {0};
    synchronized (audioOutput) {
      audioSample = audioOutput.toByteArray();
      audioOutput.reset();
    }
    InputStream inStream = new ByteArrayInputStream(audioSample);
    AudioInputStream audioInputStream = new AudioInputStream(inStream, audioFormat, audioSample.length/audioFormat.getFrameSize());
    return audioInputStream;
	}
	
}
