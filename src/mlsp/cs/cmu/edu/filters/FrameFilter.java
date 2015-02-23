package mlsp.cs.cmu.edu.filters;

public interface FrameFilter {
  
  public Double[] doFilter(Double[] frame);
  
  public String getName(); /* for output */
  
}
