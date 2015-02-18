
public class EnergyBasedEndpointing implements SegmentStrategy {

  private final Double threshold = 47.0;
  
  @Override
  public boolean isSpeech(Double energy) {
    return energy >= threshold; 
  }

}
