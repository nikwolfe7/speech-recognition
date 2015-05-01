package mlsp.cs.cmu.edu.graph;

public interface NodeFactory<N> {
  
  public Node<N> getNewNode(N value);
  
  public CartesianNode<N> getNewCartesianNode(N v1, N v2);

}
