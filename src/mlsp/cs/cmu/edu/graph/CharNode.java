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
    // TODO Auto-generated constructor stub
  }

  public CharNode(Character value, double cost) {
    super(value, cost);
    // TODO Auto-generated constructor stub
  }

  @Override
  public double getDifference(Node<Character> node) {
    Character nodeChar = node.getValue();
    return getValue().compareTo(nodeChar);
  }

  @Override
  @SuppressWarnings("unchecked") // believe me it's checked.
  protected Iterable<Node<Character>> retrieveNodesFromEdges(Set<Edge<?>> edges) {
    List<Node<Character>> nodes = new ArrayList<Node<Character>>();
    for (Edge<?> e : edges) {
      Object o = e.getNodePointer().getValue();
      if (o instanceof Character) {
        nodes.add((Node<Character>) e.getNodePointer());
      }
    }
    return nodes;
  }

}
