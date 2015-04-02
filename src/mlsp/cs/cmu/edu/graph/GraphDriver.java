package mlsp.cs.cmu.edu.graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GraphDriver {

  public static void main(String[] args) throws FileNotFoundException {
    
    List<String> dictionary = new ArrayList<String>();
    Scanner scn = new Scanner(new File("./dict/dict_80k.txt"));
    while(scn.hasNextLine()) {
      dictionary.add(scn.nextLine());
    }
    
    GraphFactory<Character, String> factory = new StringGraphFactory(dictionary);
    Graph<Character,String> G1 = factory.buildGraph();
    System.out.println("Done building graph!");
    Node<Character> pointer = G1.getHead();
    printGraph(pointer, pointer);
  }
  
  public static void printGraph(Node<?> pointer, Node<?> head) {
    System.out.println(pointer.getValue());
    for(Edge<?> edge : pointer.getOutgoingEdges()) {
      if(edge.getValue() != null) {
        System.out.println(edge.getValue());
      }
      Node<?> node = edge.getNodePointer();
      if (node != pointer && node != head) {
        printGraph(edge.getNodePointer(), head);
      } else if (node.getValue() == null) { // tail
        System.out.println("tail");
      }
    }
  }
  
}
