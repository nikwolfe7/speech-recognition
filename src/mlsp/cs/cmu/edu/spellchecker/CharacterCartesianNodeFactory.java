package mlsp.cs.cmu.edu.spellchecker;

import mlsp.cs.cmu.edu.graph.CartesianNode;
import mlsp.cs.cmu.edu.graph.CartesianNodeFactory;
import mlsp.cs.cmu.edu.graph.Node;
import mlsp.cs.cmu.edu.graph.NodeFactory;

import org.apache.commons.math3.util.Pair;

public class CharacterCartesianNodeFactory implements CartesianNodeFactory<Character> {

  private static CartesianNode<Character> nodePrototype = new CharacterCartesianNode(null, null);

  private static CharacterCartesianNodeFactory singleton = null;

  private CharacterCartesianNodeFactory() {}

  public static CharacterCartesianNodeFactory getInstance() {
    if (singleton == null) {
      singleton = new CharacterCartesianNodeFactory();
    }
    return singleton;
  }

  @Override
  public Node<Character> getNewNode(Character value) {
    return new CharNode(value);
  }

  @SuppressWarnings("unchecked")
  @Override
  public CartesianNode<Character> getNewCartesianNode(Node<Character> n1, Node<Character> n2) {
    try {
      CartesianNode<Character> clone = (CartesianNode<Character>) nodePrototype.clone();
      clone.setValue(n1, n2);
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }
    return null;
  }

  

  
  //  try {
  //  CharacterCartesianNode node = (CharacterCartesianNode) nodePrototype.clone();
  //  node.setValue(value);
  //  return node;
  //} catch (CloneNotSupportedException e) {
  //  e.printStackTrace();
  //}
}
