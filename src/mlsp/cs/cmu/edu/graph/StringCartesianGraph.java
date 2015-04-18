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
  protected Edge<String> getTypeEdgeImpl(Node<?> from, Node<?> to) {
    return new StringEdge(from, to);
  }

  @Override
  protected Node<Character> getTypeNodeImpl(Edge<?> edge) {
    if (edge instanceof StringEdge) {
      StringEdge strEdge = (StringEdge) edge;
      if (strEdge.getNodePointer() instanceof CharNode) {
        return (CharNode) strEdge.getNodePointer();
      }
    }
    System.out.println("Type cast failed! Check your graph!!");
    return null;
  }

  @Override
  protected Node<Pair<Node<Character>, Node<Character>>> getPairNodeImpl(
          Pair<Node<Character>, Node<Character>> pair) {
    final class PairNode extends Node<Pair<Node<Character>, Node<Character>>> {

      public PairNode(Pair<Node<Character>, Node<Character>> value) {
        super(value);
      }

      @Override
      public double getDifference(Node<Pair<Node<Character>, Node<Character>>> node) {
        return 0;
      }

    }
    return new PairNode(pair);
  }

}
