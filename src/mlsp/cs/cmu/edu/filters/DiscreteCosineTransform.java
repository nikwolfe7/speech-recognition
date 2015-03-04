package mlsp.cs.cmu.edu.filters;

import org.apache.commons.math3.transform.DctNormalization;
import org.apache.commons.math3.transform.FastCosineTransformer;
import org.apache.commons.math3.transform.TransformType;

public class DiscreteCosineTransform implements FrameFilter {

  private FastCosineTransformer dct = new FastCosineTransformer(DctNormalization.STANDARD_DCT_I);
  
  @Override
  public double[] doFilter(double[] frame) {
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
  public String getName() {
    return "Inverse Discrete Fourier Transform";
  }

}
