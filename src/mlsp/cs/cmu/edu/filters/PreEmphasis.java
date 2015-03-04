package mlsp.cs.cmu.edu.filters;

public class PreEmphasis implements FrameFilter {

  
  
  private int callCount = 0;

  private int frameDrops = 10;
  
  private double alpha = 0.95;
  
  @Override
  public double[] doFilter(double[] frame) {
    if (callCount++ >= frameDrops) {
      for (int i = 1; i < frame.length; i++) {
        frame[i] = frame[i] - alpha * frame[i - 1];
      }
    } else {
      for (int i = 0; i < frame.length; i++) {
        // the log of the sum this will become zero
        frame[i] = 1.0 / Math.sqrt(frame.length);
      }
    }
    return frame;
  }

  @Override
  public String getName() {
    return "Pre-Emphasis";
  }

}
