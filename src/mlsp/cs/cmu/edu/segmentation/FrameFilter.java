package mlsp.cs.cmu.edu.segmentation;

public interface FrameFilter {
  
  public Short[] doFilter(Short[] frame);
  
  public Double[] doFilter(Double[] frame);
  
}
