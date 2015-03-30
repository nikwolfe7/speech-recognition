package mlsp.cs.cmu.edu.graph;

import java.util.List;
import java.util.Map;

public class Node<T> {
  
  // the node's id is itself.
  private Node<T> id = this;
  
  // score or cost
  private Double score = 0.0;
  
  // node and weight list
  private List<Map.Entry<Node<T>, Double>> successors;

  
  
  
}
