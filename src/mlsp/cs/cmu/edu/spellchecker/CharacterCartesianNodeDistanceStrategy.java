package mlsp.cs.cmu.edu.spellchecker;

import mlsp.cs.cmu.edu.graph.DistanceCalculator;
import mlsp.cs.cmu.edu.graph.Node;

import org.apache.commons.lang3.tuple.MutablePair;

public class CharacterCartesianNodeDistanceStrategy implements DistanceCalculator<MutablePair<Node<Character>, Node<Character>>> {

  private static CharacterCartesianNodeDistanceStrategy singleton = null;
  
  private CharacterCartesianNodeDistanceStrategy() {}
  
  public static CharacterCartesianNodeDistanceStrategy getInstance() {
    if(singleton == null) {
      singleton = new CharacterCartesianNodeDistanceStrategy();
    } 
    return singleton;
  }

  @Override
  public double getDifference(Node<MutablePair<Node<Character>, Node<Character>>> n1,
          Node<MutablePair<Node<Character>, Node<Character>>> n2) {
    double INFINITY = 1e100; 
    Character xToValue, yToValue, xFromValue, yFromValue;
    xFromValue = n1.getValue().getLeft().getValue();
    yFromValue = n1.getValue().getRight().getValue();
    xToValue = n2.getValue().getLeft().getValue();
    yToValue = n2.getValue().getRight().getValue();
    if (n1 == n2) {
      return INFINITY;
    } else if (xToValue.equals(yToValue)) {
      if (!checkIsNonEmittingNode(n1))
        return 0;
      else
        return INFINITY;
    } else if (xFromValue.equals(yFromValue)) {
      if (!checkIsNonEmittingNode(n2)) {
        if (xToValue.equals(yToValue))
          return 0;
        else
          return 1;
      } else {
        return INFINITY;
      }
    } else if (xFromValue.equals(xToValue) || yFromValue.equals(yToValue)) {
      if (checkPairContainsNonEmittingNode(n1, n2))
        return INFINITY;
      else
        return 1;
    } else if (checkPairContainsNonEmittingNode(n1, n2)) {
      return INFINITY;
    } else {
      return 1;
    }
  }
  
  private boolean checkIsNonEmittingNode(Node<MutablePair<Node<Character>, Node<Character>>> node) {
    Character xValue = node.getValue().getLeft().getValue();
    Character yValue = node.getValue().getRight().getValue();
    char end = CharacterConstants.END_CHARACTER.getValue();
    char begin = CharacterConstants.BEGIN_CHARACTER.getValue();
    if (xValue.equals(yValue) && (xValue.equals(end) || xValue.equals(begin)))
      return false;
    else
      return xValue.equals(end) || yValue.equals(end) || xValue.equals(begin)
              || yValue.equals(begin);
  }
  
  private boolean checkPairContainsNonEmittingNode(Node<MutablePair<Node<Character>, Node<Character>>> n1,
          Node<MutablePair<Node<Character>, Node<Character>>> n2) {
    return checkIsNonEmittingNode(n1) || checkIsNonEmittingNode(n2);
  }

  
}
