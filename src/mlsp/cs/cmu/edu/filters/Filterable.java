package mlsp.cs.cmu.edu.filters;

public interface Filterable {
  
  public void attachFilter(FrameFilter frameFilter);
  
  public void clearFilters();

}
