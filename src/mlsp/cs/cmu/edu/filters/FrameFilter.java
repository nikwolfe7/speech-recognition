package mlsp.cs.cmu.edu.filters;

import mlsp.cs.cmu.edu.features.FeatureVisitor;

public abstract class FrameFilter implements FeatureVisitor {
  
  private double[] lastProcessedFrame = null;
  
  public double[] doFilter(double[] frame) {
    double[] processedFrame = doFilterImplementation(frame);
    cacheFrame(processedFrame);
    return getLastProcessedFrame();
  }
  
  protected double[] getLastProcessedFrame() {
    return lastProcessedFrame;
  }
  
  private void cacheFrame(double[] frame) {
    lastProcessedFrame = frame;
  }
  
  public abstract String getName(); /* for output */
  
  protected abstract double[] doFilterImplementation(double[] frame);
  
}
