package mlsp.cs.cmu.edu.graph;

import java.util.LinkedList;
import java.util.List;

public class Graph<N, E> {

  private List<Edge<E>> graphEdges;

  private List<Node<N>> graphNodes;

  private Node<N> headNode;

  public Graph(Node<N> head) {
    this.graphEdges = new LinkedList<Edge<E>>();
    this.graphNodes = new LinkedList<Node<N>>();
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

  public Iterable<Node<N>> getNodes() {
    return graphNodes;
  }

  public List<Edge<E>> getGraphEdges() {
    return graphEdges;
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    System.out.println("Num Nodes: " + graphNodes.size());
    System.out.println("Num Edges: " + graphEdges.size());
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
