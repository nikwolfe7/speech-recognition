package mlsp.cs.cmu.edu.graph;


public class StringGraphFactory {
  
  public Graph<Character> getStringGraph(String s) {
    for(int i = 0; i < s.length(); i++) {
      Character c = s.charAt(i);
      Node<Character> node = new Node<Character>(c);
      node.addDirectedEdgeTo(node, 0.0); // self loop
  
    }
    
    return null;
  }
  
  

}
