package mlsp.cs.cmu.edu.graph;

import org.apache.commons.math3.util.Pair;

public abstract class CartesianNode<N> extends Node<Pair<Node<N>, Node<N>>> implements Cloneable {

  @Override
  protected abstract DistanceCalculator<Pair<Node<N>, Node<N>>> getDistanceStrategy();
  
  public CartesianNode(Node<N> n1, Node<N> n2) {
    super(new Pair<>(n1,n2));
  }
  
  public void setValue(Node<N> n1, Node<N> n2) {
    setValue(new Pair<>(n1, n2));
  }
  
  public Node<N> getFirst() {
    return getValue().getFirst();
  }
  
  public Node<N> getSecond() {
    return getValue().getSecond();
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
    String s = "(" + getValue().getFirst().getValue() + "," + getValue().getSecond().getValue()
            + ") id=" + hashCode();
    if (getCost() != null)
      s += " cost=" + getCost();
    return s;
  }

  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
}
