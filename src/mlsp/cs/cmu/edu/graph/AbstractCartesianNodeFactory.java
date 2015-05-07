package mlsp.cs.cmu.edu.graph;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public abstract class AbstractCartesianNodeFactory<N> implements CartesianNodeFactory<N> {

  private Queue<CartesianNode<N>> nodeQueue = new LinkedList<CartesianNode<N>>();

  private Queue<Edge<?>> edgeQueue = new LinkedList<Edge<?>>();

  private int increaseCapacity = getIncreaseCapacity();

  private int minCapacity = getMinCapacity();

  private class NodeCreator implements Runnable {
    @Override
    public void run() {
      System.out.println("Refilling nodeQueue...");
      int count = 0;
      while (count++ <= increaseCapacity) {
        nodeQueue.add(getNodePrototype());
      }
    }
  }

  private class EdgeCreator implements Runnable {
    @Override
    public void run() {
      System.out.println("Refilling edgeQueue...");
      int count = 0;
      while (count++ <= increaseCapacity) {
        edgeQueue.add(getEdgePrototype());
      }
    }
  }

  @Override
  public CartesianNode<N> getNewCartesianNode(Node<N> n1, Node<N> n2) {
    CartesianNode<N> node = null;
    if (nodeQueue.size() < minCapacity)
      new NodeCreator().run();
    node = nodeQueue.poll();
    node.setLeft(n1);
    node.setRight(n2);
    return node;
  }

  @Override
  public Edge<?> getNewEdge(CartesianNode<N> pFrom, CartesianNode<N> pTo, double weight) {
    Edge<?> edge = null;
    if (edgeQueue.size() < minCapacity)
      new EdgeCreator().run();
    edge = edgeQueue.poll();
    edge.setAdjacentNodes(pFrom, pTo);
    edge.setWeight(weight);
    return edge;
  }

  @Override
  public void recycleNode(CartesianNode<N> node) {
    node.destroy();
    nodeQueue.add(node);
  }

  @Override
  public void recycleEdges(List<Edge<?>> edges) {
    for (Edge<?> edge : edges) {
      edge.destroy();
      edgeQueue.add(edge);
    }
  }

  protected abstract Edge<?> getEdgePrototype();

  protected abstract int getMinCapacity();

  protected abstract int getIncreaseCapacity();

  protected abstract CartesianNode<N> getNodePrototype();

}
