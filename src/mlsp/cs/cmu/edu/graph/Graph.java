package mlsp.cs.cmu.edu.graph;

import java.util.LinkedList;
import java.util.List;

public class Graph<N, E> {

  private List<Edge<E>> graphEdges;

  private List<Node<N>> graphNodes;

  private Node<N> headNode;

  private Node<N> tailNode;

  public void destroy() {
    getEdges().clear();
    getNodes().clear();
    setHeadNode(null);
    setTailNode(null);
  }

  public Graph(Node<N> head) {
    this.graphEdges = new LinkedList<Edge<E>>();
    this.graphNodes = new LinkedList<Node<N>>();
    setHeadNode(head);
    addNode(head);
  }

  public void setHeadNode(Node<N> head) {
    this.headNode = head;
  }

  public Node<N> getTailNode() {
    return tailNode;
  }

  public void setTailNode(Node<N> tailNode) {
    this.tailNode = tailNode;
  }

  public void addNode(Node<N> node) {
    if (node != null)
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

  public void remove(Node<N> node) {
    getNodes().remove(node);
  }

  public String printGraph() {
    StringBuilder sb = new StringBuilder();
    for (Node<N> node : getNodes()) {
      sb.append("\nN=" + node.getValue().toString() + ",cost=" + node.getCost() + " id="
              + node.hashCode());
      if (node.getBackPointer() != null) {
        sb.append(" <<Ptr=" + node.getNodeFromBackPointer().getValue().toString() + " id="
                + node.getNodeFromBackPointer().hashCode());
      }
      sb.append(" \n");
      for (Edge<?> edge : node.getOutgoingEdges()) {
        sb.append("OUT:|------" + edge.toString() + "\n");
      }
      sb.append(" \n");
      for (Edge<?> edge : node.getIncomingEdges()) {
        sb.append("IN: |------" + edge.toString() + "\n");
      }
    }
    sb.append("\nTail: " + getTailNode().hashCode());
    return sb.toString();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Node<N> n : getNodes()) {
      sb.append(n.toString() + "\n");
    }
    return sb.toString();
  }
}
