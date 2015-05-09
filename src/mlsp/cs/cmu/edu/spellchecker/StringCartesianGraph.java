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
    // set the first column weights
    double INFINITY = 1e100;
    // push the weight out to the node...
    Double pFromCost = pFrom.getCost();
    if (pFromCost == null) {
      // if (checkIsNonEmittingNode(pFrom)) {
      // if (pFrom.getValue().getLeft().getValue().equals(pFrom.getValue().getRight().getValue()))
      // pFrom.setCost(0.0);
      // else
      // pFrom.setCost(INFINITY);
      // } else {
      pFrom.setCost(0.0);
      // }
    }
    double nodeCost = edge.getWeight() + pFrom.getCost();
    Double pToCost = pTo.getCost();
    if (pToCost == null) {
      pTo.setCost(nodeCost);
      pTo.setBackPointer(edge);
    } else if (nodeCost <= pTo.getCost()) {
      pTo.setCost(nodeCost);
      pTo.setBackPointer(edge);
    }
  }

  private boolean checkIsNonEmittingNode(CartesianNode<Character> node) {
    char xValue = node.getValue().getLeft().getValue();
    char yValue = node.getValue().getRight().getValue();
    char end = CharacterConstants.END_CHARACTER.getValue();
    char begin = CharacterConstants.BEGIN_CHARACTER.getValue();
    if ((xValue == yValue) && ((xValue == end) || (xValue == begin)))
      return true;
    else
      return xValue == end || yValue == end || xValue == begin || yValue == begin;
  }

  @Override
  protected void initialize() {
  }

}