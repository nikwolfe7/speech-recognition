package mlsp.cs.cmu.edu.graph;

public abstract class AbstractCartesianGraphFactory<N, E> implements CartesianGraphFactory<N, E> {

  private CartesianGraph<N, E> graphSingleton;
  
  public AbstractCartesianGraphFactory(CartesianNodeFactory<N> nodeFactory) {
    this.graphSingleton = getGraphImpl(nodeFactory);
  }

  @Override
  public CartesianGraph<N, E> buildGraph(Graph<N, E> G1, Graph<N, E> G2) {
    graphSingleton.buildGraph(G1, G2);
    return graphSingleton;
  }
  
  protected abstract CartesianGraph<N,E> getGraphImpl(CartesianNodeFactory<N> factory);

}
