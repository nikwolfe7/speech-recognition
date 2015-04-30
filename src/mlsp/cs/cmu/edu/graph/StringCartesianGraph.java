package mlsp.cs.cmu.edu.graph;

import java.util.List;
import java.util.ListIterator;

import org.apache.commons.math3.util.Pair;

/**
 * @author nwolfe
 *
 */
public class StringCartesianGraph extends CartesianGraph<Character, String> {
  
  private PairNode nodePrototype;
  
  public StringCartesianGraph(Graph<Character, String> G1, Graph<Character, String> G2) {
    super(G1, G2);
    this.nodePrototype = new PairNode(null);
  }
  
  @Override
  protected boolean acceptOrRejectNode(Node<Pair<Node<Character>, Node<Character>>> cartNode) {
    // TODO Auto-generated method stub
    return false;
  }
  
  @Override
  protected void pushNodeCosts(Node<Pair<Node<Character>, Node<Character>>> pFrom,
          Node<Pair<Node<Character>, Node<Character>>> pTo, Edge<String> edge) {
    // push the weight out to the node...
    Double pFromCost = pFrom.getCost();
    if (pFromCost == null)
      pFrom.setCost(0.0);
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

  @Override
  protected Edge<String> getEdgeValueAndSetWeights(
          Node<Pair<Node<Character>, Node<Character>>> pFrom,
          Node<Pair<Node<Character>, Node<Character>>> pTo) {
    // FIRST elements are the template
    // SECOND elements are the input
    double weight = pFrom.getDistance(pTo);
    return new Edge<String>(pFrom, pTo, weight);
  }

  @Override
  protected Node<Pair<Node<Character>, Node<Character>>> getCartesianNodeImpl(Pair<Node<Character>, Node<Character>> pair) {
    try {
      PairNode newNode = (PairNode) nodePrototype.clone();
      newNode.setValue(pair);
      return newNode;
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }
    return null;
  }
}
