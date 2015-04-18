package mlsp.cs.cmu.edu.graph;

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
  protected Edge<String> getEdgeTypeImpl(Node<?> from, Node<?> to) {
    return new Edge<String>(from, to);
  }

  @Override
  protected Node<Character> getNodeTypeImpl(Edge<?> edge) {
    if (edge instanceof Edge<String>) {
      Edge<String> strEdge = (StringEdge) edge;
      if (strEdge.getNodePointer() instanceof CharNode) {
        return (CharNode) strEdge.getNodePointer();
      }
    }
    System.out.println("Type cast failed! Check your graph!!");
    return null;
  }

  @Override
  protected Node<Pair<Node<Character>, Node<Character>>> getNodePairImpl(Pair<Node<Character>, Node<Character>> nodePair) {
    final class NodePair extends Node<Pair<Node<Character>,Node<Character>>> {

      public NodePair(Pair<Node<Character>, Node<Character>> value) {
        super(value);
        // TODO Auto-generated constructor stub
      }

      @Override
      protected Iterable<Node<Pair<Node<Character>, Node<Character>>>> retrieveNodesFromEdges(Set<Edge<?>> edges) {
        // TODO Auto-generated method stub
        return null;
      }

      @Override
      public double getDifference(Node<Pair<Node<Character>, Node<Character>>> node) {
        // TODO Auto-generated method stub
        return 0;
      }
    }
   return new NodePair(nodePair);
  }
}
