package mlsp.cs.cmu.edu.graph;

import org.apache.commons.lang3.tuple.MutablePair;

public abstract class CartesianNode<N> extends Node<MutablePair<Node<N>, Node<N>>> {

  private static final long serialVersionUID = 8623888536440491160L;

  @Override
  protected abstract DistanceCalculator<MutablePair<Node<N>, Node<N>>> getDistanceStrategy();

  public CartesianNode(Node<N> n1, Node<N> n2) {
    super(new MutablePair<Node<N>, Node<N>>(n1, n2));
  }

  @Override
  public void destroy() {
    // leave value because we can just reset left/right
    setCost(null);
    getIncomingEdges().clear();
    getOutgoingEdges().clear();
    getSuccessors().clear();
    getPredecessors().clear();
    setBackPointer(null);
  }
  
  public void setLeft(Node<N> node) {
    getValue().setLeft(node);
  }

  public void setRight(Node<N> node) {
    getValue().setRight(node);
  }

  public Node<N> getLeft() {
    return getValue().getLeft();
  }

  public Node<N> getRight() {
    return getValue().getRight();
  }

  @Override
  @SuppressWarnings("unchecked")
  // it's checked...
  protected CartesianNode<N> retrievePredecessorFromEdge(Edge<?> edge) {
    Object o = edge.getNodePredecessor();
    if (o instanceof CartesianNode) {
      return (CartesianNode<N>) edge.getNodePredecessor();
    } else {
      return null;
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  // it's checked...
  protected CartesianNode<N> retrieveSuccessorFromEdge(Edge<?> edge) {
    Object o = edge.getNodeSuccessor();
    if (o instanceof CartesianNode) {
      return (CartesianNode<N>) edge.getNodeSuccessor();
    } else {
      return null;
    }
  }

  @Override
  public String toString() {
    String s = "(" + getValue().getLeft().getValue() + "," + getValue().getRight().getValue()
            + ") id=" + hashCode();
    if (getCost() != null)
      s += " cost=" + getCost();
    return s;
  }

}
