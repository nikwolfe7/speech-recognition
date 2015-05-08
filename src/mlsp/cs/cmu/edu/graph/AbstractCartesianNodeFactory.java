package mlsp.cs.cmu.edu.graph;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

public abstract class AbstractCartesianNodeFactory<N> implements CartesianNodeFactory<N> {

  private LinkedBlockingQueue<CartesianNode<N>> nodeQueue;

  private LinkedBlockingQueue<Edge<?>> edgeQueue;

  ThreadPoolExecutor executor;

  private Runnable nodeThead;

  private Runnable edgeThread;

  public AbstractCartesianNodeFactory() {
    this.nodeQueue = new LinkedBlockingQueue<CartesianNode<N>>();
    this.edgeQueue = new LinkedBlockingQueue<Edge<?>>();
    this.executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);
    this.nodeThead = new RefillNodeQueue();
    this.edgeThread = new RefillEdgeQueue();
    executor.execute(nodeThead);
    executor.execute(edgeThread);
    executor.shutdown();
  }

  private class RefillNodeQueue implements Runnable {
    @Override
    public void run() {
      if (nodeQueue.size() < getMinCapacity()) {
        int count = 0;
        while (count++ <= getIncreaseNodeCapacity()) {
          nodeQueue.add(getNodePrototype());
        }
      }
    }
  }

  private class RefillEdgeQueue implements Runnable {
    @Override
    public void run() {
      if (edgeQueue.size() < getMinCapacity()) {
        int count = 0;
        while (count++ <= getIncreaseEdgeCapacity()) {
          edgeQueue.add(getEdgePrototype());
        }
      }
    }
  }

  @Override
  public CartesianNode<N> getNewCartesianNode(Node<N> n1, Node<N> n2) {
    CartesianNode<N> node = nodeQueue.poll();
    if (node == null)
      node = getNodePrototype();
    node.setLeft(n1);
    node.setRight(n2);
    return node;
  }

  @Override
  public Edge<?> getNewEdge(CartesianNode<N> pFrom, CartesianNode<N> pTo, double weight) {
    Edge<?> edge = edgeQueue.poll();
    if (edge == null)
      edge = getEdgePrototype();
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
    for (Edge<?> e : edges) {
      e.destroy();
      edgeQueue.add(e);
    }
  }

  protected abstract int getMinCapacity();

  protected abstract int getIncreaseNodeCapacity();

  protected abstract int getIncreaseEdgeCapacity();

  protected abstract Edge<?> getEdgePrototype();

  protected abstract CartesianNode<N> getNodePrototype();

}
