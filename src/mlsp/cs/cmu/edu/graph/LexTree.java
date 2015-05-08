package mlsp.cs.cmu.edu.graph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class LexTree<N, E> extends Graph<N, E> {

  public LexTree(Graph<N,E> graph) {
    super(graph.getHead());
    setTailNode(graph.getTailNode());
    System.out.println("TAIL:" + getTailNode());
    for (Node<N> node : getNodes())
      buildLexTree(node);
    System.out.print(printGraph());
  }

  private void buildLexTree(Node<N> node) {
    System.out.println("TAIL:" + getTailNode());
    Map<N, List<Node<N>>> map = new HashMap<N, List<Node<N>>>();
    for (Node<N> n : node.getSuccessors()) {
      if (map.containsKey(n.getValue())) {
        map.get(n.getValue()).add(n);
      } else {
        map.put(n.getValue(), new LinkedList<Node<N>>());
        map.get(n.getValue()).add(n);
      }
    }
    for (N key : map.keySet()) {
      ListIterator<Node<N>> iter = map.get(key).listIterator();
      Node<N> n1 = iter.next();
      while (iter.hasNext()) {
        Node<N> n2 = iter.next();
        mergeNodes(n1, n2);
        iter.remove();
      }
    }
  }

  @SuppressWarnings("unchecked")
  private void mergeNodes(Node<N> n1, Node<N> n2) {
    System.out.println("TAIL:" + getTailNode());
    for (Edge<?> e : n2.getOutgoingEdges()) {
      if(!(e.getNodePredecessor() == e.getNodeSuccessor()))
        e.setNodePredecessor(n1);
    }
    for (Edge<?> e : n2.getIncomingEdges()) {
      Node<N> parent = (Node<N>) e.getNodePredecessor();
      parent.removeOutgoingEdge(e);
    }
    n2.destroy();
  }
}
