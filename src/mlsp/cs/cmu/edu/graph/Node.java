package mlsp.cs.cmu.edu.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Node<T> {
  
  // the node's id is itself.
  private Node<T> id = this;
  
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
    super();
    this.value = value;
    this.score = score;
    this.successors = new ArrayList<Map.Entry<Node<T>,Double>>();
  }

  public Node(T value, Double score, List<Entry<Node<T>, Double>> successors) {
    super();
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

  public List<Map.Entry<Node<T>, Double>> getSuccessors() {
    return successors;
  }

  @SuppressWarnings("unchecked")
  public void addSuccessors(Map.Entry<Node<T>, Double>... items) {
    for(Map.Entry<Node<T>, Double> successor : items) {
      this.successors.add(successor);
    }
  }

  public Node<T> getId() {
    return id;
  }

  public T getValue() {
    return value;
  }

}
