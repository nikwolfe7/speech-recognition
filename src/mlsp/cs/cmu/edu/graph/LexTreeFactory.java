package mlsp.cs.cmu.edu.graph;

import mlsp.cs.cmu.edu.spellchecker.StringGraphFactory;

public class LexTreeFactory implements GraphFactory<Character, String> {

  Graph<Character, String> lexTree;
  
  public LexTreeFactory(String... processList) {
    lexTree = new StringGraphFactory(processList).buildGraph();
  }
  
  public LexTreeFactory(Graph<Character, String> graph) {
    this.lexTree = graph;
  }

  @Override
  public Graph<Character, String> buildGraph() {
    return new LexTree<Character, String>(lexTree);
  }

}
