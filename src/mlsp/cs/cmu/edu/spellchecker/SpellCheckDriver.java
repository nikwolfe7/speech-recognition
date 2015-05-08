package mlsp.cs.cmu.edu.spellchecker;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import mlsp.cs.cmu.edu.graph.CartesianGraph;
import mlsp.cs.cmu.edu.graph.CartesianGraphFactory;
import mlsp.cs.cmu.edu.graph.CartesianNode;
import mlsp.cs.cmu.edu.graph.Edge;
import mlsp.cs.cmu.edu.graph.Graph;
import mlsp.cs.cmu.edu.graph.GraphFactory;

public class SpellCheckDriver {

  private static long startTime;

  private static long endTime;

  private static void startTimer() {
    startTime = System.nanoTime();
  }

  private static long stopTimer() {
    endTime = System.nanoTime();
    long duration = (endTime - startTime) / 1000000;
    System.out.println("Execution took: " + duration + "ms");
    startTime = 0;
    return duration;
  }

  private static List<String> fillDictionary1(List<String> dictionary) throws FileNotFoundException {
    Scanner scn = new Scanner(new File("./dict/dict_5k.txt"));
    while (scn.hasNextLine()) {
      dictionary.add(scn.nextLine());
    }
    scn.close();
    return dictionary;
  }

  private static List<String> fillDictionary2(List<String> dictionary) {
//    // dictionary.add("abc");
//    // dictionary.add("a");
//    for (int i = 0; i < 10; i++) {
      dictionary.add("pohnae");
//      dictionary.add("was");
//      dictionary.add("a");
//      dictionary.add("dim-witted");
//      dictionary.add("man");
//      dictionary.add("but");
//      dictionary.add("his");
//      dictionary.add("wife");
//      dictionary.add(",");
//      dictionary.add("mohnae");
//      dictionary.add(",");
//      dictionary.add("was");
//      dictionary.add("very");
//      dictionary.add("smart");
//      dictionary.add("zudda");
//      dictionary.add("woodsman");
//      dictionary.add("tac");
//      dictionary.add("skime");
//      dictionary.add("time");
//      dictionary.add("off");
//      dictionary.add("it");
//      dictionary.add("the");
//      dictionary.add("of");
//      dictionary.add(".");
//    }
    return dictionary;
  }

  private static List<String> fillInput1(List<String> input) throws FileNotFoundException {
    Scanner scn = new Scanner(new File("./text/typos.txt"));
    while (scn.hasNextLine()) {
      String[] arr = scn.nextLine().split(" ");
      input.addAll(Arrays.asList(arr));
    }
    scn.close();
    return input;
  }

  private static List<String> fillInput2(List<String> input) {
//    // input.add("123");
//    for (int i = 0; i < 10; i++) {
      input.add("fpohnae");
//      input.add("was");
//      input.add("a");
//      input.add("diwitted");
//      input.add("man");
//      input.add("but");
//      input.add("his");
//      input.add("wdfe");
//      input.add(",");
//      input.add("mohnaje");
//      input.add(",");
//      input.add("was");
//      input.add("vey");
//      input.add("smrxt");
//      input.add(".");
//      input.add("it");
//      input.add("was");
//      input.add("the");
//      input.add("tkime");
//      input.add("of");
//    }
    return input;
  }

  public static void main(String[] args) throws FileNotFoundException {
    long numRuns = 3;
    long runTotal = 0;
    long counter = numRuns;
    while ((counter--) > 0) {
      startTimer();
      doStuff();
      runTotal += stopTimer();
    }
    System.out.println("Avg runtime: " + (runTotal / numRuns) + "ms");
  }

  private static void doStuff() throws FileNotFoundException {
    List<String> dictionary = new ArrayList<String>();
    List<String> input = new ArrayList<String>();
     dictionary = fillDictionary1(dictionary);
     input = fillInput1(input);
//    dictionary = fillDictionary2(dictionary);
//    input = fillInput2(input);

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
    CartesianGraphFactory<Character, String> cgFactory = new StringCartesianGraphFactory();
    for (Graph<Character, String> wordGraph : words) {
      CartesianGraph<Character, String> product = cgFactory.buildGraph(G1, wordGraph);
      CartesianNode<Character> n = (CartesianNode<Character>) product.getTailNode();
      List<Edge<?>> x = n.getIncomingEdges();
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
