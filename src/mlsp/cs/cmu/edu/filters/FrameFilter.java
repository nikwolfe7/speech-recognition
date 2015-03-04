package mlsp.cs.cmu.edu.filters;

import mlsp.cs.cmu.edu.features.FeatureVisitor;

public interface FrameFilter extends FeatureVisitor {
  
  public double[] doFilter(double[] frame);
  
  public String getName(); /* for output */
  
}
