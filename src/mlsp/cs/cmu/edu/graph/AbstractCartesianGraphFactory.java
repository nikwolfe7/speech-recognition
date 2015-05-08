package mlsp.cs.cmu.edu.graph;

public abstract class AbstractCartesianGraphFactory<N, E> implements CartesianGraphFactory<N, E> {

  private CartesianNodeFactory<N> factory;
  
  public AbstractCartesianGraphFactory(CartesianNodeFactory<N> nodeFactory) {
    this.factory = nodeFactory;
  }

  @Override
  public CartesianGraph<N, E> buildGraph(Graph<N, E> G1, Graph<N, E> G2) {
    CartesianGraph<N, E> graph = getGraphImpl(factory);
    graph.buildGraph(G1, G2);
    return graph;
  }
  
  protected abstract CartesianGraph<N,E> getGraphImpl(CartesianNodeFactory<N> factory);

}
