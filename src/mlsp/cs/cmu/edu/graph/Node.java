package mlsp.cs.cmu.edu.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Node<T> {
  
  // the node's id is itself.
  private int id = hashCode();
  
  // the node's value
  protected T value;
  
  // score or cost
  protected Double score = 0.0;
  
  // node and edge weight list
  protected List<Map.Entry<Node<T>, Double>> successors;

  public Node(T value) {
    this.value = value;
    this.successors = new ArrayList<Map.Entry<Node<T>,Double>>();
  }
  
  public Node(T value, Double score) {
    this.value = value;
    this.score = score;
    this.successors = new ArrayList<Map.Entry<Node<T>,Double>>();
  }

  public Node(T value, Double score, List<Entry<Node<T>, Double>> successors) {
    this.value = value;
    this.score = score;
    this.successors = successors;
  }

  public Double getScore() {
    return score;
  }

  public void setScore(Double score) {
    this.score = score;
  }

  public List<Map.Entry<Node<T>, Double>> getOutgoingEdges() {
    return successors;
  }

  public void addDirectedEdgeTo(Node<T> node, Double weight) {
    Map.Entry<Node<T>, Double> successor = new Entry<Node<T>, Double>() {

      private Node<T> key = node;
      private Double edgeWeight = weight;
      
      @Override
      public Node<T> getKey() {
        return key;
      }

      @Override
      public Double getValue() {
        return edgeWeight;
      }

      @Override
      public Double setValue(Double value) {
        return this.edgeWeight = value;
      }
    };
    this.successors.add(successor);
  }

  public int getId() {
    return id;
  }

  public T getValue() {
    return value;
  }

}
