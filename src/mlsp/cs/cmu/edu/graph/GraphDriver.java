package mlsp.cs.cmu.edu.graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class GraphDriver {

  public static void main(String[] args) throws FileNotFoundException {

    List<String> dictionary = new ArrayList<String>();
    Scanner scn = new Scanner(new File("./dict/dict_5k.txt"));
    while (scn.hasNextLine()) {
      dictionary.add(scn.nextLine());
    }
    scn.close();
    // dictionary.add("a");
    // dictionary.add("pohnae");
    // dictionary.add("was");
    // dictionary.add("a");
    // dictionary.add("dim-witted");
    // dictionary.add("man");
    // dictionary.add("but");
    // dictionary.add("his");
    // dictionary.add("wife");
    // dictionary.add(",");
    // dictionary.add("mohnae");
    // dictionary.add(",");
    // dictionary.add("was");
    // dictionary.add("very");
    // dictionary.add("smart");
    // dictionary.add("zudda");
    // dictionary.add("woodsman");
    // dictionary.add("tac");

    List<String> input = new ArrayList<String>();
    scn = new Scanner(new File("./text/typos.txt"));
    while (scn.hasNextLine()) {
      String[] arr = scn.nextLine().split(" ");
      input.addAll(Arrays.asList(arr));
    }
    scn.close();
    // // input.add("fpohnae");
    // input.add("was");
    // input.add("a");
    // input.add("diwitted");
    // input.add("man");
    // input.add("but");
    // input.add("his");
    // input.add("wdfe");
    // input.add(",");
    // input.add("mohnaje");
    // input.add(",");
    // input.add("was");
    // input.add("vey");
    // input.add("smrxt");

    GraphFactory<Character, String> factory = new StringGraphFactory(
            dictionary.toArray(new String[dictionary.size()]));
    Graph<Character, String> G1 = factory.buildGraph();
    System.out.println("Dictionary Graph: Done building graph!");
    // printGraph(G1);

    List<Graph<Character, String>> words = new ArrayList<Graph<Character, String>>();
    for (String s : input) {
      factory = new StringGraphFactory(s);
      Graph<Character, String> G2 = factory.buildGraph();
      words.add(G2);
      // printGraph(G2);
    }
    List<String> checkedList = new ArrayList<String>();
    for (Graph<Character, String> wordGraph : words) {
      CartesianGraph<Character, String> product = new StringCartesianGraph(G1, wordGraph);
      // printGraph(product);
      String word = product.getTailNode().getBackPointer().getValue().toString();
      checkedList.add(word);
      System.out.println("Word: " + word);
    }

     printAccuracy(checkedList);

  }

  private static void printAccuracy(List<String> checkedList) throws FileNotFoundException {
    List<String> truthList = new ArrayList<String>();
    Scanner scn = new Scanner(new File("./text/original.txt"));
    while (scn.hasNextLine()) {
      String[] arr = scn.nextLine().split(" ");
      truthList.addAll(Arrays.asList(arr));
    }
    scn.close();
    double numCorrect = 0;
    if (checkedList.size() > 0) {
      for (int i = 0; i < checkedList.size(); i++) {
        if (truthList.get(i).equals(checkedList.get(i))) {
          numCorrect++;
        }
      }
    }
    DecimalFormat df = new DecimalFormat("#.####");
    System.out.println("Accuracy: " + df.format(numCorrect / truthList.size()));
  }

  public static void printGraph(Graph<?, ?> graph) {
    System.out.println(graph.toString());
  }

}