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
  protected Edge<String> getEdgeValueWeightAndPushNodeCosts(
          Node<Pair<Node<Character>, Node<Character>>> pFrom,
          Node<Pair<Node<Character>, Node<Character>>> pTo) {
    // FIRST elements are the template
    // SECOND elements are the input
    double weight = pFrom.getDistance(pTo);
    Edge<String> edge = new Edge<String>(pFrom, pTo, weight);
    // push the weight out to the node...
    Double pFromCost = pFrom.getCost(); 
    if(pFromCost == null)
      pFrom.setCost(0.0);
    double nodeCost = weight + pFrom.getCost();
    Double pToCost = pTo.getCost(); 
    if (pToCost == null) {
      pTo.setCost(nodeCost);
      pTo.setBackPointer(edge);
    } else if (nodeCost <= pTo.getCost()) {
      pTo.setCost(nodeCost);
      pTo.setBackPointer(edge); 
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
            char end = CharacterConstants.END_CHARACTER.getValue();
            char begin = CharacterConstants.BEGIN_CHARACTER.getValue();
            if (xToValue.equals(yToValue)) {
              return 0;
            } else if (n1 == n2) { // self transition
              return 1e100;
            } else if (n1idx.getValue() != n1idy.getValue()) {
              if (n1idx.getValue() == end || n1idy.getValue() == end || n1idx.getValue() == begin
                      || n1idy.getValue() == begin) {
                return 1e100;
              } else {
                return 1;
              }
            } else {
              if (n2idx.getValue() != n2idy.getValue()) {
                if (n2idx.getValue() == end || n2idy.getValue() == end || n2idx.getValue() == begin
                        || n2idy.getValue() == begin) {
                  return 1e100;
                } else {
                  return 1;
                }
              } else {
                return 1e100;
              }
            }
          }
        };
      }

      @Override
      public String toString() {
        String s = "(" + getValue().getFirst().getValue() + "," + getValue().getSecond().getValue()
                + ") id=" + hashCode();
        if(getCost() != null)
          s += " cost=" + getCost();
        return s;
      }
    }
    return new PairNode(pair);
  }
}
