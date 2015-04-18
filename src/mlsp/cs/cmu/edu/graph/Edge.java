package mlsp.cs.cmu.edu.graph;

public abstract class Edge<E> {

  private E value = null;

  private double weight;

  private Node<?> nodePointer;

  private Node<?> predecessor;

  public Edge(Node<?> from, Node<?> to) {
    setPredecessor(from);
    setNodePointer(to);
    this.weight = 0.0;
  }

  public Edge(Node<?> from, Node<?> to, double weight) {
    setPredecessor(from);
    setNodePointer(to);
    this.weight = weight;
  }

  public void setNodePointer(Node<?> nodePointer) {
    this.nodePointer = nodePointer;
    nodePointer.addIncomingEdge(this);
  }

  public void setPredecessor(Node<?> predecessor) {
    this.predecessor = predecessor;
    predecessor.addOutgoingEdge(this);
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

  public Node<?> getNodePointer() {
    return nodePointer;
  }

  public Node<?> getPredecessor() {
    return predecessor;
  }

}