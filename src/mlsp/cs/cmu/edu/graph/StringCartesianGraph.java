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
  protected Edge<String> getEdgeValueWeightAndNodeCosts(
          Node<Pair<Node<Character>, Node<Character>>> pFrom,
          Node<Pair<Node<Character>, Node<Character>>> pTo) {
    double weight = pFrom.getDistance(pTo);
    Edge<String> edge = new Edge<String>(pFrom, pTo, weight);
    // if this is the last node, we will find the correct string on the
    // outgoing edge of the TEMPLATE, which is the second value in the pair
    if(weight == 0 && pTo.getValue().getSecond().getValue() == CharacterConstants.END_CHARACTER.getValue()) {
      // this is an end state, the template value will be the value of this nodes incoming edge
      Object word = pTo.getValue().getSecond().getIncomingEdges().get(0).getValue();
      if(word != null && word instanceof String) {
        // set this outgoing edge value
        edge.setValue((String)word);
      }
    }
    return edge;
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
      // to be used with edge weight assesssments...
      protected DistanceCalculator<Pair<Node<Character>, Node<Character>>> getDistanceStrategy() {
        return new DistanceCalculator<Pair<Node<Character>, Node<Character>>>() {
          @Override
          public double getDifference(Node<Pair<Node<Character>, Node<Character>>> n1,
                  Node<Pair<Node<Character>, Node<Character>>> n2) {
            Character xToValue, yToValue;
            xToValue = n2.getValue().getFirst().getValue();
            yToValue = n2.getValue().getSecond().getValue();
            Node<Character> n2idx = n2.getValue().getFirst();
            Node<Character> n1idx = n1.getValue().getFirst();
            Node<Character> n2idy = n2.getValue().getSecond();
            Node<Character> n1idy = n1.getValue().getSecond();
            if ( n1 == n2 ) { // insanity check
              return 1e100; // infinity
            } else {
              if(n2idx == n1idx) { // horizontal move
                return 1; // insertion
              } else if(n2idy == n1idy) { // vertical move
                return 1; // deletion
              } else if(xToValue.equals(yToValue)) {
                if(xToValue == CharacterConstants.BEGIN_CHARACTER.getValue()) {
                  return 1; // begin char  
                } else {
                  return 0; // perfect match
                }
              } else {
                return 1; // substitution 
              }
            }
          }
        };
      }

      @Override
      public String toString() {
        return "(" + getValue().getFirst().getValue() + "," + getValue().getSecond().getValue()
                + ") id=" + hashCode();
      }
    }
    return new PairNode(pair);
  }
}
