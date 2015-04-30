package mlsp.cs.cmu.edu.graph;

import org.apache.commons.math3.util.Pair;

public abstract class CartesianNode<N> extends Node<Pair<Node<N>, Node<N>>> implements Cloneable {

  public CartesianNode(Pair<Node<N>, Node<N>> value) {
    super(value);
  }

  @Override
  // to be used with edge weight assesssments...
  protected abstract DistanceCalculator<Pair<Node<N>, Node<N>>> getDistanceStrategy();

  @Override
  @SuppressWarnings("unchecked")
  // it's checked...
  protected Node<Pair<Node<N>, Node<N>>> retrievePredecessorFromEdge(Edge<?> edge) {
    Object o = edge.getNodePredecessor();
    if (o instanceof CartesianNode) {
      return (Node<Pair<Node<N>, Node<N>>>) edge.getNodePredecessor();
    } else {
      return null;
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  // it's checked...
  protected Node<Pair<Node<N>, Node<N>>> retrieveSuccessorFromEdge(Edge<?> edge) {
    Object o = edge.getNodeSuccessor();
    if (o instanceof CartesianNode) {
      return (Node<Pair<Node<N>, Node<N>>>) edge.getNodeSuccessor();
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
