package mlsp.cs.cmu.edu.segmentation;

public interface Filterable {
  
  public void attachFilter(FrameFilter frameFilter);
  
  public void clearFilters();

}
