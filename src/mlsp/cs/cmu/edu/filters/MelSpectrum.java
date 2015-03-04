package mlsp.cs.cmu.edu.filters;

import mlsp.cs.cmu.edu.audio.AudioConstants;

/**
 * Assumes 16kHz sample rate...
 * 
 * @author nwolfe
 *
 */
public class MelSpectrum implements FrameFilter {
  
  private TriangularFilter[] filterBank = null;

  @Override
  public Double[] doFilter(Double[] frame) {
    // assumes we aren't changing the framesize throughout
    // the use of this object
    if(filterBank == null) {
      // can be changed for different sample rates... but Triangular Filter class will 
      // have to change as well. 
      Integer max = AudioConstants.MELFREQUENCY_MAX_16KHZ.getValue();
      Integer min = AudioConstants.MELFREQUENCY_MIN_16KHZ.getValue();
      this.filterBank = getFilterBank(max, min, frame);
    }
    Double[] melSpectrum = new Double[filterBank.length];
    for(int i = 0; i < melSpectrum.length; i++) {
      melSpectrum[i] = filterBank[i].getEnergy(frame);
    }
    return melSpectrum;
  }
  
  private TriangularFilter[] getFilterBank(Integer maxFrequency, Integer minFrequency, Double[] frame) {
    Double maxMel = MelConverter.getMelFrequency(maxFrequency.doubleValue());
    Double minMel = MelConverter.getMelFrequency(minFrequency.doubleValue());
    Double melScale = maxMel - minMel;
    Double[] melFrequencies = new Double[AudioConstants.MELFREQUENCY_BINS.getValue() + 2];
    Double melGradient = melScale / (AudioConstants.MELFREQUENCY_BINS.getValue() + 1);
    for (int i = 0; i < melFrequencies.length; i++) {
      melFrequencies[i] = (i) * melGradient + minMel;
    }
    TriangularFilter[] fBank = new TriangularFilter[AudioConstants.MELFREQUENCY_BINS.getValue()];
    for(int i = 1; i < melFrequencies.length-1; i++) {
      fBank[i-1] = new TriangularFilter(melFrequencies[i-1], melFrequencies[i], melFrequencies[i+1], frame); 
    }
    return fBank;
  }

  // assumes 16 khz sample rate
  private class TriangularFilter {
    private Integer sampleRate;
    private Integer startIndex;
    private Integer peakIndex;
    private Integer endIndex;
    private Double gradient;

    public TriangularFilter(Double melStart, Double melPeak, Double melEnd, Double[] frame) {
      this.sampleRate = AudioConstants.KHZ16.getValue();
      this.gradient = (sampleRate/2.0)/frame.length;
      // convert to FFT bins...
      this.startIndex = getFFTBinFromFrequency(MelConverter.getFrequencyFromMel(melStart));
      this.peakIndex = getFFTBinFromFrequency(MelConverter.getFrequencyFromMel(melPeak));
      this.endIndex = getFFTBinFromFrequency(MelConverter.getFrequencyFromMel(melEnd));
    }
    
    private Integer getFFTBinFromFrequency(Double frequency) {
      Integer testIndex = 0;
      Double testFrequency = 0.0;
      while(Math.abs(testFrequency - frequency) >= gradient/2) {
        testIndex++;
        testFrequency = testIndex * gradient;
      }
      return testIndex;
    }
    
    private Double getFreqFromIndex(Integer index) {
      Double frequency = index * gradient;
      return frequency;
    }
    
    // assuming a height of 1 for all triangles...
    public Double getEnergy(Double[] frame) {
      Double triangleHeight = 2/(getFreqFromIndex(endIndex) - getFreqFromIndex(startIndex));
      Double slope1 = triangleHeight/(getFreqFromIndex(peakIndex) - getFreqFromIndex(startIndex));
      Double slope2 = triangleHeight/(getFreqFromIndex(peakIndex) - getFreqFromIndex(endIndex));
      Double integral = 0.0;
      for(int i = startIndex; i <= peakIndex; i++) {
        Double height = (getFreqFromIndex(i) - getFreqFromIndex(startIndex)) * slope1;
        Double energy = (height * frame[i]);
        integral += energy;
      }
      for(int i = peakIndex + 1; i <= endIndex; i++) {
        Double height = (getFreqFromIndex(i) - getFreqFromIndex(endIndex)) * slope2;
        Double energy = (height * frame[i]);
        integral += energy;
      }
      return integral;
    }
    
  }

  @Override
  public String getName() {
    return "Mel Spectrum Filter Bank";
  }

}
