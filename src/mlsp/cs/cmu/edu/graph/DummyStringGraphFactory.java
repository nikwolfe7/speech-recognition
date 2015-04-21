package mlsp.cs.cmu.edu.graph;

public class DummyStringGraphFactory extends StringGraphFactory {
  
  private String[] processList;

  public DummyStringGraphFactory(String... processList) {
    this.processList = processList;
  }

  @Override
  public Graph<Character, String> buildGraph() {
    if(processList[0] == "abc") {
      Node<Character> a = new CharNode('a');
      Node<Character> b = new CharNode('b');
      Node<Character> c = new CharNode('c');
      Graph<Character, String> G = new Graph<Character, String>(a);
      G.addEdge(new Edge<String>(a, c));
      G.addEdge(new Edge<String>(a, a));
      G.addEdge(new Edge<String>(b, a));
      G.addEdge(new Edge<String>(c, b));
      G.addNode(b);
      G.addNode(c);
      return G;
      
    } else if(processList[0] == "123") {
      Node<Character> one = new CharNode('1');
      Node<Character> two = new CharNode('2');
      Node<Character> three = new CharNode('3');
      Graph<Character, String> G = new Graph<Character, String>(one);
      G.addEdge(new Edge<String>(one, one));
      G.addEdge(new Edge<String>(two, one));
      G.addEdge(new Edge<String>(three, one));
      G.addEdge(new Edge<String>(two, three));
      G.addEdge(new Edge<String>(three, two));
      G.addNode(two);
      G.addNode(three);
      return G;
    }
    return null;
  }

}
