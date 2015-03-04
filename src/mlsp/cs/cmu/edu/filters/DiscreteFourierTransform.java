package mlsp.cs.cmu.edu.filters;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

public class DiscreteFourierTransform implements FrameFilter {

  FastFourierTransformer fft = new FastFourierTransformer(DftNormalization.STANDARD);

  /**
   * returns the FFT power spectrum
   */
  @Override
  public double[] doFilter(double[] frame) {
    Integer padding = 2;
    while (padding < frame.length) {
      padding = padding * 2;
    }
    double[] signal = new double[padding];
    System.arraycopy(frame, 0, signal, 0, frame.length);
    Complex[] complex = fft.transform(signal, TransformType.FORWARD);
    frame = new double[complex.length/2+1];
    for (int i = 0; i < frame.length; i++) {
      // compute power spectrum
      frame[i] = complex[i].getReal() * complex[i].getReal() + complex[i].getImaginary()
              * complex[i].getImaginary();
    }
    return frame;
  }

  @Override
  public String getName() {
    return "Discrete Fourier Transform";
  }

}
