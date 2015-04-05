package mlsp.cs.cmu.edu.graph;

import java.util.Map;

public abstract class CartesianGraph<N, E> {

  private Map<Coordinate, Node<Coordinate>> graphNodes;

  private Node<Coordinate> headNode;

  public class Coordinate {

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
      if (!(obj instanceof CartesianGraph.Coordinate))
        return false;
      @SuppressWarnings("unchecked")
      Coordinate k = (Coordinate) obj;
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
  public Node<Coordinate> getHeadNode() {
    return headNode;
  }

  protected Coordinate getCoordinate(Node<N> n1, Node<N> n2) {
    return new Coordinate(n1, n2);
  }

  public CartesianGraph(Graph<N, E> G1, Graph<N, E> G2) {
    Node<N> headG1 = G1.getHead();
    Node<N> headG2 = G2.getHead();
    Coordinate head = getCoordinate(headG1, headG2);
    headNode = getCoordinateNodeImpl(head);
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
            Node<Coordinate> n1n3, n2n4;
            Coordinate c1 = new Coordinate(n1,n3);
            Coordinate c2 = new Coordinate(n2,n4);
            
            // Get new graph nodes
            if(graphNodes.containsKey(c1)) 
              n1n3 = graphNodes.get(c1);
            else
              n1n3 = getCoordinateNodeImpl(c1);
            
            if(graphNodes.containsKey(c2)) 
              n2n4 = graphNodes.get(c2);
            else
              n2n4 = getCoordinateNodeImpl(c2);
            
            // connect the dots!
            graphNodes.put(c1, n1n3);
            graphNodes.put(c2, n2n4);
            n1n3.addEdge(getTypeEdgeImpl(n2n4));
          }
        }
      }
    }
  }
  
  /* Defer edge type instantiation to subclass */
  protected abstract Edge<E> getTypeEdgeImpl(Node<?> node);
  
  /* Get parameterized Node implemenation */
  protected abstract Node<N> getTypeNodeImpl(Edge<?> edge);
  
  /* Defer node type construction to subclasses */
  protected abstract Node<Coordinate> getCoordinateNodeImpl(Coordinate coordinate);

}
