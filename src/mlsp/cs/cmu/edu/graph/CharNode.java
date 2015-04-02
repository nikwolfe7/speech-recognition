/**
 * 
 */
package mlsp.cs.cmu.edu.graph;

/**
 * @author nwolfe
 *
 */
public class CharNode extends Node<Character> {

  public CharNode(Character value) {
    super(value);
    // TODO Auto-generated constructor stub
  }

  public CharNode(Character value, double cost) {
    super(value, cost);
    // TODO Auto-generated constructor stub
  }

  @Override
  public double getDifference(Node<Character> node) {
    Character nodeChar = node.getValue();
    return getValue().compareTo(nodeChar);
  }

}
