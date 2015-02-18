package mlsp.cs.cmu.edu.filters;

public class PreEmphasisFilter implements FrameFilter {

  @Override
  public Short[] doFilter(Short[] frame) {
    return frame;
  }

  @Override
  public Double[] doFilter(Double[] frame) {
    return frame;
  }

}
