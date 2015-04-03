package mlsp.cs.cmu.edu.graph;

import java.util.Map;

public abstract class CartesianGraph<N, E> {

  private Map<Coordinate<N, E>, Node<Coordinate<N, E>>> graphNodes;

  private Node<Coordinate<N, E>> headNode;

  public class Coordinate<N, E> {
    public Node<N> X;

    public Node<N> Y;

    public Coordinate(Node<N> x, Node<N> y) {
      this.X = x;
      this.Y = y;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (!(obj instanceof Coordinate))
        return false;
      @SuppressWarnings("unchecked")
      Coordinate<N, E> k = (Coordinate<N, E>) obj;
      return X == k.X && Y == k.Y;
    }

    @Override
    public int hashCode() {
      return 13 * X.hashCode() * Y.hashCode();
    }
  }

  /**
   * @return the headNode
   */
  public Node<Coordinate<N, E>> getHeadNode() {
    return headNode;
  }
  
  protected Coordinate<N, E> getCoordinate(Node<N> n1, Node<N> n2) {
    return new Coordinate<N, E>(n1, n2);
  }

  public CartesianGraph(Graph<N, E> G1, Graph<N, E> G2) {
    Node<N> headG1 = G1.getHead();
    Node<N> headG2 = G2.getHead();
    Coordinate<N, E> head = getCoordinate(headG1, headG2);
    headNode = getCoordinateNodeImpl(head);
    graphNodes.put(head, headNode);

    Iterable<N> graph1 = G1.getNodes();
    for (N g1Key : graph1) {
      // for each node n1 in G1:
      Node<N> n1 = G1.getNode(g1Key);

      for (Edge<?> e1 : n1.getOutgoingEdges()) {
        // for each node n2 in successors of (n1):
        Node<?> n2 = e1.getNodePointer();

        Iterable<N> graph2 = G2.getNodes();
        for (N g2Key : graph2) {

          // for each node n3 in G2:
          Node<N> n3 = G2.getNode(g2Key);
          for (Edge<?> e2 : n3.getOutgoingEdges()) {

            // for each node n4 in successors of (n3):
            Node<?> n4 = e2.getNodePointer();

            // add edge n1,n3 --> n2,n4
            Coordinate<N, E> c1 = getCoordinate(n1, n3);
            Node<Coordinate<N, E>> node1 = getCoordinateNodeImpl(c1);

            //Coordinate<N, E> c2 = getCoordinate(n2, n4);

          }
        }
      }
    }

  }

  

  /* Defer node type construction to subclasses */
  protected abstract Node<Coordinate<N, E>> getCoordinateNodeImpl(Coordinate<?,?> coordinate);

}
