package mlsp.cs.cmu.edu.graph;

import java.io.Serializable;

public class Edge<E> implements Serializable{

  private static final long serialVersionUID = -5653940785139037110L;

  private E value = null;

  private double weight;

  private Node<?> nodeSuccessor;

  private Node<?> nodePredecessor;
  
  public void destroy() {
    setValue(null);
    setWeight(0.0);
    setNodeSuccessor(null);
    setNodePredecessor(null);
  }

  public Edge(Node<?> from, Node<?> to) {
    setAdjacentNodes(from, to);
    this.weight = 0.0;
  }

  public Edge(Node<?> from, Node<?> to, double weight) {
    setAdjacentNodes(from, to);
    this.weight = weight;
  }
  
  public void setAdjacentNodes(Node<?> from, Node<?> to) {
    this.nodePredecessor = from;
    this.nodeSuccessor = to;
    nodeSuccessor.addIncomingEdge(this);
    nodePredecessor.addOutgoingEdge(this);
  }

  public void setNodeSuccessor(Node<?> nodeSuccessor) {
    this.nodeSuccessor = nodeSuccessor;
    if(nodePredecessor != null && nodeSuccessor != null) {
      nodeSuccessor.addIncomingEdge(this);
    }
  }

  public void setNodePredecessor(Node<?> nodePredecessor) {
    this.nodePredecessor = nodePredecessor;
    if(nodeSuccessor != null && nodePredecessor != null) {
      nodePredecessor.addOutgoingEdge(this);
    }
  }

  public E getValue() {
    return value;
  }

  public void setValue(E value) {
    this.value = value;
  }

  public double getWeight() {
    return weight;
  }

  public void setWeight(double weight) {
    this.weight = weight;
  }

  public Node<?> getNodeSuccessor() {
    return nodeSuccessor;
  }

  public Node<?> getNodePredecessor() {
    return nodePredecessor;
  }

  @Override
  public String toString() {
    return " " + getNodePredecessor().getValue().toString() + " id=" + getNodePredecessor().hashCode() +
            " --> (v=" + getValue() + ",w=" + getWeight() + 
            ") --> " + getNodeSuccessor().getValue().toString() + " id=" + getNodeSuccessor().hashCode();
  }

}