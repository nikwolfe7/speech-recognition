package mlsp.cs.cmu.edu.spellchecker;

import mlsp.cs.cmu.edu.graph.AbstractCartesianNodeFactory;
import mlsp.cs.cmu.edu.graph.CartesianNode;
import mlsp.cs.cmu.edu.graph.Edge;
import mlsp.cs.cmu.edu.graph.Node;

public class CharacterCartesianNodeFactory extends AbstractCartesianNodeFactory<Character> {

  @Override
  public Node<Character> getNewNode(Character value) {
    return new CharNode(value);
  }

  @Override
  protected CartesianNode<Character> getNodePrototype() {
    char beginChar = CharacterConstants.BEGIN_CHARACTER.getValue();
    return new CharacterCartesianNode(getNewNode(beginChar), getNewNode(beginChar));
  }

  @Override
  protected int getMinCapacity() {
    return 10000;
  }

  @Override
  protected int getIncreaseCapacity() {
    return 1000000;
  }

  @Override
  protected Edge<?> getEdgePrototype() {
    return new Edge<String>(getNodePrototype(), getNodePrototype());
  }
  
}