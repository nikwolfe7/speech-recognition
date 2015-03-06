package mlsp.cs.cmu.edu.features;

import org.apache.commons.math3.transform.DctNormalization;
import org.apache.commons.math3.transform.FastCosineTransformer;
import org.apache.commons.math3.transform.TransformType;

import mlsp.cs.cmu.edu.filters.FrameFilter;

public class InverseDiscreteCosineTransform extends FrameFilter {
  
  FastCosineTransformer dct = new FastCosineTransformer(DctNormalization.ORTHOGONAL_DCT_I);
  
  @Override
  protected double[] doFilterImplementation(double[] frame) {
    frame = getPowerOfTwoArrayPlusOne(frame);
    frame = dct.transform(frame, TransformType.INVERSE);
    return frame;
  }

  private double[] getPowerOfTwoArrayPlusOne(double[] frame) {
    int padding = 2;
    while (padding < frame.length) {
      padding = padding * 2;
    }
    double[] signal = new double[padding + 1];
    System.arraycopy(frame, 0, signal, 0, frame.length);
    return signal;
  }

  @Override
  public void visit(MFCCFeatureVectorContainer container) {
    container.addRecoveredLogMelSpectrumFeatureFrame(getLastProcessedFrame());
  }

  @Override
  public String getName() {
    return "Inverse Discrete Cosine Transform";
  }
}
