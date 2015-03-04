package mlsp.cs.cmu.edu.filters;

public class HammingWindow implements FrameFilter {

  @Override
  public double[] doFilter(double[] frame) {
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

}
