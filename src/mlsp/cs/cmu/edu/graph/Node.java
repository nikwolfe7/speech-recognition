package mlsp.cs.cmu.edu.graph;

import java.util.ArrayList;
import java.util.List;

public abstract class Node<N> {
  
  private N value;
  private double cost;
  private List<Edge<?>> outgoingEdges = new ArrayList<Edge<?>>();
  
  public Node(N value) {
    this.value = value;
    this.cost = 0.0;
  }

  public Node(N value, double cost) {
    this.value = value;
    this.cost = cost;
  }
  
  /**
   * Defines how to compare this node to another node, returning
   * some double value to represent that difference. 
   * 
   * @param node
   * @return
   */
  public abstract double getDifference(Node<N> node);
  
  /**
   * @param edge
   */
  public void addEdge(Edge<?> edge) {
    outgoingEdges.add(edge);
  }

  /**
   * @return the value
   */
  public N getValue() {
    return value;
  }

  /**
   * @param value the value to set
   */
  public void setValue(N value) {
    this.value = value;
  }

  /**
   * @return the cost
   */
  public double getCost() {
    return cost;
  }

  /**
   * @param cost the cost to set
   */
  public void setCost(double cost) {
    this.cost = cost;
  }

  /**
   * @return the outgoingEdges
   */
  public List<Edge<?>> getOutgoingEdges() {
    return outgoingEdges;
  }

}
