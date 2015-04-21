package mlsp.cs.cmu.edu.graph;

import java.util.ArrayList;
import java.util.List;

public abstract class Node<N> {

  private N value;

  private double cost;

  private List<Edge<?>> outgoingEdges = new ArrayList<Edge<?>>();

  private List<Edge<?>> incomingEdges = new ArrayList<Edge<?>>();

  public Node(N value) {
    this.value = value;
    this.cost = 0.0;
  }

  public Node(N value, double cost) {
    this.value = value;
    this.cost = cost;
  }
  
  public Iterable<Node<N>> getSuccessors() {
    return retrieveNodesFromEdges(outgoingEdges);
  }
  
  public Iterable<Node<N>> getPredecessors() {
    return retrieveNodesFromEdges(incomingEdges);
  }
  
  /**
   * Get the nodes at the other ends of our edges...
   * 
   * @param edges
   * @return
   */
  protected abstract Iterable<Node<N>> retrieveNodesFromEdges(List<Edge<?>> edges);

  /**
   * Defines how to compare this node to another node, returning some double value to represent that
   * difference.
   * 
   * @param node
   * @return
   */
  public double getDifference(Node<N> node) {
    return getDistanceStrategy().getDifference(this, node);
  }
  
  protected abstract DistanceCalculator<N> getDistanceStrategy();

  public void addOutgoingEdge(Edge<?> edge) {
    if (edge.getPredecessor() == this)
      outgoingEdges.add(edge);
    else
      throw new RuntimeException("Attempted to add outgoing edge which does not come from me!");
  }

  public void addIncomingEdge(Edge<?> edge) {
    if (edge.getNodePointer() == this) // only if you're pointing to me.
      incomingEdges.add(edge);
    else
      throw new RuntimeException("Attempted to add an incoming edge which does not point to me!");
  }

  public boolean removeOutgoingEdge(Edge<?> edge) {
    edge.setPredecessor(null);
    return outgoingEdges.remove(edge);
  }

  public boolean removeIncomingEdge(Edge<?> edge) {
    edge.setNodePointer(null);
    return incomingEdges.remove(edge);
  }

  public N getValue() {
    return value;
  }

  public void setValue(N value) {
    this.value = value;
  }

  public double getCost() {
    return cost;
  }

  public void setCost(double cost) {
    this.cost = cost;
  }

  public List<Edge<?>> getOutgoingEdges() {
    return outgoingEdges;
  }

  public List<Edge<?>> getIncomingEdges() {
    return incomingEdges;
  }

  @Override
  public abstract String toString();

}
