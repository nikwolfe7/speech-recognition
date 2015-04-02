package mlsp.cs.cmu.edu.graph;

import java.util.List;

public class StringGraphFactory implements GraphFactory<Character, String> {

  private List<String> processList;

  public StringGraphFactory(List<String> processList) {
    this.processList = processList;
  }

  @Override
  public Graph<Character, String> buildGraph() {
    CharNode head = new CharNode(null);
    Graph<Character, String> G = new StringGraph(head); 
    for (String stringGraph : processList) {
      CharNode pointer = head;
      for(Character c : stringGraph.toCharArray()) {
        CharNode node = new CharNode(c);
        node.addEdge(new StringEdge(node)); // self loop
        pointer.addEdge(new StringEdge(node)); // pointer from previous
        pointer = node; // update pointer
        G.addNode(pointer);
      }
      StringEdge last = new StringEdge(head);
      last.setValue(stringGraph);
      pointer.addEdge(last);
    }
    return G;
  }
}
