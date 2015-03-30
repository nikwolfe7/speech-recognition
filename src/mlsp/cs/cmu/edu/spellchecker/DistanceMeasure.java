package mlsp.cs.cmu.edu.spellchecker;

public interface DistanceMeasure<T, E> {

  public int getCost(E[][] trellis, T src, T target, int i, int j);
  
  public int getScore(E[][] trellis, T src, T target, int i, int j);
  
}
