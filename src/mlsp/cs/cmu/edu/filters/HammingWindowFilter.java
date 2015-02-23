package mlsp.cs.cmu.edu.filters;

public class HammingWindowFilter implements FrameFilter {

  @Override
  public Double[] doFilter(Double[] frame) {
    for(int n = 0; n < frame.length; n++) {
      Double window = 0.54 - 0.46*Math.cos((2 * Math.PI * n)/frame.length); 
      frame[n] = frame[n] * window;
    }
    return frame;
  }

  @Override
  public String getName() {
    return "Hamming Window";
  }

}
