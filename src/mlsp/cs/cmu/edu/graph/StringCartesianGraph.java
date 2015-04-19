package mlsp.cs.cmu.edu.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
      protected Iterable<Node<Pair<Node<Character>, Node<Character>>>> retrieveNodesFromEdges(Set<Edge<?>> edges) {
        List<Node<Pair<Node<Character>, Node<Character>>>> nodeList = new ArrayList<Node<Pair<Node<Character>, Node<Character>>>>();
        for (Edge<?> e : edges) {
          Object o1 = e.getNodePointer();
          if (o1 instanceof PairNode) {
            nodeList.add((Node<Pair<Node<Character>, Node<Character>>>) e.getNodePointer());
          }
        }
        return nodeList;
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
    }
    return new PairNode(pair);
  }
}
