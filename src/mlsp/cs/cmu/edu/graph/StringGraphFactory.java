package mlsp.cs.cmu.edu.graph;

public class StringGraphFactory implements GraphFactory<Character, String> {

  private String[] processList;

  public StringGraphFactory(String... processList) {
    this.processList = processList;
  }

  @Override
  public Graph<Character, String> buildGraph() {
    CharNode head = new CharNode(null);
    Graph<Character, String> G = new StringGraph(head); 
    CharNode currNode = head;
    for (String stringGraph : processList) {
      for(Character c : stringGraph.toCharArray()) {
        CharNode newNode = new CharNode(c);
        StringEdge newEdge = new StringEdge(currNode, newNode);
      }
    }
    return G;
  }
}
