package mlsp.cs.cmu.edu.graph;

public abstract class Edge<E> {
  
  private E value = null;
  private double weight;
  private Node<?> nodePointer;
  
  public Edge(Node<?> nodePointer) {
    this.nodePointer = nodePointer;
    nodePointer.registerIncomingEdge(this);
    this.weight = 0.0;
  }

  public Edge(Node<?> nodePointer, double weight) {
    this.nodePointer = nodePointer;
    nodePointer.registerIncomingEdge(this);
    this.weight = weight;
  }

  /**
   * @return the value
   */
  public E getValue() {
    return value;
  }

  /**
   * @param value the value to set
   */
  public void setValue(E value) {
    this.value = value;
  }

  /**
   * @return the weight
   */
  public double getWeight() {
    return weight;
  }

  /**
   * @param weight the weight to set
   */
  public void setWeight(double weight) {
    this.weight = weight;
  }

  /**
   * @return the nodePointer
   */
  public Node<?> getNodePointer() {
    return nodePointer;
  }

  /**
   * @param nodePointer the nodePointer to set
   */
  public void setNodePointer(Node<?> nodePointer) {
    this.nodePointer = nodePointer;
    nodePointer.registerIncomingEdge(this);
  }
}