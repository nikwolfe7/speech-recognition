package mlsp.cs.cmu.edu.graph;

public interface CartesianGraphFactory<N,E> {
  
  public CartesianGraph<N, E> buildGraph(Graph<N,E> G1, Graph<N,E> G2);

}
