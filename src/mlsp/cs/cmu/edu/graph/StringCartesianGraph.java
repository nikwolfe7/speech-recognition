package mlsp.cs.cmu.edu.graph;

import org.apache.commons.math3.util.Pair;

/**
 * @author nwolfe
 *
 */
public class StringCartesianGraph extends CartesianGraph<Character, String> {

  public StringCartesianGraph(Graph<Character, String> G1, Graph<Character, String> G2) {
    super(G1, G2);
  }

  @Override
  protected Node<Pair<Node<Character>, Node<Character>>> getCartesianNodeImpl(Pair<Node<Character>, Node<Character>> pair) {
    final class PairNode extends Node<Pair<Node<Character>, Node<Character>>> {
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
      protected DistanceCalculator<Pair<Node<Character>, Node<Character>>> getDistanceStrategy() {
        return new DistanceCalculator<Pair<Node<Character>, Node<Character>>>() {
          @Override
          public double getDifference(Node<Pair<Node<Character>, Node<Character>>> n1, Node<Pair<Node<Character>, Node<Character>>> n2) {
            return Math.abs(n1.getCost() - n2.getCost()); // just the distance between the node costs...
          }
        };
      }

      @Override
      public String toString() {
        return "(" + getValue().getFirst().getValue() + "," + getValue().getSecond().getValue() + ")";
      }
    }
    return new PairNode(pair);
  }

}
