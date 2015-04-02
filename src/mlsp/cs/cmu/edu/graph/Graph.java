package mlsp.cs.cmu.edu.graph;

import java.util.HashMap;
import java.util.Map;

public abstract class Graph<N,E> {
  
  private Map<N, Node<N>> graphNodes = new HashMap<N, Node<N>>();

  public abstract Iterable<N> getNodes(); 
  
  public abstract Iterable<Edge<E>> getNodeEdges(N key); 
  
  
}
