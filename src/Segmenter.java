import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;

/**
 * This class uses the energy waveform to allow different strategies to do endpointing
 * 
 * @author nwolfe
 *
 */
public abstract class Segmenter extends Thread implements Filterable {

  private FrameSequence frameSequence;

  private WAVWriter wavWriter;

  protected AudioFormat audioFormat;

  protected Integer sampleRate;

  protected Integer sampleIndex;

  protected Integer frameSize;

  protected Integer frameIndex;

  protected SegmentStrategy segmentStrategy;

  protected ArrayList<Double> decibelWaveform;

  protected ArrayList<Short> waveform;

  protected ArrayList<Short[]> waveframes;
  
  protected ArrayList<FrameFilter> filters;

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
    this.filters = new ArrayList<FrameFilter>();
    this.segmentStrategy = strategy;
  }

  @Override
  public void attachFilter(FrameFilter frameFilter) {
    filters.add(frameFilter);
  }
  
  @Override
  public void clearFilters() {
    filters.clear();
  }
  
  /* Expects a continuous stream in general... */
  public void run() {
    while (!Thread.currentThread().isInterrupted()) {
      try {
        Short[] frame = new Short[frameSize];
        for (int i = 0; i < frameSize; i++) {
          sampleIndex++;
          frame[i] = frameSequence.getFrame();
          waveform.add(frame[i]);
        }
        /* run the attached filters */
        for(FrameFilter filter : filters) {
          frame = filter.doFilter(frame);
        }
        frameIndex++;
        waveframes.add(frame);
        Double energy = getFrameDecibelLevel(frame);
        decibelWaveform.add(energy);
        classifyAndSegmentFrame(energy, segmentStrategy.isSpeech(energy));
        /*
         * Not sure what else to do here...
         * 
         */
        System.out.println(Math.round(energy));
      } catch (InterruptedException e) {
        System.out.println("Segmenter interrupted! Terminating...");
        Thread.currentThread().interrupt();
      }
    }
  }
  
  public void stopSegmenting() {
    this.interrupt();
  }

  protected Double getFrameDecibelLevel(Short[] frame) {
    Double sigma = 0.0;
    for (Short s : frame) {
      sigma += (s * s);
    }
    return 10 * Math.log10(sigma) - 30;
  }
  
  /**
   * Template method for the algorithm that defines how to do segmentation
   * Takes the energy of a given frame and the result of the SegmentStrategy
   * 
   * @param energy
   * @param isSpeech
   */
  protected abstract void classifyAndSegmentFrame(Double energy, boolean isSpeech); 
  
}
