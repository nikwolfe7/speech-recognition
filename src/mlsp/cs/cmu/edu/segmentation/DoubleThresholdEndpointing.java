package mlsp.cs.cmu.edu.segmentation;

/* Tuned for the ATR Usb microphone ... */
public class DoubleThresholdEndpointing implements SegmentStrategy {

  private int frameCount = 0;

  private Double totalEnergy = 0.0;

  private Double BACKGROUND_ENERGY = 0.0;

  private Double threshold = 1.3;

  private Double belowThreshold = 0.8;

  private boolean inSpeech = false;

  @Override
  public boolean isSpeech(Double energy) {
    frameCount++;
    totalEnergy += energy;
    BACKGROUND_ENERGY = totalEnergy / frameCount;
    if (frameCount >= 50) { // only start after 10 frames have elapsed...
      Double onsetThreshold = (BACKGROUND_ENERGY * threshold);
      Double offsetThreshold = (BACKGROUND_ENERGY * belowThreshold);
      if (inSpeech) {
        if (energy <= offsetThreshold) {
          inSpeech = false;
        }
      } else { // not in speech
        if (energy >= onsetThreshold) {
          inSpeech = true;
        }
      }
    }
    // System.out.println("Energy: " + energy + " Average Energy: " + BACKGROUND_ENERGY);
    return inSpeech;
  }
}
