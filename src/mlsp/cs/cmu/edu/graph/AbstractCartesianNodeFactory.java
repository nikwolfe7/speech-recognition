package mlsp.cs.cmu.edu.graph;

import java.util.concurrent.LinkedBlockingQueue;

public abstract class AbstractCartesianNodeFactory<N> implements CartesianNodeFactory<N> {

  private LinkedBlockingQueue<CartesianNode<N>> queue = new LinkedBlockingQueue<CartesianNode<N>>();
  
  private CartesianNode<N> nodePrototype = getPrototype();

  private int increaseCapacity = getIncreaseCapacity();

  private int minCapacity = getMinCapacity();

  private class NodeCreator implements Runnable {
    @Override
    public void run() {
      if (queue.size() < minCapacity) {
        System.out.println("Refilling queue...");
        int count = 0;
        while (count++ <= increaseCapacity) {
          try {
            queue.put(generateNode());
          } catch (InterruptedException e) {
            System.out.println("Node queue was interrupted!");
          }
        }
      }
    }

    private CartesianNode<N> generateNode() {
      return getPrototype();
//      try {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        ObjectOutputStream oos = new ObjectOutputStream(baos);
//        oos.writeObject(nodePrototype);
//        oos.flush();
//        oos.close();
//        ByteArrayInputStream ins = new ByteArrayInputStream(baos.toByteArray());
//        ObjectInputStream in = new ObjectInputStream(ins);
//        return (CartesianNode<N>) in.readObject();
//      } catch (IOException | ClassNotFoundException e) {
//        e.printStackTrace();
//      }
//      return null;
    }
  }

  @Override
  public CartesianNode<N> getNewCartesianNode(Node<N> n1, Node<N> n2) {
    CartesianNode<N> node;
    try {
      new NodeCreator().run();
      node = queue.take();
      node.setLeft(n1);
      node.setRight(n2);
      return node;
    } catch (InterruptedException e) {
      System.out.println("Node queue was interrupted!");
    }
    return null;
  }

  
  @Override
  public void recycleNode(CartesianNode<N> node) {
    try {
      queue.put(node);
    } catch (InterruptedException e) {
      System.out.println("Node queue was interrupted!");
    }
  }
  
  protected abstract int getMinCapacity(); 
  
  protected abstract int getIncreaseCapacity();

  protected abstract CartesianNode<N> getPrototype();

}
