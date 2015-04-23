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
  
 /**
  *  override in sublcass to provide a specific ordering...
  * @return
  */
 public Iterable<Node<N>> getNodeIterator() {
  return graphNodes;
 }

  public List<Edge<E>> getEdges() {
    return graphEdges;
  }
  
  /**
   * By default we return the node with the least cost.
   * Classes requiring the most expensive (or highest scoring)
   * path should override this method. 
   * 
   * @param pointer
   */
  public Edge<?> getBestPredecessor(Node<N> node) {
    double bestCost = 1e100; // "infinity"
    double currCost = 1e100;
    Edge<?> bestEdge = null;
    for (Edge<?> edge : node.getIncomingEdges()) {
      double cost = edge.getWeight() + edge.getNodePredecessor().getCost();
      if (cost <= bestCost) {
        bestCost = cost;
        bestEdge = edge;
      }
    }
    if (node.getBackPointer() != null) {
      currCost = node.getBackPointer().getWeight() + node.getNodeFromBackPointer().getCost();
      if (bestCost >= currCost)
        return node.getBackPointer();
    }
    return bestEdge;
  }

  // get the viterbi best path cost
  public Node<N> getViterbiBestPath() {
    Node<N> lastPointer = null;
    for (Node<N> node : getNodeIterator()) {
      lastPointer = node;
      Edge<?> bestEdge = getBestPredecessor(node);
      node.setBackPointer(bestEdge);
      double bestCost = bestEdge.getWeight() + node.getNodeFromBackPointer().getCost(); 
      node.setCost(bestCost);
    }
    return lastPointer;
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Node<N> node : getNodes()) {
      sb.append("\nN=" + node.getValue().toString() + ",w=" + node.getCost() + " id="
              + node.hashCode());
      if (node.getBackPointer() != null) {
        sb.append(" <<Ptr=" + node.getNodeFromBackPointer().getValue().toString() + " id="
                + node.getNodeFromBackPointer().hashCode());
      }
      sb.append(" \n");
      for (Edge<?> edge : node.getOutgoingEdges()) {
        sb.append("OUT:|------" + edge.toString() + "\n");
      }
      for (Edge<?> edge : node.getIncomingEdges()) {
        sb.append("IN: |------" + edge.toString() + "\n");
      }
    }
    return sb.toString();
  }
}
