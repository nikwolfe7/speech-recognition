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
    return new CharacterCartesianNode(getNewNode(null), getNewNode(null));
  }

  @Override
  protected int getMinCapacity() {
    return 100;
  }

  @Override
  protected Edge<?> getEdgePrototype() {
   return new Edge<String>(getNodePrototype(), getNodePrototype());
  }

  @Override
  protected int getIncreaseNodeCapacity() {
    return 10000;
  }

  @Override
  protected int getIncreaseEdgeCapacity() {
    return 10000;
  }

}