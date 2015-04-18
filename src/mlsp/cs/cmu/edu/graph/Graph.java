package mlsp.cs.cmu.edu.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class Graph<N, E> {

  private Set<Edge<E>> graphEdges;
  private Map<N, Node<N>> graphNodes;
  private final Node<N> headNode;
  
  public Graph(Node<N> head) {
    this.headNode = head;
    this.graphEdges = new HashSet<Edge<E>>();
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
    System.out.println(graphNodes.get(node.getValue()));
  }

  public Set<Edge<E>> getGraphEdges() {
    return graphEdges;
  }
}
