package mlsp.cs.cmu.edu.filters;

import mlsp.cs.cmu.edu.audio.AudioConstants;

/**
 * Assumes 16kHz sample rate...
 * 
 * @author nwolfe
 *
 */
public class MelFilterBank implements FrameFilter {
  
  private TriangularFilter[] filterBank = null;

  @Override
  public Double[] doFilter(Double[] frame) {
    // assumes we aren't changing the framesize throughout
    // the use of this object
    if(filterBank == null) {
      // can be changed for different sample rates... but Triangular Filter class will 
      // have to change as well. 
      this.filterBank = getFilterBank(AudioConstants.MELFREQUENCY_MAX_16KHZ.getValue());
    }
    return frame;
  }
  
  private TriangularFilter[] getFilterBank(Integer maxFrequency) {
    Double melScale = MelConverter.getMelFrequency(maxFrequency.doubleValue());
    Double melGradient = melScale / AudioConstants.MELFREQUENCY_BINS.getValue();
    Double[] melFrequencies = new Double[AudioConstants.MELFREQUENCY_BINS.getValue()+2]; // 42
    for (int i = 0; i < melFrequencies.length; i++) {
      melFrequencies[i] = (i) * melGradient;
    }
    TriangularFilter[] fBank = new TriangularFilter[AudioConstants.MELFREQUENCY_BINS.getValue()];
    for(int i = 1; i < melFrequencies.length-1; i++) {
      fBank[i-1] = new TriangularFilter(melFrequencies[i-1], melFrequencies[i], melFrequencies[i+1]); 
    }
    return fBank;
  }

  // assumes 16 khz sample rate
  private class TriangularFilter {
    private Double sampleRate = AudioConstants.KHZ16.getValue().doubleValue();
    private Double start;
    private Double peak;
    private Double end;

    public TriangularFilter(Double melStart, Double melPeak, Double melEnd) {
      this.start = MelConverter.getFrequencyFromMel(melStart);
      this.peak = MelConverter.getFrequencyFromMel(melPeak);
      this.end = MelConverter.getFrequencyFromMel(melEnd);
      // convert to FFT bins...
    }
    
    public Double getEnergy(Double[] frame) {
      return 0.0;
    }
    
  }

  @Override
  public String getName() {
    return "Mel Filter Bank";
  }

}
