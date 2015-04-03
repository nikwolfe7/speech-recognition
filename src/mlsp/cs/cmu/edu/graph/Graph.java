package mlsp.cs.cmu.edu.graph;

import java.util.HashMap;
import java.util.Map;

public abstract class Graph<N, E> {

  private Map<N, Node<N>> graphNodes;
  private final Node<N> headNode;
  
  public Graph(Node<N> head) {
    this.headNode = head;
    this.graphNodes = new HashMap<N, Node<N>>();
    addNode(headNode);
  }

  public Iterable<Node<N>> getNodes() {
    return graphNodes.values();
  }

  public Node<N> getHead() {
    return headNode;
  }
  
  public void addNode(Node<N> node) {
    graphNodes.put(node.getValue(), node);
  }

  public abstract Iterable<Edge<E>> getNodeEdges(Node<N> node);

}
