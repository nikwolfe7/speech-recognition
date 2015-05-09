package mlsp.cs.cmu.edu.spellchecker;

import mlsp.cs.cmu.edu.graph.Edge;
import mlsp.cs.cmu.edu.graph.Graph;
import mlsp.cs.cmu.edu.graph.GraphFactory;
import mlsp.cs.cmu.edu.graph.Node;

public class StringGraphFactory implements GraphFactory<Character, String> {

  private String[] processList;

  public StringGraphFactory(String... processList) {
    this.processList = processList;
  }

  @Override
  public Graph<Character, String> buildGraph() {
    Node<Character> head = new CharNode(CharacterConstants.BEGIN_CHARACTER.getValue());
    Node<Character> tail = new CharNode(CharacterConstants.END_CHARACTER.getValue());
    Graph<Character, String> G = new Graph<Character, String>(head);
    G.addNode(tail); // tie the tail back to the head.
    G.setTailNode(tail);
    // G.addEdge(new Edge<String>(tail, G.getHead()));
    Node<Character> currNode = G.getHeadNode();
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
      // tail node and tie-in...
      Edge<String> lastEdge = new Edge<String>(currNode, tail);
      lastEdge.setValue(stringGraph);
      G.addEdge(lastEdge);
      currNode = G.getHeadNode(); // reset curr node
    }
    return G;
  }
}
