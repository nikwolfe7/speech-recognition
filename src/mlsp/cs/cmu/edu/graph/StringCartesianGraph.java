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
  protected void pushNodeCosts(Node<Pair<Node<Character>, Node<Character>>> pFrom,
          Node<Pair<Node<Character>, Node<Character>>> pTo, Edge<String> edge) {
    // push the weight out to the node...
    Double pFromCost = pFrom.getCost();
    if (pFromCost == null)
      pFrom.setCost(0.0);
    double nodeCost = edge.getWeight() + pFrom.getCost();
    Double pToCost = pTo.getCost();
    if (pToCost == null) {
      pTo.setCost(nodeCost);
      pTo.setBackPointer(edge);
    } else if (nodeCost <= pTo.getCost()) {
      pTo.setCost(nodeCost);
      pTo.setBackPointer(edge);
    }

  }

  @Override
  protected Edge<String> getEdgeValueAndSetWeights(
          Node<Pair<Node<Character>, Node<Character>>> pFrom,
          Node<Pair<Node<Character>, Node<Character>>> pTo) {
    // FIRST elements are the template
    // SECOND elements are the input
    double weight = pFrom.getDistance(pTo);
    return new Edge<String>(pFrom, pTo, weight);
  }

  @Override
  protected Node<Pair<Node<Character>, Node<Character>>> getCartesianNodeImpl(
          Pair<Node<Character>, Node<Character>> pair) {

    final class PairNode extends Node<Pair<Node<Character>, Node<Character>>> {

      private double INFINITY = 1e100;

      public PairNode(Pair<Node<Character>, Node<Character>> value) {
        super(value);
      }

      @Override
      @SuppressWarnings("unchecked")
      // it's checked...
      protected Node<Pair<Node<Character>, Node<Character>>> retrievePredecessorFromEdge(
              Edge<?> edge) {
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
            Character xToValue, yToValue, xFromValue, yFromValue;
            xFromValue = n1.getValue().getFirst().getValue();
            yFromValue = n1.getValue().getSecond().getValue();
            xToValue = n2.getValue().getFirst().getValue();
            yToValue = n2.getValue().getSecond().getValue();
            if (n1 == n2) {
              return INFINITY;
            } else if (xToValue.equals(yToValue)) {
              if (!checkIsNonEmittingNode(n1))
                return 0;
              else
                return INFINITY;
            } else if (xFromValue.equals(yFromValue)) {
              if (!checkIsNonEmittingNode(n2)) {
                if (xToValue.equals(yToValue))
                  return 0;
                else
                  return 1;
              } else {
                return INFINITY;
              }
            } else if (xFromValue.equals(xToValue) || yFromValue.equals(yToValue)) {
              if (checkPairContainsNonEmittingNode(n1, n2))
                return INFINITY;
              else
                return 1;
            } else if (checkPairContainsNonEmittingNode(n1, n2)) {
              return INFINITY;
            } else {
              return 1;
            }
          }
        };
      }

      private boolean checkPairContainsNonEmittingNode(
              Node<Pair<Node<Character>, Node<Character>>> n1,
              Node<Pair<Node<Character>, Node<Character>>> n2) {
        return checkIsNonEmittingNode(n1) || checkIsNonEmittingNode(n2);
      }
      
      private boolean checkIsNonEmittingNode(Node<Pair<Node<Character>, Node<Character>>> node) {
        Character xValue = node.getValue().getFirst().getValue();
        Character yValue = node.getValue().getSecond().getValue();
        char end = CharacterConstants.END_CHARACTER.getValue();
        char begin = CharacterConstants.BEGIN_CHARACTER.getValue();
        if (xValue.equals(yValue) && (xValue.equals(end) || xValue.equals(begin)))
          return false;
        else
          return xValue.equals(end) || yValue.equals(end) || xValue.equals(begin)
                  || yValue.equals(begin);
      }

      @Override
      public String toString() {
        String s = "(" + getValue().getFirst().getValue() + "," + getValue().getSecond().getValue()
                + ") id=" + hashCode();
        if (getCost() != null)
          s += " cost=" + getCost();
        return s;
      }
    }
    return new PairNode(pair);
  }

}
