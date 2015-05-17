package mlsp.cs.cmu.edu.spellchecker;

import org.apache.commons.lang3.tuple.MutablePair;

import mlsp.cs.cmu.edu.graph.Edge;
import mlsp.cs.cmu.edu.graph.Graph;
import mlsp.cs.cmu.edu.graph.GraphFactory;
import mlsp.cs.cmu.edu.graph.Node;

public class LexTreeFactory implements GraphFactory<Character, String> {

  private String[] processList;

  private boolean CONTINUOUS = false;

  public LexTreeFactory(String... processList) {
    this.processList = processList;
  }

  @Override
  public Graph<Character, String> buildGraph() {
    Node<Character> head = new CharNode(CharacterConstants.BEGIN_CHARACTER.getValue());
    Node<Character> tail = new CharNode(CharacterConstants.END_CHARACTER.getValue());
    Graph<Character, String> G = new Graph<Character, String>(head);
    G.addNode(tail); // tie the tail back to the head.
    G.setTailNode(tail);

    Node<Character> currNode = G.getHeadNode();
    MutablePair<Integer, Node<Character>> pair;
    for (String stringGraph : processList) {
      pair = trace(stringGraph.toCharArray(), 0, G.getHeadNode());
      currNode = pair.getRight();
      for (int i = pair.getLeft(); i < stringGraph.length(); i++) {
        CharNode nextNode = new CharNode(stringGraph.charAt(i));
        Edge<String> newEdge = new Edge<String>(currNode, nextNode);
        G.addEdge(newEdge);
        G.addNode(nextNode);
        currNode = nextNode;
        Edge<String> selfEdge = new Edge<String>(currNode, nextNode);
        G.addEdge(selfEdge);
      }
      // tail node and tie-in...
      Edge<String> lastEdge;
      if (CONTINUOUS)
        lastEdge = new Edge<String>(currNode, head);
      else
        lastEdge = new Edge<String>(currNode, tail);
      lastEdge.setValue(stringGraph);
      G.addEdge(lastEdge);
      currNode = G.getHeadNode(); // reset curr node
    }
    return G;
  }

  private MutablePair<Integer, Node<Character>> trace(char[] cs, int position, Node<Character> node) {
    if (position >= cs.length) {
      return new MutablePair<Integer, Node<Character>>(position, node);
    } else {
      Node<Character> pNode = node;
      for (Node<Character> n : node.getSuccessors()) {
        if (n != node) {
          if (n.getValue() == cs[position]) {
            pNode = n;
            break;
          }
        }
      }
      if (pNode == node) {
        return new MutablePair<Integer, Node<Character>>(position, node);
      } else {
        return trace(cs, (++position), pNode);
      }
    }
  }

}
