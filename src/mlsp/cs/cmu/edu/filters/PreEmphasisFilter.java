package mlsp.cs.cmu.edu.filters;

public class PreEmphasisFilter implements FrameFilter {

  private Integer callCount = 0;

  private Integer frameDrops = 10;

  
  /*
  @Override
  public Double[] doFilter(Double[] frame) {
    return frame;
  }
  */
  @Override
  public Double[] doFilter(Double[] frame) {
    if (callCount++ > frameDrops) {
      for (int i = 1; i < frame.length; i++) {
        frame[i] = frame[i] - frame[i - 1];
      }
    } else {
      for (int i = 0; i < frame.length; i++) {
        // the log of the sum this will become zero
        frame[i] = 1.0 / Math.sqrt(frame.length);
      }
    }
    return frame;
  }

}
