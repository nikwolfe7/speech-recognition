package mlsp.cs.cmu.edu.spellchecker;

import mlsp.cs.cmu.edu.graph.CartesianGraph;
import mlsp.cs.cmu.edu.graph.CartesianNode;
import mlsp.cs.cmu.edu.graph.CartesianNodeFactory;
import mlsp.cs.cmu.edu.graph.Edge;
import mlsp.cs.cmu.edu.graph.Node;

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
    // push the weight out to the node...
    Double pFromCost = pFrom.getCost();
    if (pFromCost == null)
      pFrom.setCost(0.0);
    double nodeCost = edge.getWeight() + pFrom.getCost();
    Double pToCost = pTo.getCost();
    if (pToCost == null) {
      pTo.setCost(nodeCost);
      pTo.setBackPointer(edge);
    } else if (nodeCost < pTo.getCost()) {
      pTo.setCost(nodeCost);
      pTo.setBackPointer(edge);
    }
  }


  @Override
  protected void initialize() {}

}