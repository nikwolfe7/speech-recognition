package mlsp.cs.cmu.edu.segmentation;

public class AdaptiveEndpointing implements SegmentStrategy {

  private Integer frameCount = 0;

  private Double totalEnergy = 0.0;

  private Double BACKGROUND_ENERGY = 0.0;
  
  private Double onsetThreshold = 15.0;

  private Double forgetFactor = 1.5;

  private Double adjustment = 0.05;
  
  private Double level = 0.0;
  
  private boolean inSpeech = false;

  @Override
  public boolean isSpeech(Double energy) {
    if(frameCount == 0) { // first frame
      level = energy;
    }
    frameCount++;
    if (frameCount >= 25) { // only start after 25 frames have elapsed...
      inSpeech = false;
      level = ((level * forgetFactor) + energy) / (forgetFactor+1);
      if(energy < BACKGROUND_ENERGY) {
        BACKGROUND_ENERGY = energy;
      } else {
        BACKGROUND_ENERGY += (energy - BACKGROUND_ENERGY) * adjustment;
      }
      if(level < BACKGROUND_ENERGY) {
        level = BACKGROUND_ENERGY;
      }
      if((level - BACKGROUND_ENERGY) > onsetThreshold) {
        inSpeech = true;
      }
    } else { // get average energy
      totalEnergy += energy;
      BACKGROUND_ENERGY = totalEnergy / frameCount;
    }
    // System.out.println("Energy: " + energy + " Average Energy: " + BACKGROUND_ENERGY);
    return inSpeech;
  }

}
