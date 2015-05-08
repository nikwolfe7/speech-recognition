package mlsp.cs.cmu.edu.graph;

import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class AbstractCartesianNodeFactory<N> implements CartesianNodeFactory<N> {

  private LinkedBlockingQueue<CartesianNode<N>> nodeQueue;

  private LinkedBlockingQueue<Edge<?>> edgeQueue;

  private Runnable nodeThead;

  private Runnable edgeThread;

  public AbstractCartesianNodeFactory() {
    this.nodeQueue = new LinkedBlockingQueue<CartesianNode<N>>();
    this.edgeQueue = new LinkedBlockingQueue<Edge<?>>();
    this.nodeThead = new refillNodeQueue();
    this.edgeThread = new refillEdgeQueue();
    nodeThead.run();
    edgeThread.run();
  }

  private class refillNodeQueue implements Runnable {
    @Override
    public void run() {
      while (!Thread.currentThread().isInterrupted()) {
        if (nodeQueue.size() < getMinCapacity()) {
          System.out.println("Refilling nodeQueue...");
          int count = 0;
          while (count++ <= getIncreaseNodeCapacity()) {
            nodeQueue.add(getNodePrototype());
          }
        }
        synchronized (this) {
          try {
            this.wait();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    }
  }

  private class refillEdgeQueue implements Runnable {
    @Override
    public void run() {
      while (!Thread.currentThread().isInterrupted()) {
        if (edgeQueue.size() < getMinCapacity()) {
          System.out.println("Refilling edgeQueue...");
          int count = 0;
          while (count++ <= getIncreaseEdgeCapacity()) {
            edgeQueue.add(getEdgePrototype());
          }
        }
        synchronized (this) {
          try {
            this.wait();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    }
  }

  @Override
  public CartesianNode<N> getNewCartesianNode(Node<N> n1, Node<N> n2) {
    CartesianNode<N> node = nodeQueue.poll();
    // don't delay execution
    if (node == null) {
      nodeThead.notify();
      node = getNodePrototype();
    }
    node.setLeft(n1);
    node.setRight(n2);
    return node;
  }

  @Override
  public Edge<?> getNewEdge(CartesianNode<N> pFrom, CartesianNode<N> pTo, double weight) {
    Edge<?> edge = edgeQueue.poll();
    // don't delay execution
    if (edge == null) {
      edgeThread.notify();
      edge = getEdgePrototype();
    }
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
    // executor.execute(new Runnable() {
    // @Override
    // public void run() {
    ListIterator<Edge<?>> iter = edges.listIterator();
    while (iter.hasNext()) {
      Edge<?> e = iter.next();
      e.destroy();
      edgeQueue.add(e);
    }
    // }
    // });
    // executor.shutdown();
  }

  protected abstract int getMinCapacity();

  protected abstract int getIncreaseNodeCapacity();

  protected abstract int getIncreaseEdgeCapacity();

  protected abstract Edge<?> getEdgePrototype();

  protected abstract CartesianNode<N> getNodePrototype();

}
