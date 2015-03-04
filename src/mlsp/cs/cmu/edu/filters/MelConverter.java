package mlsp.cs.cmu.edu.filters;

public class MelConverter {
  
  public static double getMelFrequency(double frequency) {
    return 1125 * Math.log(1 + (frequency / 700));
  }

  public static double getFrequencyFromMel(double mel) {
    return 700 * (Math.exp(mel / 1125) - 1);
  }

}
