package mlsp.cs.cmu.edu.graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class GraphDriver {

  public static void main(String[] args) throws FileNotFoundException {
    
    List<String> dictionary = new ArrayList<String>();
    /*Scanner scn = new Scanner(new File("./dict/dict_80k.txt"));
    while(scn.hasNextLine()) {
      dictionary.add(scn.nextLine());
    }*/
    dictionary.add("aaaa");
    
    List<String> input = new ArrayList<String>();
    /*scn = new Scanner(new File("./text/typos.txt"));
    while(scn.hasNextLine()) {
      String[] arr = scn.nextLine().split(" ");
      input.addAll(Arrays.asList(arr));
    }*/
    input.add("aabb");
   
    GraphFactory<Character, String> factory = new StringGraphFactory(dictionary.toArray(new String[dictionary.size()]));
    Graph<Character,String> G1 = factory.buildGraph();
    System.out.println("Done building graph!");
    printGraph(G1);
    
    List<Graph<Character,String>> words = new ArrayList<Graph<Character,String>>();
    for(String s : input) {
      factory = new StringGraphFactory(s);
      Graph<Character,String> G2 = factory.buildGraph(); 
      words.add(G2);
      printGraph(G2);
    }
    
    for(Graph<Character,String> wordGraph : words) {
      CartesianGraph<Character, String> product = new StringCartesianGraph(G1, wordGraph);
      printNodes(product.getHeadNode(), product.getHeadNode());
    }
  }
  
  public static void printGraph(Graph<?,?> graph) {
    Node<?> pointer = graph.getHead();
    printNodes(pointer, pointer);
  }
  
  private static void printNodes(Node<?> pointer, Node<?> head) {
    System.out.print(" --> "+pointer.getValue());
    for(Edge<?> edge : pointer.getOutgoingEdges()) {
      if(edge.getValue() != null) {
        System.out.println("\n"+edge.getValue());
      }
      Node<?> node = edge.getNodePointer();
      if (node != pointer && node != head) {
        printNodes(edge.getNodePointer(), head);
      } else if (node.getValue() == null) { // tail
        System.out.println("tail");
      }
    }
  }
  
}
