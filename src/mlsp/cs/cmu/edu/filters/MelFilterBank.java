package mlsp.cs.cmu.edu.filters;


public class MelFilterBank implements FrameFilter {

  @Override
  public Double[] doFilter(Double[] frame) {
    return frame;
  }

  @Override
  public String getName() {
    return "Mel Filter Bank";
  }

}
