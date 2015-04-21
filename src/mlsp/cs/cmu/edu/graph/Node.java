package mlsp.cs.cmu.edu.graph;

import java.util.ArrayList;
import java.util.List;

public abstract class Node<N> {

  private N value;

  private double cost;

  private List<Edge<?>> outgoingEdges = new ArrayList<Edge<?>>();

  private List<Edge<?>> incomingEdges = new ArrayList<Edge<?>>();

  private List<Node<N>> successors = new ArrayList<Node<N>>();

  private List<Node<N>> predecessors = new ArrayList<Node<N>>();

  public Node(N value) {
    this.value = value;
    this.cost = 0.0;
  }

  public Node(N value, double cost) {
    this.value = value;
    this.cost = cost;
  }

  public Iterable<Node<N>> getSuccessors() {
    return successors;
  }

  public Iterable<Node<N>> getPredecessors() {
    return predecessors;
  }

  /**
   * Get the nodes at the other ends of our edges...
   * 
   * @param edges
   * @return
   */
  protected abstract Node<N> retrievePredecessorFromEdge(Edge<?> edge);

  protected abstract Node<N> retrieveSuccessorFromEdge(Edge<?> edge);

  /**
   * Defines how to compare this node to another node, returning some double value to represent that
   * difference.
   * 
   * @param node
   * @return
   */
  public double getDistance(Node<N> node) {
    return getDistanceStrategy().getDifference(this, node);
  }

  protected abstract DistanceCalculator<N> getDistanceStrategy();

  public void addOutgoingEdge(Edge<?> edge) {
    if (edge.getNodePredecessor() == this) {
      outgoingEdges.add(edge);
      if(edge.getNodeSuccessor() != null)
        successors.add(retrieveSuccessorFromEdge(edge));
    } else {
      throw new RuntimeException("Attempted to add outgoing edge which does not come from me!");
    }
  }

  public void addIncomingEdge(Edge<?> edge) {
    if (edge.getNodeSuccessor() == this) { // only if you're pointing to me.
      incomingEdges.add(edge);
      if(edge.getNodePredecessor() != null)
        predecessors.add(retrievePredecessorFromEdge(edge));
    } else {
      throw new RuntimeException("Attempted to add an incoming edge which does not point to me!");
    }
  }

  /**
   * Discouraged unless the edge is already fully connected. 
   * Otherwise, use setAdjacentNodes
   * 
   * @param edge
   * @return
   */
  public boolean removeOutgoingEdge(Edge<?> edge) {
    edge.setNodePredecessor(null);
    if(edge.getNodeSuccessor() != null)
      successors.remove(edge.getNodeSuccessor());
    return outgoingEdges.remove(edge);
  }

  /**
   * Discouraged unless the edge is already fully connected. 
   * Otherwise, use setAdjacentNodes
   * 
   * @param edge
   * @return
   */
  public boolean removeIncomingEdge(Edge<?> edge) {
    edge.setNodeSuccessor(null);
    if(edge.getNodePredecessor() != null)
      predecessors.remove(edge.getNodePredecessor());
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
