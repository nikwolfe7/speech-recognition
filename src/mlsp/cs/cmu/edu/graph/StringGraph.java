/**
 * 
 */
package mlsp.cs.cmu.edu.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nwolfe
 *
 */
public class StringGraph extends Graph<Character, String> {

  public StringGraph(Node<Character> head) {
    super(head);
  }

  @Override
  public List<Edge<String>> getNodeEdges(Node<Character> key) {
    List<Edge<String>> list = new ArrayList<Edge<String>>();
    for(Edge<?> edge : key.getOutgoingEdges()) {
      if(edge instanceof StringEdge)
        list.add((StringEdge) edge);
    }
    return list;
  }

}
