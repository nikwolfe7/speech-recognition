package mlsp.cs.cmu.edu.spellchecker;

import mlsp.cs.cmu.edu.graph.CartesianNode;
import mlsp.cs.cmu.edu.graph.Node;
import mlsp.cs.cmu.edu.graph.NodeFactory;

import org.apache.commons.math3.util.Pair;

public class CharacterCartesianNodeFactory implements NodeFactory<Character> {

  private static CartesianNode<Character> nodePrototype = new CharacterCartesianNode(null);

  private static CharacterCartesianNodeFactory singleton = null;

  private CharacterCartesianNodeFactory() {}

  public static CharacterCartesianNodeFactory getInstance() {
    if (singleton == null) {
      singleton = new CharacterCartesianNodeFactory();
    }
    return singleton;
  }
  
  public CartesianNode<Character> getNewCartesianNode(Node<Character> n1, Node<Character> n2) {
//  try {
    //  CharacterCartesianNode node = (CharacterCartesianNode) nodePrototype.clone();
    //  node.setValue(value);
    //  return node;
    //} catch (CloneNotSupportedException e) {
    //  e.printStackTrace();
    //}
    return null;
  }
  

}
