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
  }

  public CharNode(Character value, double cost) {
    super(value, cost);
  }

  @Override
  @SuppressWarnings("unchecked") // believe me it's checked.
  protected Node<Character> retrievePredecessorFromEdge(Edge<?> edge) {
    Object o = edge.getNodePredecessor().getValue();
    if (o instanceof Character || o == null)
      return (Node<Character>) edge.getNodePredecessor();
    else
      return null;
  }
  
  @Override
  @SuppressWarnings("unchecked") // believe me it's checked.
  protected Node<Character> retrieveSuccessorFromEdge(Edge<?> edge) {
    Object o = edge.getNodeSuccessor().getValue();
    if (o instanceof Character || o == null)
      return (Node<Character>) edge.getNodeSuccessor();
    else
      return null;
  }

  @Override
  protected DistanceCalculator<Character> getDistanceStrategy() {
    return new DistanceCalculator<Character>() {
      @Override
      public double getDifference(Node<Character> n1, Node<Character> n2) {
        return n1.getValue().compareTo(n2.getValue());
      }
    };
  }

  @Override
  public String toString() {
    if(getValue() == CharacterConstants.BEGIN_CHARACTER.getValue())
      return "HEAD";
    else if (getValue() == CharacterConstants.END_CHARACTER.getValue())
      return "TAIL";
    else
      return "" + getValue();
  }

}
