package mlsp.cs.cmu.edu.graph;

import org.apache.commons.math3.util.Pair;

public abstract class CartesianGraph<N, E> extends Graph<Pair<Node<N>, Node<N>>, E> {

  public CartesianGraph(Graph<N, E> G1, Graph<N, E> G2) {
    super(null);
    setHeadNode(getCartesianNode(G1.getHead(), G2.getHead()));

    // for each node n1 in G1:
    for (Node<N> n1 : G1.getNodes()) {
      // for each node n2 in successors of (n1):
      for (Node<N> n2 : n1.getSuccessors()) {
        // for each node n3 in G2:
        for (Node<N> n3 : G2.getNodes()) {
          // for each node n4 in successors of (n3):
          for (Node<N> n4 : n3.getSuccessors()) {
            // add edge n1,n3 --> n2,n4
            Node<Pair<Node<N>, Node<N>>> n1n3, n2n4;
            n1n3 = getCartesianNode(n1, n3);
            n2n4 = getCartesianNode(n2, n4);
            addEdge(new Edge<E>(n1n3, n2n4));
            addNode(n1n3);
            addNode(n2n4);
          }
        }
      }
    }
  }

  protected abstract Node<Pair<Node<N>, Node<N>>> getCartesianNodeImpl(Pair<Node<N>, Node<N>> pair);

  private Node<Pair<Node<N>, Node<N>>> getCartesianNode(Node<N> n1, Node<N> n2) {
    Pair<Node<N>, Node<N>> pair = new Pair<Node<N>, Node<N>>(n1, n2);
    if (graphNodes.containsKey(pair))
      return graphNodes.get(pair);
    else
      return getCartesianNodeImpl(pair);
  }
  
}
