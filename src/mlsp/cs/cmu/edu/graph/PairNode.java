package mlsp.cs.cmu.edu.graph;

import org.apache.commons.math3.util.Pair;

public class PairNode extends Node<Pair<Node<Character>, Node<Character>>> implements Cloneable {

  private DistanceCalculator<Pair<Node<Character>, Node<Character>>> distanceStrategy = CharacterNodeDistanceStrategy.getInstance();

  public PairNode(Pair<Node<Character>, Node<Character>> value) {
    super(value);
  }

  @Override
  @SuppressWarnings("unchecked")
  // it's checked...
  protected Node<Pair<Node<Character>, Node<Character>>> retrievePredecessorFromEdge(Edge<?> edge) {
    Object o = edge.getNodePredecessor();
    if (o instanceof PairNode) {
      return (Node<Pair<Node<Character>, Node<Character>>>) edge.getNodePredecessor();
    } else {
      return null;
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  // it's checked...
  protected Node<Pair<Node<Character>, Node<Character>>> retrieveSuccessorFromEdge(Edge<?> edge) {
    Object o = edge.getNodeSuccessor();
    if (o instanceof PairNode) {
      return (Node<Pair<Node<Character>, Node<Character>>>) edge.getNodeSuccessor();
    } else {
      return null;
    }
  }

  @Override
  // to be used with edge weight assesssments...
  protected DistanceCalculator<Pair<Node<Character>, Node<Character>>> getDistanceStrategy() {
    return distanceStrategy;
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
  protected Object clone() throws CloneNotSupportedException {
    return this.clone();
  }
}
