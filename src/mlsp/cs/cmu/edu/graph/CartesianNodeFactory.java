package mlsp.cs.cmu.edu.graph;

public interface CartesianNodeFactory<N> extends NodeFactory<N> {
  
  public CartesianNode<N> getNewCartesianNode(Node<N> n1, Node<N> n2);

}
