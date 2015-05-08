package mlsp.cs.cmu.edu.graph;

public class LexTreeFactory<N, E> implements GraphFactory<N, E> {

  Graph<N, E> lexTree;
  
  public LexTreeFactory(Graph<N,E> graph) {
    this.lexTree = graph;
  }

  @Override
  public Graph<N, E> buildGraph() {
    return new LexTree<N, E>(lexTree);
  }

}
