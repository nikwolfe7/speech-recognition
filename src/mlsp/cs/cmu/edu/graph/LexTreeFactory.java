package mlsp.cs.cmu.edu.graph;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mlsp.cs.cmu.edu.spellchecker.CharNode;
import mlsp.cs.cmu.edu.spellchecker.CharacterConstants;
import mlsp.cs.cmu.edu.spellchecker.StringGraphFactory;

public class LexTreeFactory implements GraphFactory<Character, String> {

  Graph<Character, String> lexTree;
  String[] processList;
  
  public LexTreeFactory(String... processList) {
    this.processList = processList;
//    List<String> stringSet = Arrays.asList(processList);
//    Set<String> arr = new HashSet<String>();
//    arr.addAll(stringSet);
//    processList = arr.toArray(new String[arr.size()]);
//    lexTree = new StringGraphFactory(processList).buildGraph();
  }
  
//  public LexTreeFactory(Graph<Character, String> graph) {
//    this.lexTree = graph;
//  }

  @Override
  public Graph<Character, String> buildGraph() {
    Node<Character> head = new CharNode(CharacterConstants.BEGIN_CHARACTER.getValue());
    Node<Character> tail = new CharNode(CharacterConstants.END_CHARACTER.getValue());
    Graph<Character, String> G = new Graph<Character, String>(head);
    G.addNode(tail); // tie the tail back to the head.
    G.setTailNode(tail);
    for (String stringGraph : processList) {
      for (Character c : stringGraph.toCharArray()) {
        Node<Character> newNode = new CharNode(c);
        for(Node<Character> node : G.getHeadNode().getSuccessors()) {
          if(node.getValue() == newNode.getValue())
        }
        
      }
    }
    return G;
  }
  
  private void trace(Character[] arr) {
    
  }

}
