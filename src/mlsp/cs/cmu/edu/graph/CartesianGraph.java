package mlsp.cs.cmu.edu.graph;

import java.util.Set;

import org.apache.commons.math3.util.Pair;

public abstract class CartesianGraph<N, E> extends Graph<Pair<Node<N>, Node<N>>, E> {

  private Map2D<Node<N>, Node<N>, CartesianNode<N>> indexMapping;

  private CartesianNode<N> getCartesianNode(Node<N> n1, Node<N> n2) {
    if (indexMapping.containsKey(n1, n2)) {
      return indexMapping.get(n1, n2);
    } else {
      Pair<Node<N>, Node<N>> pair = new Pair<Node<N>, Node<N>>(n1, n2);
      CartesianNode<N> nodePair = getCartesianNodeImpl(pair);
      indexMapping.put(n1, n2, nodePair);
      addNode(nodePair);
      return nodePair;
    }
  }

  @SuppressWarnings("unchecked")
  // it's checked
  public CartesianGraph(Graph<N, E> G1, Graph<N, E> G2) {
    super(null);
    indexMapping = new Map2D<Node<N>, Node<N>, CartesianNode<N>>();
    CartesianNode<N> headNode = getCartesianNodeImpl(new Pair<Node<N>, Node<N>>(
            G1.getHead(), G2.getHead()));
    setHeadNode(headNode);
    addNode(headNode);
    indexMapping.put(G1.getHead(), G2.getHead(), headNode);

    // for each node n1 in G1:
    for (Node<N> n1 : G1.getNodes()) {
      // for each node n2 in successors of (n1):
      for (Node<N> n2 : n1.getSuccessors()) {
        // for each node n3 in G2:
        for (Node<N> n3 : G2.getNodes()) {
          // for each node n4 in successors of (n3):
          for (Node<N> n4 : n3.getSuccessors()) {
            // add edge n1,n3 --> n2,n4
            CartesianNode<N> n1n3, n2n4;
            // n1 is the
            n1n3 = getCartesianNode(n1, n3);
            n2n4 = getCartesianNode(n2, n4);
            Edge<E> edge = getEdgeValueAndSetWeights(n1n3, n2n4);
            pushNodeCosts(n1n3, n2n4, edge);
            if (n2 == G1.getTailNode() && n4 == G2.getTailNode()) {
              for (Edge<?> e : n1.getOutgoingEdges()) {
                if (e.getValue() != null) {
                  edge.setValue((E) e.getValue());
                }
              }
              setTailNode(n2n4);
            }
            addEdge(edge);
          }
        }
        //prune(n1);
      }
    }
  }

  private void prune(Node<N> column) {
    Set<Node<N>> colValues = indexMapping.yKeyset(column);
    for(Node<N> node : colValues) {
      CartesianNode<N> cartNode = getCartesianNode(column, node);
      if(!acceptOrRejectNode(cartNode)) {
        
      }
    }
  }

  protected abstract boolean acceptOrRejectNode(CartesianNode<N> cartNode);

  // Assess and push node costs, if any...
  protected abstract void pushNodeCosts(CartesianNode<N> pFrom,
          CartesianNode<N> pTo, Edge<E> edge);

  // Assess the edge penalties, if any...
  protected abstract Edge<E> getEdgeValueAndSetWeights(CartesianNode<N> pFrom,
          CartesianNode<N> pTo);

  // get the subclass implementation of the Node<Pair>
  protected abstract CartesianNode<N> getCartesianNodeImpl(Pair<Node<N>, Node<N>> pair);

}
