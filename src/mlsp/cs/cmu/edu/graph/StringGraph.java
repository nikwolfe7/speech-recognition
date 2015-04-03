/**
 * 
 */
package mlsp.cs.cmu.edu.graph;

/**
 * @author nwolfe
 *
 */
public class StringGraph extends Graph<Character, String> {

  public StringGraph(Node<Character> head) {
    super(head);
  }

  @Override
  public Iterable<Edge<String>> getNodeEdges(Node<Character> key) {
    return getNodeEdges(key);
  }

}
