package mlsp.cs.cmu.edu.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.math3.util.Pair;

public class Graph<N, E> {

  protected Set<Edge<E>> graphEdges;

  protected Map<N, Node<N>> graphNodes;

  protected Node<N> headNode;

  public Graph(Node<N> head) {
    this.graphEdges = new HashSet<Edge<E>>();
    this.graphNodes = new HashMap<N, Node<N>>();
    setHeadNode(head);
  }

  public void setHeadNode(Node<N> head) {
    this.headNode = head;
    addNode(head);
  }

  public void addNode(Node<N> node) {
    if(node != null)
      graphNodes.put(node.getValue(), node);
  }

  public void addEdge(Edge<E> edge) {
    graphEdges.add(edge);
  }

  public Node<N> getHead() {
    return headNode;
  }

  public Iterable<Node<N>> getNodes() {
    return graphNodes.values();
  }

  public Set<Edge<E>> getGraphEdges() {
    return graphEdges;
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for(Node<N> node : getNodes()) {
      sb.append("N( " + node.toString() + " )\n");
      for(Edge<?> edge : node.getOutgoingEdges()) {
        sb.append("|------" + edge.toString() + " " + edge.getNodePointer().toString() + "\n");
      }
    }
    return sb.toString();
  }
}
