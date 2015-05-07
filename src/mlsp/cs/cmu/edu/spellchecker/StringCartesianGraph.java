package mlsp.cs.cmu.edu.spellchecker;

import mlsp.cs.cmu.edu.graph.CartesianGraph;
import mlsp.cs.cmu.edu.graph.CartesianNode;
import mlsp.cs.cmu.edu.graph.Edge;
import mlsp.cs.cmu.edu.graph.Node;

/**
 * @author nwolfe
 *
 */
public class StringCartesianGraph extends CartesianGraph<Character, String> {

  private CharacterCartesianNodeFactory factory;

  @Override
  protected void setup() {
    this.factory = new CharacterCartesianNodeFactory();
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
  protected Edge<String> getEdgeValueAndSetWeights(CartesianNode<Character> pFrom,
          CartesianNode<Character> pTo) {
    // FIRST elements are the template
    // SECOND elements are the input
    double weight = pFrom.getDistance(pTo);
    return new Edge<String>(pFrom, pTo, weight);
  }

  @Override
  protected CartesianNode<Character> getCartesianNodeImpl(Node<Character> n1, Node<Character> n2) {
    CartesianNode<Character> n00b = factory.getNewCartesianNode(n1, n2);
    return n00b;
  }
}
