package mlsp.cs.cmu.edu.graph;

import java.util.ArrayList;
import java.util.List;

public class CartesianGraph<T> {
  
  private List<CartesianNode<T>> graph;
  
  public CartesianGraph() {
    this.graph = new ArrayList<CartesianNode<T>>();
  }

  public Iterable<CartesianNode<T>> getIterable() {
    return graph;
  }
  
  public void addNode(CartesianNode<T> node) {
    graph.add(node);
  }
  
}
