package mlsp.cs.cmu.edu.hmm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public abstract class PriorTable<S> {

  protected List<S> states;

  protected Map<S, Double> priors;

  private File priorUpdates;

  public PriorTable(String filename, List<S> states, boolean initRandom) {
    this.priorUpdates = new File(filename.replace(".txt", "-update.txt"));
    this.states = states;
    this.priors = new HashMap<S, Double>();

    File file = new File(filename);
    try {
      Scanner scn = new Scanner(file);
      while (scn.hasNextLine()) {
        loadPriorFromLine(scn.nextLine());
      }
      scn.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    if (initRandom)
      populateRandom();
  }

  private void populateRandom() {
    Random rnd = new Random();
    double pSum = 0;
    for (S state : states) {
      double val = rnd.nextDouble();
      pSum += val;
      priors.put(state, val);
    }
    for (S state : states) {
      double rndPrior = LogOperations.log(priors.get(state) / pSum);
      priors.put(state, rndPrior);
    }
  }

  public void printPriors() throws IOException {
    System.out.println("\n========\n" + "Pi Table\n" + "========");
    FileWriter fw = new FileWriter(priorUpdates);
    for (Map.Entry<S, Double> key : priors.entrySet()) {
      String outStr = key.getKey() + ":" + key.getValue();
      System.out.println(outStr);
      fw.write(outStr + "\n");
    }
    fw.close();
  }

  public List<S> getStates() {
    return states;
  }

  public void setPrior(S state, Double prior) {
    priors.put(state, prior);
  }

  public void setPriorAtIndex(int i, Double prior) {
    priors.put(states.get(i), prior);
  }

  public double getPrior(S state) {
    return priors.get(state);
  }

  protected abstract void loadPriorFromLine(String nextLine);

}
