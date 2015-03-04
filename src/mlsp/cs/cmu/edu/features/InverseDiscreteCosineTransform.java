package mlsp.cs.cmu.edu.features;

import mlsp.cs.cmu.edu.filters.FrameFilter;

public class InverseDiscreteCosineTransform extends FrameFilter {
  
  @Override
  public void visit(MFCCFeatureVectorContainer container) {
    container.addRecoveredLogMelSpectrumFeatureFrame(getLastProcessedFrame());
  }

  @Override
  public String getName() {
    return "Inverse Discrete Cosine Transform";
  }

  @Override
  protected double[] doFilterImplementation(double[] frame) {
    return frame;
  }

}
