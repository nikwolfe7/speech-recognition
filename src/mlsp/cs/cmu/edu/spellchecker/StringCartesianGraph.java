package mlsp.cs.cmu.edu.spellchecker;

import mlsp.cs.cmu.edu.graph.CartesianGraph;
import mlsp.cs.cmu.edu.graph.CartesianNode;
import mlsp.cs.cmu.edu.graph.CartesianNodeFactory;
import mlsp.cs.cmu.edu.graph.Edge;

/**
 * @author nwolfe
 *
 */
public class StringCartesianGraph extends CartesianGraph<Character, String> {

  private final double INFINITY = Double.MAX_VALUE;

  private final char begin = CharacterConstants.BEGIN_CHARACTER.getValue();

  private final char end = CharacterConstants.END_CHARACTER.getValue();

  public StringCartesianGraph(CartesianNodeFactory<Character> nodeFactory) {
    super(nodeFactory);
  }

  @Override
  protected boolean acceptOrRejectNode(CartesianNode<Character> cartNode) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  protected void pushNodeCosts(CartesianNode<Character> pFrom, CartesianNode<Character> pTo,
          Edge<String> edge) {
    if (pFrom.getCost() == null) {
        pFrom.setCost(0.0);
    }
    double pathCost = edge.getWeight() + pFrom.getCost();
    if (pTo.getCost() == null || pTo.getCost() > pathCost) {
      pTo.setCost(pathCost);
      pTo.setBackPointer(edge);
    }
  }

  @Override
  protected void initialize() {
  }

}