package mlsp.cs.cmu.edu.hmm;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.math3.util.Pair;

public class DataCleaner {

  private Pattern alpha = Pattern.compile("\\p{Alpha}+");

  private String folder = "./hw7-data/";

  private Counter<Character> counter = new Counter<Character>();

  public void cleanData(String filename, boolean calcStats) throws IOException {

    /* Read input training file */
    File file = new File(filename);
    Scanner scn = new Scanner(file);

    /* Clean Input */
    StringBuilder builder = new StringBuilder();
    while (scn.hasNextLine()) {
      String line = scn.nextLine();
      Matcher m = alpha.matcher(line);
      while (m.find())
        builder.append((m.group() + " ").toUpperCase());
    }
    scn.close();
    String doc = builder.toString();

    FileWriter writer = getWriter(filename.split(".txt")[0] + "-cleaned.txt");
    writer.write(doc);
    writer.close();
    //System.out.println("Done!\n");

    if (calcStats) {
      /* Count characters and generate probabilities */
      char[] arr = doc.toCharArray();
      for (Character c : arr) {
        counter.add(c);
      }

      /* Output priors */
      List<Pair<Character, Double>> probs = counter.getProbabilites();
      for (Pair<Character, Double> prob : probs) {
        //System.out.println(prob.getKey() + ":" + prob.getValue());
      }

      /* Get individual state probabilities */
      Double vowelsum = 0.0;
      Double consonantsum = 0.0;
      List<Character> vowels = new ArrayList<Character>(Arrays.asList('A','E','I','O','U','Y',' '));
      for (Pair<Character, Double> prob : probs) {
        if (vowels.contains(prob.getKey()))
          vowelsum += prob.getValue();
        else
          consonantsum += prob.getValue();
      }

      DecimalFormat df = new DecimalFormat("#.###");
      StringBuilder V = new StringBuilder();
      StringBuilder C = new StringBuilder();
      
      for (Pair<Character, Double> prob : probs) {
        Character character = prob.getKey();
        Double vprob = 0.0;
        Double cprob = 0.0;
        if (vowels.contains(prob.getKey())) {
          //System.out.println("Vowel/Space & " + prob.getKey() + " & " + df.format(prob.getValue() / vowelsum) + " \\\\ \\hline");
          vprob = prob.getValue() / vowelsum;
        } else {
          //System.out.println("Consonants & " + prob.getKey() + " & " + df.format(prob.getValue() / vowelsum) + " \\\\ \\hline");
          cprob = prob.getValue() / vowelsum;
        }
        V.append(character + ":" + vprob + "\t");
        C.append(character + ":" + cprob + "\t");
      }
      
      /* Ouput emissions */
      writer = getWriter(folder + "hmm-emit.txt");
      writer.write("V\t" + V.toString() + "\n");
      writer.write("C\t" + C.toString() + "\n");
      writer.close();
    }
  }

  private FileWriter getWriter(String filename) throws IOException {
    File file = new File(filename);
    FileWriter writer = new FileWriter(file);
    return writer;
  }

}
