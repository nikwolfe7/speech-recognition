package mlsp.cs.cmu.edu.spellchecker;

import mlsp.cs.cmu.edu.graph.AbstractCartesianGraphFactory;
import mlsp.cs.cmu.edu.graph.CartesianGraph;
import mlsp.cs.cmu.edu.graph.CartesianNodeFactory;

public class StringCartesianGraphFactory extends AbstractCartesianGraphFactory<Character,String> {

  public StringCartesianGraphFactory() {
    super(new CharacterCartesianNodeFactory());
  }

  @Override
  protected CartesianGraph<Character, String> getGraphImpl(CartesianNodeFactory<Character> factory) {
    return new StringCartesianGraph(factory);
  }

}
