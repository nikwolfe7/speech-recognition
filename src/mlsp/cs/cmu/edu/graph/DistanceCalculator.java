package mlsp.cs.cmu.edu.graph;

public interface DistanceCalculator<N> {
  
  public double getDifference(Node<N> n1, Node<N> n2);
  
}
