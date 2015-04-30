package mlsp.cs.cmu.edu.graph;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public abstract class Node<N> {

  private N value;

  private Double cost;

  private List<Edge<?>> outgoingEdges = new LinkedList<Edge<?>>();

  private List<Edge<?>> incomingEdges = new LinkedList<Edge<?>>();

  private List<Node<N>> successors = new LinkedList<Node<N>>();

  private List<Node<N>> predecessors = new LinkedList<Node<N>>();
  
  private Edge<?> backPointer;

  public Node(N value) {
    this.value = value;
    this.cost = null;
  }

  public Node(N value, Double cost) {
    this.value = value;
    this.cost = cost;
  }

  public List<Node<N>> getSuccessors() {
    return successors;
  }

  public List<Node<N>> getPredecessors() {
    return predecessors;
  }
  
  public void destroy() {
    ListIterator<Edge<?>> iter = getIncomingEdges().listIterator();
    while(iter.hasNext()) {
      iter.next();
      iter.remove();
    }
    iter = getOutgoingEdges().listIterator();
    while(iter.hasNext()) {
      iter.next();
      iter.remove();
    }
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
   * Defines how to compare this node to another node, returning some Double value to represent that
   * difference.
   * 
   * @param node
   * @return
   */
  public Double getDistance(Node<N> node) {
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
      edge.getNodeSuccessor().removeIncomingEdge(edge);
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
      edge.getNodePredecessor().removeOutgoingEdge(edge);
    return incomingEdges.remove(edge);
  }

  public N getValue() {
    return value;
  }

  public void setValue(N value) {
    this.value = value;
  }

  public Double getCost() {
    return cost;
  }

  public void setCost(Double cost) {
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

  public Edge<?> getBackPointer() {
    return backPointer;
  }
  
  @SuppressWarnings("unchecked")
  public Node<N> getNodeFromBackPointer() {
    if(getBackPointer() != null) {
      Node<?> node = getBackPointer().getNodePredecessor();
      if(node.getClass().isAssignableFrom(this.getClass())) {
        return (Node<N>) node;
      } 
    }
    return null;
  }
  
  public void setBackPointer(Edge<?> backPointer) {
    this.backPointer = backPointer;
  }

  

}
