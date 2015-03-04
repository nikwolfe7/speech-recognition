package mlsp.cs.cmu.edu.filters;

import mlsp.cs.cmu.edu.features.MFCCFeatureVectorContainer;

public class RemoveDCOffset extends FrameFilter {

  @Override
  public String getName() {
    return "DC Offset Filter";
  }

  @Override
  public void visit(MFCCFeatureVectorContainer container) {
    // TODO Auto-generated method stub

  }

  @Override
  protected double[] doFilterImplementation(double[] frame) {
    double sum = 0.0;
    for (int i = 0; i < frame.length; i++) {
      sum += frame[i];
    }
    double avg = sum / frame.length;
    for (int i = 0; i < frame.length; i++) {
      frame[i] = frame[i] - avg;
    }
    return frame;
  }

}
