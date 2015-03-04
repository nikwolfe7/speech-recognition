package mlsp.cs.cmu.edu.filters;

public interface FrameFilter {
  
  public double[] doFilter(double[] frame);
  
  public String getName(); /* for output */
  
}
