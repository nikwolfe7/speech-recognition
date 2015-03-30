package mlsp.cs.cmu.edu.graph;

import java.util.ArrayList;
import java.util.List;

public class CartesianNode<T> {

  private List<Node<T>> coordinates;
  private List<CartesianNode<T>> successors; 
  private Double score;

  @SuppressWarnings("unchecked")
  public CartesianNode(Node<T>... nodes) {
    this.coordinates = new ArrayList<Node<T>>();
    for(Node<T> node : nodes) {
      coordinates.add(node);
    }
  }
  
  

}
