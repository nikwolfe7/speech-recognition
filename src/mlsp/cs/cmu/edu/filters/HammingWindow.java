package mlsp.cs.cmu.edu.filters;

import mlsp.cs.cmu.edu.features.MFCCFeatureVectorContainer;

public class HammingWindow extends FrameFilter {

  @Override
  protected double[] doFilterImplementation(double[] frame) {
    for(int n = 0; n < frame.length; n++) {
      double window = 0.54 - 0.46*Math.cos((2 * Math.PI * n)/frame.length); 
      frame[n] = frame[n] * window;
    }
    return frame;
  }

  @Override
  public String getName() {
    return "Hamming Window";
  }

  @Override
  public void visit(MFCCFeatureVectorContainer container) {
    // do nothing...
  }

}
