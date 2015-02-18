import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;

/**
 * This class uses the energy waveform to allow different strategies to
 * do endpointing
 * @author nwolfe
 *
 */
public class Segmenter extends Thread implements FrameExtractor {

  private FrameSequence frameSequence;

  private WAVWriter wavWriter;

  private AudioFormat audioFormat;

  private Integer sampleRate;

  private Integer sampleIndex;
  
  private Integer frameSize;
  
  private Integer frameIndex;
  
  private SegmentStrategy segmentStrategy;
  
  private ArrayList<Double> decibelWaveform;
  
  private ArrayList<Short> waveform;

  private ArrayList<Short[]> waveframes;

  /* We use 10 ms frames */
  public Segmenter(FrameSequence fs, AudioFormatFactory formatFactory, SegmentStrategy strategy) {
    this.frameSequence = fs;
    this.audioFormat = formatFactory.getAudioFormat();
    this.sampleRate = (int) audioFormat.getSampleRate();
    this.frameSize = sampleRate / 100; /* This is 10ms */
    this.sampleIndex = 0;
    this.frameIndex = 0;
    this.wavWriter = new WAVWriter();
    this.waveform = new ArrayList<Short>();
    this.waveframes = new ArrayList<Short[]>();
    this.decibelWaveform = new ArrayList<Double>();
    this.segmentStrategy = strategy;
  }

  /* Expects a continuous stream in general... */
  public void run() {
    while (!Thread.currentThread().isInterrupted()) {
      try {
        Short[] frame = new Short[frameSize];
        for (int i = 0; i < frameSize; i++) {
          frame[i] = frameSequence.getFrame();
          waveform.add(frame[i]);
          sampleIndex++;
        }
        waveframes.add(frame);
        Double energy = getFrameDecibelLevel(frame);
        System.out.println(Math.round(energy));
        decibelWaveform.add(energy);
        frameIndex++;
        classifyAndSegmentFrame(energy);
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
  
  private void classifyAndSegmentFrame(Double energy) {
    boolean isSpeech = segmentStrategy.isSpeech(energy);
    if(isSpeech) {
      System.out.println("THIS IS SPEECH!!!!");
    } else {
      //System.out.println("");
    }
    
  }

  private Double getFrameDecibelLevel(Short[] frame) {
    Double sigma = 0.0;
    for(Short s : frame) {
      sigma += (s * s);
    }
    return 10 * Math.log10(sigma) - 30;
  }

  public void stopSegmenting() {
    this.interrupt();
  }
}
