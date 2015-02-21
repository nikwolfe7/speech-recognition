package mlsp.cs.cmu.edu.segmentation;

import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;

import mlsp.cs.cmu.edu.audio.AudioFormatFactory;
import mlsp.cs.cmu.edu.audio.RecordContext;
import mlsp.cs.cmu.edu.filters.Filterable;
import mlsp.cs.cmu.edu.filters.FrameFilter;
import mlsp.cs.cmu.edu.sampling.FrameSequence;
import mslp.cs.cmu.edu.wavutils.WAVWriter;

/**
 * This class uses the energy waveform to allow different strategies to do endpointing
 * 
 * @author nwolfe
 *
 */
public abstract class Segmenter extends Thread implements Filterable {

  private FrameSequence frameSequence;

  protected final Integer sampleRate;

  protected final Integer frameSize;

  private Integer sampleIndex;

  private Integer frameIndex;

  protected AudioFormat audioFormat;

  protected SegmentStrategy segmentStrategy;

  protected ArrayList<Short> waveform;

  protected ArrayList<Double> decibelWaveform;

  protected ArrayList<Double[]> waveframes;

  protected ArrayList<FrameFilter> filters;

  /* We use 10 ms frames */
  public Segmenter(FrameSequence fs, AudioFormatFactory formatFactory, SegmentStrategy strategy) {
    RecordContext.registerSegmenter(this);
    this.frameSequence = fs;
    this.audioFormat = formatFactory.getAudioFormat();
    this.sampleRate = (int) audioFormat.getSampleRate();
    this.frameSize = sampleRate / 100; /* This is 10ms */
    this.sampleIndex = 0;
    this.frameIndex = 0;
    this.waveform = new ArrayList<Short>();
    this.waveframes = new ArrayList<Double[]>();
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
        Double[] frame = new Double[frameSize];
        for (int i = 0; i < frameSize; i++) {
          Short val = frameSequence.getFrame();
          waveform.add(val);
          frame[i] = val.doubleValue();
          sampleIndex++;
        }
        /* run the attached filters */
        for (FrameFilter filter : filters) {
          frame = filter.doFilter(frame);
        }
        frameIndex++;
        waveframes.add(frame);
        Double energy = getFrameDecibelLevel(frame);
        decibelWaveform.add(energy);
        classifyAndSegmentFrame(energy, segmentStrategy.isSpeech(energy));
        /*
         * Not sure what else to do here...
         */
        printEnergies(energy);
      } catch (InterruptedException e) {
        System.out.println("Segmenter interrupted! Terminating...");
        Thread.currentThread().interrupt();
      }
    }
    System.out.println("Now writing wav data to file...");
  }
  
  private void printEnergies(Double energy) {
    StringBuilder sb = new StringBuilder(Math.round(energy) + "\t| ");
    while(energy > 25) { // hack for display purposes...
      energy += -1;
      sb.append("]]");
    }
    System.out.println(sb);
  }

  public void stopSegmenting() {
    this.interrupt();
  }

  protected Double getFrameDecibelLevel(Double[] frame) {
    Double sigma = 0.0;
    for (Double s : frame) {
      sigma += (s * s);
    }
    return 10 * Math.log10(sigma);
  }

  protected Integer getSampleIndex() {
    return sampleIndex;
  }

  protected Integer getFrameIndex() {
    return frameIndex;
  }

  /**
   * Template method for the algorithm that defines how to do segmentation Takes the energy of a
   * given frame and the result of the SegmentStrategy
   * 
   * @param energy
   * @param isSpeech
   */
  protected abstract void classifyAndSegmentFrame(Double energy, boolean isSpeech);

}
