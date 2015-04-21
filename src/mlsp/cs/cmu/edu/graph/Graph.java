package mlsp.cs.cmu.edu.graph;

import java.util.ArrayList;
import java.util.List;

public class Graph<N, E> {

  private List<Edge<E>> graphEdges;

  private List<Node<N>> graphNodes;

  private Node<N> headNode;

  public Graph(Node<N> head) {
    this.graphEdges = new ArrayList<Edge<E>>();
    this.graphNodes = new ArrayList<Node<N>>();
    setHeadNode(head);
  }

  public void setHeadNode(Node<N> head) {
    this.headNode = head;
    addNode(head);
  }

  public void addNode(Node<N> node) {
    if(node != null)
      graphNodes.add(node);
  }

  public void addEdge(Edge<E> edge) {
    graphEdges.add(edge);
  }

  public Node<N> getHead() {
    return headNode;
  }

  public List<Node<N>> getNodes() {
    return graphNodes;
  }

  public List<Edge<E>> getEdges() {
    return graphEdges;
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for(Node<N> node : getNodes()) {
      sb.append("\nN=" + node.toString() + ", id="+node.hashCode()+"\n");
      for(Edge<?> edge : node.getOutgoingEdges()) {
        sb.append("OUT:|------" + edge.toString() + "\n");
      }
      for(Edge<?> edge : node.getIncomingEdges()) {
        sb.append("IN:|------" + edge.toString() + "\n");
      }
    }
    return sb.toString();
  }
}
