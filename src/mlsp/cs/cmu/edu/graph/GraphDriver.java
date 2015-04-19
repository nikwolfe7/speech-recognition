package mlsp.cs.cmu.edu.graph;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class GraphDriver {

  public static void main(String[] args) throws FileNotFoundException {

    List<String> dictionary = new ArrayList<String>();
    /*
     * Scanner scn = new Scanner(new File("./dict/dict_80k.txt")); while(scn.hasNextLine()) {
     * dictionary.add(scn.nextLine()); }
     */
    dictionary.add("abc");

    List<String> input = new ArrayList<String>();
    /*
     * scn = new Scanner(new File("./text/typos.txt")); while(scn.hasNextLine()) { String[] arr =
     * scn.nextLine().split(" "); input.addAll(Arrays.asList(arr)); }
     */
    input.add("123");

    GraphFactory<Character, String> factory = new StringGraphFactory(
            dictionary.toArray(new String[dictionary.size()]));
    Graph<Character, String> G1 = factory.buildGraph();
    System.out.println("Done building graph!");
    printGraph(G1);

    List<Graph<Character, String>> words = new ArrayList<Graph<Character, String>>();
    for (String s : input) {
      factory = new StringGraphFactory(s);
      Graph<Character, String> G2 = factory.buildGraph();
      words.add(G2);
      printGraph(G2);
    }

    for (Graph<Character, String> wordGraph : words) {
      CartesianGraph<Character, String> product = new StringCartesianGraph(G1, wordGraph);
      printNodes(product.getHead(), product.getHead());
    }
  }

  public static void printGraph(Graph<?, ?> graph) {
    Node<?> pointer = graph.getHead();
    printNodes(pointer, pointer);
    System.out.println();
  }

  private static void printNodes(Node<?> pointer, Node<?> head) {
    for (Node<?> node : pointer.getSuccessors()) {
      if(node == head) {
        return;
      } else if (pointer != node) {
        System.out.print(" --> " + node.toString());
        printNodes(node, head);
      }
    }
  }

}