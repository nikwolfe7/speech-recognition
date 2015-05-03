package mlsp.cs.cmu.edu.spellchecker;

import mlsp.cs.cmu.edu.graph.CartesianNode;
import mlsp.cs.cmu.edu.graph.CartesianNodeFactory;
import mlsp.cs.cmu.edu.graph.Node;

public class CharacterCartesianNodeFactory implements CartesianNodeFactory<Character> {

  private CharacterCartesianNode nodePrototype = new CharacterCartesianNode(null, null);

  @Override
  public Node<Character> getNewNode(Character value) {
    return new CharNode(value);
  }

  @Override
  public CartesianNode<Character> getNewCartesianNode(Node<Character> n1, Node<Character> n2) {
//    CharacterCartesianNode node =  new CharacterCartesianNode(null, null);
//    node.setLeft(n1);
//    node.setRight(n2);
//    return node;
      CharacterCartesianNode clone = (CharacterCartesianNode) nodePrototype.clone();
      clone.setLeft(n1);
      clone.setRight(n2);
      return clone;
  }
}
