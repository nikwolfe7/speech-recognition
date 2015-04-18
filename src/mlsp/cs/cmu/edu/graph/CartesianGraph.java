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

  public CartesianGraph(Graph<N, E> G1, Graph<N, E> G2) {
    graphNodes = new HashMap<Pair<Node<N>,Node<N>>, Node<Pair<Node<N>,Node<N>>>>();
    Node<N> headG1 = G1.getHead();
    Node<N> headG2 = G2.getHead();
    headNode = getNodePairImpl(headG1, headG2);
    addNodeToGraph(headNode);

    // for each node n1 in G1:
    for(Node<N> n1 : G1.getNodes()) {
      // for each node n2 in successors of (n1):
      for(Node<N> n2 : n1.getSuccessors()) {
        // for each node n3 in G2:
        for(Node<N> n3 : G2.getNodes()) {
          // for each node n4 in successors of (n3):
          for(Node<N> n4 : n3.getSuccessors()) {
            // add edge n1,n3 --> n2,n4
            Node<Pair<Node<N>,Node<N>>> n1n3, n2n4;
            n1n3 = getNodePairImpl(n1, n3);
            n2n4 = getNodePairImpl(n2, n4);
            Edge<E> edge = new Edge<E>(n1n3, n2n4);
            
          }
        }
      }
    }
  }
  
  private void addNodeToGraph(Node<Pair<Node<N>,Node<N>>> newNode) {
    Pair<Node<N>,Node<N>> c = newNode.getValue();
    // Get new graph nodes
    if(graphNodes.containsKey(c)) 
      n1n3 = graphNodes.get(c);
    else
      n1n3 = getNodePairImpl(c);
    
    // connect the dots!
    graphNodes.put(c, n1n3);
    graphNodes.put(c2, n2n4);
  }
  
  protected abstract Node<Pair<Node<N>,Node<N>>> getNodePairImpl(Node<N> n1, Node<N> n2);

  /**
   * @return the graphEdges
   */
  public List<Edge<E>> getGraphEdges() {
    return graphEdges;
  }

}
