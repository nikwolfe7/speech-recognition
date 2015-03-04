package mlsp.cs.cmu.edu.filters;

import mlsp.cs.cmu.edu.features.MFCCFeatureVectorContainer;

public class LogMelSpectrum extends FrameFilter {

  @Override
  protected double[] doFilterImplementation(double[] frame) {
    for(int i = 0; i < frame.length; i++) {
      frame[i] = Math.log(frame[i]);
    }
    return frame;
  }

  @Override
  public String getName() {
    return "Log Mel Spectrum";
  }

  @Override
  public void visit(MFCCFeatureVectorContainer container) {
    container.addLogMelSpectrumFeatureFrame(getLastProcessedFrame());
  }

}
