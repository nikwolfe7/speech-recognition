package mlsp.cs.cmu.edu.spellchecker;

import mlsp.cs.cmu.edu.graph.CartesianNode;
import mlsp.cs.cmu.edu.graph.DistanceCalculator;
import mlsp.cs.cmu.edu.graph.Node;

import org.apache.commons.lang3.tuple.MutablePair;

public class CharacterCartesianNode extends CartesianNode<Character> {

  public CharacterCartesianNode(Node<Character> n1, Node<Character> n2) {
    super(n1, n2);
  }

  @Override
  protected DistanceCalculator<MutablePair<Node<Character>, Node<Character>>> getDistanceStrategy() {
    return CharacterCartesianNodeDistanceStrategy.getInstance();
  }

}
