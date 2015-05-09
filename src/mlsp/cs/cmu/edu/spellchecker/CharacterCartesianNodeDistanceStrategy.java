package mlsp.cs.cmu.edu.spellchecker;

import mlsp.cs.cmu.edu.graph.DistanceCalculator;
import mlsp.cs.cmu.edu.graph.Node;

import org.apache.commons.lang3.tuple.MutablePair;

public class CharacterCartesianNodeDistanceStrategy implements
        DistanceCalculator<MutablePair<Node<Character>, Node<Character>>> {

  private static CharacterCartesianNodeDistanceStrategy singleton = null;

  private final double INFINITY = Double.MAX_VALUE;

  private final char begin = CharacterConstants.BEGIN_CHARACTER.getValue();

  private final char end = CharacterConstants.END_CHARACTER.getValue();

  private CharacterCartesianNodeDistanceStrategy() {
  }

  public static CharacterCartesianNodeDistanceStrategy getInstance() {
    if (singleton == null) {
      singleton = new CharacterCartesianNodeDistanceStrategy();
    }
    return singleton;
  }
  
  private boolean isEdge(char x, char y) {
    return (x != y) && (x == begin || x == end || y == begin || y == end);
  }
  
  @Override
  public double getDifference(Node<MutablePair<Node<Character>, Node<Character>>> n1,
          Node<MutablePair<Node<Character>, Node<Character>>> n2) {
    char xToValue, yToValue, xFromValue, yFromValue;
    xFromValue = n1.getValue().getLeft().getValue();
    yFromValue = n1.getValue().getRight().getValue();
    xToValue = n2.getValue().getLeft().getValue();
    yToValue = n2.getValue().getRight().getValue();
    /**
     * Self-transition
     */
    if (n1 == n2)
      return INFINITY;
    else {
      /**
       * Edge case: return infinity
       */
      if(isEdge(xFromValue, yFromValue) || (isEdge(xToValue, yToValue))) 
        return INFINITY;
      /**
       * Destination node matches
       */
      if (yToValue == xToValue)
        return 0;
      /**
       * Something else
       */
      else
        return 1;
    }

  }
}
