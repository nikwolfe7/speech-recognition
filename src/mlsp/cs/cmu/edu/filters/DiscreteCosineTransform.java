package mlsp.cs.cmu.edu.filters;

import mlsp.cs.cmu.edu.audio.AudioConstants;
import mlsp.cs.cmu.edu.features.MFCCFeatureVectorContainer;

public class DiscreteCosineTransform extends FrameFilter {

  @Override
  protected double[] doFilterImplementation(double[] frame) {
    double[] dct = new double[AudioConstants.DCT_SIZE.getValue()];
    for(int i  = 1; i <= dct.length; i++) {
      for(int j = 1; j <= frame.length; j++) {
        dct[i - 1] += frame[j - 1] * Math.cos(Math.PI * (i - 1) / frame.length * (j - 0.5));
      }
    }
    return dct;
  }
  
  /*
  private double[] getPowerOfTwoArrayPlusOne(double[] frame) {
    int padding = 2;
    while (padding < frame.length) {
      padding = padding * 2;
    }
    double[] signal = new double[padding + 1];
    System.arraycopy(frame, 0, signal, 0, frame.length);
    return signal;
  }
  
  private double[] getPowerOfTwoArrayPlusOne(double[] frame) {
    double[] newFrame = new double[AudioConstants.DCT_SIZE.getValue() + 1]; 
    System.arraycopy(frame, 0, newFrame, 0, frame.length);
    return newFrame;
  }
  */

  @Override
  public String getName() {
    return "Discrete Cosine Transform";
  }

  @Override
  public void visit(MFCCFeatureVectorContainer container) {
    // do nothing...
  }

}
