package mlsp.cs.cmu.edu.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CartesianNode<T> {

  private List<Node<T>> coordinates;

  private List<Map.Entry<CartesianNode<T>, Double>> successors;

  private Double score = 0.0;

  @SuppressWarnings("unchecked")
  public CartesianNode(Node<T>... nodes) {
    this.coordinates = new ArrayList<Node<T>>();
    this.successors = new ArrayList<Map.Entry<CartesianNode<T>, Double>>();
    for (Node<T> node : nodes) {
      coordinates.add(node);
    }
  }

  @SuppressWarnings("unchecked")
  public CartesianNode(Double score, Node<T>... nodes) {
    this.coordinates = new ArrayList<Node<T>>();
    this.successors = new ArrayList<Map.Entry<CartesianNode<T>, Double>>();
    for (Node<T> node : nodes) {
      coordinates.add(node);
    }
    this.score = score;
  }

  public List<Entry<CartesianNode<T>, Double>> getOutgoingEdges() {
    return successors;
  }

  public void addDirectedEdgeTo(CartesianNode<T> node, Double weight) {
    Map.Entry<CartesianNode<T>, Double> successor = new Entry<CartesianNode<T>, Double>() {
      
      private CartesianNode<T> key = node;
      private Double edgeWeight = weight;
      
      @Override
      public CartesianNode<T> getKey() {
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

  public Double getScore() {
    return score;
  }

  public void setScore(Double score) {
    this.score = score;
  }

}
