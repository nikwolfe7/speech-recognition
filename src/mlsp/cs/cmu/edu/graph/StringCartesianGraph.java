package mlsp.cs.cmu.edu.graph;

/**
 * @author nwolfe
 *
 */
public class StringCartesianGraph extends CartesianGraph<Character, String> {

  public StringCartesianGraph(Graph<Character, String> G1, Graph<Character, String> G2) {
    super(G1, G2);
  }

  @Override
  protected Edge<String> getTypeEdgeImpl(Node<?> node) {
    return new StringEdge(node);
  }

  @Override
  protected Node<Character> getTypeNodeImpl(Edge<?> edge) {
    if (edge instanceof StringEdge) {
      StringEdge sedge = (StringEdge) edge;
      if (sedge.getNodePointer() instanceof CharNode) {
        return (CharNode) sedge.getNodePointer();
      }
    }
    System.out.println("Type cast failed! Check your graph!!");
    return null;
  }

  @Override
  protected Node<CartesianGraph<Character, String>.Coordinate> getCoordinateNodeImpl(
          CartesianGraph<Character, String>.Coordinate coordinate) {
    return new CoordinateNode(coordinate);
  }

  /**
   * Cartesian Node implementation, i.e. a node formed from two
   * previous Nodes...
   * 
   * @author nwolfe
   *
   */
  private class CoordinateNode extends Node<CartesianGraph<Character, String>.Coordinate> {

    public CoordinateNode(CartesianGraph<Character, String>.Coordinate value) {
      super(value);
    }

    public CoordinateNode(CartesianGraph<Character, String>.Coordinate value, double cost) {
      super(value, cost);
    }

    @Override
    public double getDifference(Node<CartesianGraph<Character, String>.Coordinate> node) {
      // TODO Auto-generated method stub
      return 0;
    }

  }

}
