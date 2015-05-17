package mlsp.cs.cmu.edu.segmentation;

public class EnergyBasedEndpointing implements SegmentStrategy {

  private final Double threshold = 77.0;
  
  @Override
  public boolean isSpeech(Double energy) {
    return energy >= threshold; 
  }

}
