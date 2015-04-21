/**
 * 
 */
package mlsp.cs.cmu.edu.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author nwolfe
 *
 */
public class CharNode extends Node<Character> {

  public CharNode(Character value) {
    super(value);
  }

  public CharNode(Character value, double cost) {
    super(value, cost);
  }

  @Override
  @SuppressWarnings("unchecked") // believe me it's checked.
  protected Iterable<Node<Character>> retrieveNodesFromEdges(List<Edge<?>> edges) {
    List<Node<Character>> nodes = new ArrayList<Node<Character>>();
    for (Edge<?> e : edges) {
      Object o = e.getNodePointer().getValue();
      if (o instanceof Character || o == null) {
        nodes.add((Node<Character>) e.getNodePointer());
      }
    }
    return nodes;
  }

  @Override
  protected DistanceCalculator<Character> getDistanceStrategy() {
    return new DistanceCalculator<Character>() {
      @Override
      public double getDifference(Node<Character> n1, Node<Character> n2) {
        return n1.getValue().compareTo(n2.getValue());
      }
    };
  }

  @Override
  public String toString() {
    return "" + getValue();
  }

}
