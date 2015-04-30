package mlsp.cs.cmu.edu.spellchecker;

import mlsp.cs.cmu.edu.graph.CartesianNode;
import mlsp.cs.cmu.edu.graph.DistanceCalculator;
import mlsp.cs.cmu.edu.graph.Node;

import org.apache.commons.math3.util.Pair;

public class CharacterCartesianNode extends CartesianNode<Character> {

  public CharacterCartesianNode(Pair<Node<Character>, Node<Character>> value) {
    super(value);
  }

  @Override
  protected DistanceCalculator<Pair<Node<Character>, Node<Character>>> getDistanceStrategy() {
    return CharacterCartesianNodeDistanceStrategy.getInstance();
  }

}
