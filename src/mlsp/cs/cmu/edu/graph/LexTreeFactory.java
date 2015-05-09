package mlsp.cs.cmu.edu.graph;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mlsp.cs.cmu.edu.spellchecker.StringGraphFactory;

public class LexTreeFactory implements GraphFactory<Character, String> {

  Graph<Character, String> lexTree;
  String[] processList;
  
  public LexTreeFactory(String... processList) {
    this.processList = processList;
    List<String> stringSet = Arrays.asList(processList);
    Set<String> arr = new HashSet<String>();
    arr.addAll(stringSet);
    processList = arr.toArray(new String[arr.size()]);
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
