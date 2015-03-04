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
  public double[] doFilter(double[] frame) {
    // assumes we aren't changing the framesize throughout
    // the use of this object
    if(filterBank == null) {
      // can be changed for different sample rates... but Triangular Filter class will 
      // have to change as well. 
      int max = AudioConstants.MELFREQUENCY_MAX_16KHZ.getValue();
      int min = AudioConstants.MELFREQUENCY_MIN_16KHZ.getValue();
      this.filterBank = getFilterBank(max, min, frame);
    }
    double[] melSpectrum = new double[filterBank.length];
    for(int i = 0; i < melSpectrum.length; i++) {
      melSpectrum[i] = filterBank[i].getEnergy(frame);
    }
    return melSpectrum;
  }
  
  private TriangularFilter[] getFilterBank(int maxFrequency, int minFrequency, double[] frame) {
    double maxMel = MelConverter.getMelFrequency((double) maxFrequency);
    double minMel = MelConverter.getMelFrequency((double) minFrequency);
    double melScale = maxMel - minMel;
    double[] melFrequencies = new double[AudioConstants.MELFREQUENCY_BINS.getValue() + 2];
    double melGradient = melScale / (AudioConstants.MELFREQUENCY_BINS.getValue() + 1);
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
    private int sampleRate;
    private int startIndex;
    private int peakIndex;
    private int endIndex;
    private double gradient;

    public TriangularFilter(double melStart, double melPeak, double melEnd, double[] frame) {
      this.sampleRate = AudioConstants.KHZ16.getValue();
      this.gradient = (sampleRate/2.0)/frame.length;
      // convert to FFT bins...
      this.startIndex = getFFTBinFromFrequency(MelConverter.getFrequencyFromMel(melStart));
      this.peakIndex = getFFTBinFromFrequency(MelConverter.getFrequencyFromMel(melPeak));
      this.endIndex = getFFTBinFromFrequency(MelConverter.getFrequencyFromMel(melEnd));
    }
    
    private int getFFTBinFromFrequency(double frequency) {
      int testIndex = 0;
      double testFrequency = 0.0;
      while(Math.abs(testFrequency - frequency) >= gradient/2) {
        testIndex++;
        testFrequency = testIndex * gradient;
      }
      return testIndex;
    }
    
    private double getFreqFromIndex(int index) {
      double frequency = index * gradient;
      return frequency;
    }
    
    // assuming a height of 1 for all triangles...
    public double getEnergy(double[] frame) {
      double triangleHeight = 2/(getFreqFromIndex(endIndex) - getFreqFromIndex(startIndex));
      double slope1 = triangleHeight/(getFreqFromIndex(peakIndex) - getFreqFromIndex(startIndex));
      double slope2 = triangleHeight/(getFreqFromIndex(peakIndex) - getFreqFromIndex(endIndex));
      double integral = 0.0;
      for(int i = startIndex; i <= peakIndex; i++) {
        double height = (getFreqFromIndex(i) - getFreqFromIndex(startIndex)) * slope1;
        double energy = (height * frame[i]);
        integral += energy;
      }
      for(int i = peakIndex + 1; i <= endIndex; i++) {
        double height = (getFreqFromIndex(i) - getFreqFromIndex(endIndex)) * slope2;
        double energy = (height * frame[i]);
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
