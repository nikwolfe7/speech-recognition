package mlsp.cs.cmu.edu.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Graph<N, E> {

  private List<Edge<E>> graphEdges;
  private Map<N, Node<N>> graphNodes;
  private final Node<N> headNode;
  
  public Graph(Node<N> head) {
    this.headNode = head;
    this.graphEdges = new ArrayList<Edge<E>>();
    this.graphNodes = new HashMap<N, Node<N>>();
    addNode(headNode);
  }

  public Iterable<Node<N>> getNodes() {
    return graphNodes.values();
  }

  public Node<N> getHead() {
    return headNode;
  }
  
  public void addNode(Node<N> node) {
    if(graphNodes.containsKey(node.getValue())) {
      Node<N> curr = graphNodes.get(node.getValue());
      for(Edge<?> edge : node.getOutgoingEdges())
        curr.addEdge(edge);
      for(Edge<?> edge : node.getIncomingEdges()) 
        curr.registerIncomingEdge(edge);
    } else {
      graphNodes.put(node.getValue(), node);
    }
  }

  public List<Edge<E>> getGraphEdges() {
    return graphEdges;
  }

  public abstract List<Edge<E>> getNodeEdges(Node<N> node);

}
