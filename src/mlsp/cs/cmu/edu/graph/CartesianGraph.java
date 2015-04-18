package mlsp.cs.cmu.edu.graph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Pair;

public abstract class CartesianGraph<N, E> {

  private Map<Pair<Node<N>,Node<N>>, Node<Pair<Node<N>,Node<N>>>> graphNodes;
  
  private List<Edge<E>> graphEdges;

  private Node<Pair<Node<N>,Node<N>>> headNode;

  /**
   * @return the headNode
   */
  public Node<Pair<Node<N>,Node<N>>> getHeadNode() {
    return headNode;
  }

  protected Pair<Node<N>,Node<N>> getPair(Node<N> n1, Node<N> n2) {
    return new Pair<Node<N>,Node<N>>(n1, n2);
  }

  public CartesianGraph(Graph<N, E> G1, Graph<N, E> G2) {
    graphNodes = new HashMap<Pair<Node<N>,Node<N>>, Node<Pair<Node<N>,Node<N>>>>();
    Node<N> headG1 = G1.getHead();
    Node<N> headG2 = G2.getHead();
    Pair<Node<N>,Node<N>> head = getPair(headG1, headG2);
    headNode = getPairNodeImpl(head);
    graphNodes.put(head, headNode);

    // for each node n1 in G1:
    for(Node<N> n1 : G1.getNodes()) {
      // for each node n2 in successors of (n1):
      for(Edge<?> e1 : n1.getOutgoingEdges()) {
        Node<N> n2 = getTypeNodeImpl(e1);
        // for each node n3 in G2:
        for(Node<N> n3 : G2.getNodes()) {
          // for each node n4 in successors of (n3):
          for(Edge<?> e2 : n3.getOutgoingEdges()) {
            Node<N> n4 = getTypeNodeImpl(e2);
            // add edge n1,n3 --> n2,n4
            Node<Pair<Node<N>,Node<N>>> n1n3, n2n4;
            Pair<Node<N>,Node<N>> c1 = getPair(n1, n3);
            Pair<Node<N>,Node<N>> c2 = getPair(n2, n4);
            
            // Get new graph nodes
            if(graphNodes.containsKey(c1)) 
              n1n3 = graphNodes.get(c1);
            else
              n1n3 = getPairNodeImpl(c1);
            
            if(graphNodes.containsKey(c2)) 
              n2n4 = graphNodes.get(c2);
            else
              n2n4 = getPairNodeImpl(c2);
            
            // connect the dots!
            graphNodes.put(c1, n1n3);
            graphNodes.put(c2, n2n4);
            addEdge(getTypeEdgeImpl(n1n3, n2n4));
          }
        }
      }
    }
  }
  
  private void addEdge(Edge<E> edge) {
    graphEdges.add(edge);
  }
  
  /* Defer edge type instantiation to subclass */
  protected abstract Edge<E> getTypeEdgeImpl(Node<?> from, Node<?> to);
  
  /* Get parameterized Node implemenation */
  protected abstract Node<N> getTypeNodeImpl(Edge<?> edge);
  
  /* Defer node type construction to subclasses */
  protected abstract Node<Pair<Node<N>,Node<N>>> getPairNodeImpl(Pair<Node<N>,Node<N>> Pair);

  /**
   * @return the graphEdges
   */
  public List<Edge<E>> getGraphEdges() {
    return graphEdges;
  }

  

}
