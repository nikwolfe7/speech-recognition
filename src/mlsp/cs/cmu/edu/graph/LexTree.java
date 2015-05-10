package mlsp.cs.cmu.edu.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class LexTree<N, E> extends Graph<N, E> {

  public LexTree(Graph<N, E> graph) {
    super(graph.getHeadNode());
    setTailNode(graph.getTailNode());
    buildTree(getHeadNode());
    System.out.println(printGraph());
  }

  private void buildTree(Node<N> head) {
    ListIterator<Node<N>> nodeIter = head.getSuccessors().listIterator();
    Map<N, List<Node<N>>> map = new HashMap<N, List<Node<N>>>();
    while (nodeIter.hasNext()) {
      Node<N> node = nodeIter.next();
      if(!map.containsKey(node.getValue())) 
        map.put(node.getValue(), new LinkedList<Node<N>>());
      map.get(node.getValue()).add(node);
    }
    for(N key : map.keySet()) {
      nodeIter = map.get(key).listIterator();
      Node<N> first = nodeIter.next();
      while(nodeIter.hasNext()) {
        Node<N> second = nodeIter.next();
        mergeNodes(first, second, head);
      }
    }
    nodeIter = head.getSuccessors().listIterator();
    while(nodeIter.hasNext()) {
      Node<N> node = nodeIter.next();
      if(node != head && node != getTailNode()) {
        addNode(node);
        buildTree(node);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private void mergeNodes(Node<N> n1, Node<N> n2, Node<N> parent) {
    ListIterator<Edge<?>> edgeIter = n2.getOutgoingEdges().listIterator();
    while (edgeIter.hasNext()) {
      Edge<?> e = edgeIter.next();
      Node<N> successor = (Node<N>) e.getNodeSuccessor();
      if (successor != n2)
        e.setNodePredecessor(n1);
      edgeIter.remove();
    }
    n1.refreshAll();
    n2.destroy();
    parent.refreshSuccessors();
  }

}
