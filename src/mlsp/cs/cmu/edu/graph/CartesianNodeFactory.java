package mlsp.cs.cmu.edu.graph;

import java.util.List;

public interface CartesianNodeFactory<N> extends NodeFactory<N> {
  
  public CartesianNode<N> getNewCartesianNode(Node<N> n1, Node<N> n2);
  
  public Edge<?> getNewEdge(CartesianNode<N> pFrom, CartesianNode<N> pTo, double weight);
  
  public void recycleNode(CartesianNode<N> node);
  
  public void recycleEdges(List<Edge<?>> list);

}
