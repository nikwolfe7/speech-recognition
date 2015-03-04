package mlsp.cs.cmu.edu.filters;

import org.apache.commons.math3.transform.DctNormalization;
import org.apache.commons.math3.transform.FastCosineTransformer;

public class DiscreteCosineTransform implements FrameFilter {

  private FastCosineTransformer dct = new FastCosineTransformer(DctNormalization.STANDARD_DCT_I);
  
  @Override
  public double[] doFilter(double[] frame) {
    // TODO Auto-generated method stub
    return frame;
  }

  @Override
  public String getName() {
    return "Inverse Discrete Fourier Transform";
  }

}
