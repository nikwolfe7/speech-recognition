package mlsp.cs.cmu.edu.filters;

import mlsp.cs.cmu.edu.audio.AudioConstants;
import mlsp.cs.cmu.edu.features.MFCCFeatureVectorContainer;

public class MFCC extends FrameFilter {

  @Override
  protected double[] doFilterImplementation(double[] frame) {
    // Grab top 12
    double[] MFCC = new double[AudioConstants.MFCC_SIZE.getValue()];
    System.arraycopy(frame, 0, MFCC, 0, MFCC.length);
    return MFCC;
  }

  @Override
  public String getName() {
    return "Mel Frequency Cepstral Coefficients";
  }

  @Override
  public void visit(MFCCFeatureVectorContainer container) {
    //container.addMFCCFeatureFrame(getLastProcessedFrame());
  }

}
