package mlsp.cs.cmu.edu.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph<T> {
  
  private Map<T, Node<T>> graphNodes;
  
  public Graph() {
    this.graphNodes = new HashMap<T, Node<T>>();
  }
  
  public Iterable<Node<T>> getIterable() {
    return graphNodes;
  }
  
  public void addNode(Node<T> node) {
    graphNodes.add(node);
  }
}
