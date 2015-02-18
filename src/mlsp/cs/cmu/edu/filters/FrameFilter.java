package mlsp.cs.cmu.edu.filters;

public interface FrameFilter {
  
  public Short[] doFilter(Short[] frame);
  
  public Double[] doFilter(Double[] frame);
  
}
