import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;

public class Segmenter extends Thread implements FrameExtractor {

  private FrameSequence frameSequence;

  private WAVWriter wavWriter;

  private AudioFormat audioFormat;

  private Integer sampleRate;

  private Integer sampleindex;
  
  private Integer frameSize;
  
  private Integer frameindex;
  
  ArrayList<Short> waveform;

  ArrayList<Short[]> waveframes;

  /* We use 10 ms frames */
  public Segmenter(FrameSequence fs, AudioFormatFactory formatFactory) {
    this.frameSequence = fs;
    this.audioFormat = formatFactory.getAudioFormat();
    this.sampleRate = (int) audioFormat.getSampleRate();
    this.frameSize = sampleRate / 100; /* This is 10ms */
    this.sampleindex = 0;
    this.frameindex = 0;
    this.wavWriter = new WAVWriter();
    this.waveform = new ArrayList<Short>();
    this.waveframes = new ArrayList<Short[]>();
  }

  /* Expects a continuous stream in general... */
  public void run() {
    while (!Thread.currentThread().isInterrupted()) {
      try {
        Short[] frame = new Short[frameSize];
        for (int i = 0; i < frameSize; i++) {
          frame[i] = frameSequence.getFrame();
          waveform.add(frame[i]);
        }
        waveframes.add(frame);
        /*
         * 
         * 
         */
      } catch (InterruptedException e) {
        System.out.println("Segmenter interrupted! Terminating...");
        Thread.currentThread().interrupt();
      }
    }
  }
  
  public void stopSegmenting() {
    this.interrupt();
  }
}
