package mlsp.cs.cmu.edu.filters;

public class MelConverter {
  
  public static Double getMelFrequency(Double frequency) {
    return 1125 * Math.log(1 + (frequency / 700));
  }

  public static Double getFrequencyFromMel(Double mel) {
    return 700 * (Math.exp(mel / 1125) - 1);
  }

}
