package mlsp.cs.cmu.edu.graph;

public class StringGraphFactory implements GraphFactory<Character, String> {

  private String[] processList;

  public StringGraphFactory(String... processList) {
    this.processList = processList;
  }

  @Override
  public Graph<Character, String> buildGraph() {
    Graph<Character, String> G = new Graph<Character, String>(new CharNode());
    Node<Character> currNode = G.getHead();
    for (String stringGraph : processList) {
      for (Character c : stringGraph.toCharArray()) {
        Node<Character> newNode = new CharNode(c);
        Edge<String> newEdge = new Edge<String>(currNode, newNode);
        G.addEdge(newEdge);
        G.addNode(newNode);
        currNode = newNode; // move along the chain
        Edge<String> selfEdge = new Edge<String>(currNode, newNode);
        G.addEdge(selfEdge);
      }
      Edge<String> lastEdge = new Edge<String>(currNode, G.getHead());
      lastEdge.setValue(stringGraph);
      G.addEdge(lastEdge);
      currNode = G.getHead(); // reset curr node
    }
    return G;
  }
}
