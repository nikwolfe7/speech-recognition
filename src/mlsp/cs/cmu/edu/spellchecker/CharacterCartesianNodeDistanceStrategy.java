package mlsp.cs.cmu.edu.spellchecker;

import mlsp.cs.cmu.edu.graph.DistanceCalculator;
import mlsp.cs.cmu.edu.graph.Node;

import org.apache.commons.math3.util.Pair;

public class CharacterCartesianNodeDistanceStrategy implements DistanceCalculator<Pair<Node<Character>, Node<Character>>> {

  private static CharacterCartesianNodeDistanceStrategy singleton = null;
  
  private CharacterCartesianNodeDistanceStrategy() {}
  
  public static CharacterCartesianNodeDistanceStrategy getInstance() {
    if(singleton == null) {
      singleton = new CharacterCartesianNodeDistanceStrategy();
    } 
    return singleton;
  }

  @Override
  public double getDifference(Node<Pair<Node<Character>, Node<Character>>> n1,
          Node<Pair<Node<Character>, Node<Character>>> n2) {
    double INFINITY = 1e100; 
    Character xToValue, yToValue, xFromValue, yFromValue;
    xFromValue = n1.getValue().getFirst().getValue();
    yFromValue = n1.getValue().getSecond().getValue();
    xToValue = n2.getValue().getFirst().getValue();
    yToValue = n2.getValue().getSecond().getValue();
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
  
  private boolean checkIsNonEmittingNode(Node<Pair<Node<Character>, Node<Character>>> node) {
    Character xValue = node.getValue().getFirst().getValue();
    Character yValue = node.getValue().getSecond().getValue();
    char end = CharacterConstants.END_CHARACTER.getValue();
    char begin = CharacterConstants.BEGIN_CHARACTER.getValue();
    if (xValue.equals(yValue) && (xValue.equals(end) || xValue.equals(begin)))
      return false;
    else
      return xValue.equals(end) || yValue.equals(end) || xValue.equals(begin)
              || yValue.equals(begin);
  }
  
  private boolean checkPairContainsNonEmittingNode(Node<Pair<Node<Character>, Node<Character>>> n1,
          Node<Pair<Node<Character>, Node<Character>>> n2) {
    return checkIsNonEmittingNode(n1) || checkIsNonEmittingNode(n2);
  }

  
}
