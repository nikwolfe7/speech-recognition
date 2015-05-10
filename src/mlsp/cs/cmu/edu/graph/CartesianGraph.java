package mlsp.cs.cmu.edu.graph;

import java.util.Set;

import org.apache.commons.lang3.tuple.MutablePair;

public abstract class CartesianGraph<N, E> extends Graph<MutablePair<Node<N>, Node<N>>, E> {

  private Map2D<Node<N>, Node<N>, CartesianNode<N>> indexMapping = initiateMapping();

  private CartesianNodeFactory<N> factory;

  public CartesianGraph(CartesianNodeFactory<N> nodeFactory) {
    super(null);
    this.factory = nodeFactory;
    initialize();
  }

  @SuppressWarnings("unchecked")
  public void buildGraph(Graph<N, E> G1, Graph<N, E> G2) {
    tearDown();
    CartesianNode<N> headNode = getCartesianNodeImpl(G1.getHeadNode(), G2.getHeadNode());
    CartesianNode<N> tailNode = getCartesianNodeImpl(G1.getTailNode(), G2.getTailNode());
    setHeadNode(headNode);
    setTailNode(tailNode);
    addNode(headNode);
    addNode(tailNode);
    indexMapping.put(G1.getHeadNode(), G2.getHeadNode(), headNode);
    indexMapping.put(G1.getTailNode(), G2.getTailNode(), tailNode);

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
            addEdge(edge);
            if (n2n4 == getTailNode()) {
              for (Edge<?> e : n1.getIncomingEdges()) {
                if (edge.getValue() == null && e.getValue() != null) {
                  edge.setValue((E) e.getValue());
                }
              }
            }
          }
        }
        // prune(n1);
      }
    }
  }

  private CartesianNode<N> getCartesianNode(Node<N> n1, Node<N> n2) {
    if (indexMapping.containsKey(n1, n2)) {
      return indexMapping.get(n1, n2);
    } else {
      CartesianNode<N> nodePair = getCartesianNodeImpl(n1, n2);
      indexMapping.put(n1, n2, nodePair);
      addNode(nodePair);
      return nodePair;
    }
  }

  private Map2D<Node<N>, Node<N>, CartesianNode<N>> initiateMapping() {
    return new Map2D<Node<N>, Node<N>, CartesianNode<N>>();
  }

  // get the subclass implementation of the Node<MutablePair>
  protected CartesianNode<N> getCartesianNodeImpl(Node<N> n1, Node<N> n2) {
    return factory.getNewCartesianNode(n1, n2);
  }

  // Assess the edge penalties, if any...
  @SuppressWarnings("unchecked")
  protected Edge<E> getEdgeValueAndSetWeights(CartesianNode<N> pFrom, CartesianNode<N> pTo) {
    double weight = pFrom.getDistance(pTo);
    return (Edge<E>) factory.getNewEdge(pFrom, pTo, weight);
  }

  private void recycleNode(CartesianNode<N> node) {
    factory.recycleEdges(node.getOutgoingEdges());
    factory.recycleNode(node);
  }

  // tear down
  private void tearDown() {
    for (CartesianNode<N> node : indexMapping.values())
      recycleNode(node);
    indexMapping.clear();
    destroy();
  }

  private void prune(Node<N> column) {
    Set<Node<N>> colValues = indexMapping.yKeyset(column);
    for (Node<N> node : colValues) {
      CartesianNode<N> cartNode = getCartesianNode(column, node);
      if (!acceptOrRejectNode(cartNode)) {

      }
    }
  }

  // defer pruning decision to subclasses
  protected abstract boolean acceptOrRejectNode(CartesianNode<N> cartNode);

  // Gives subclasses a chance to establish initial state if need be
  protected abstract void initialize();

  // Assess and push node costs, if any...
  protected abstract void pushNodeCosts(CartesianNode<N> pFrom, CartesianNode<N> pTo, Edge<E> edge);

}
