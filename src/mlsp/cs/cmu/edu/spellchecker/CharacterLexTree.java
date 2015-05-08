package mlsp.cs.cmu.edu.spellchecker;

import mlsp.cs.cmu.edu.graph.Graph;
import mlsp.cs.cmu.edu.graph.LexTree;
import mlsp.cs.cmu.edu.graph.Node;

public class CharacterLexTree extends LexTree<Character, String> {

  public CharacterLexTree(Graph<Character, String> graph) {
    super(graph);
  }


}
