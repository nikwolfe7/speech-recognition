package mlsp.cs.cmu.edu.hmm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public abstract class BetaTable<S, O> {

  protected Map<S, Integer> states;

  protected Map<O, Integer> outputs;

  protected double[][] bTable;

  protected double[][] backward;

  private boolean displayOutput = true;

  private File betaUpdate;

  /* Assumes full path */
  public BetaTable(String filename, List<S> states, List<O> outputs, boolean initRandom) {
    if (states.size() < 1)
      throw new IllegalStateException("No states? WTF man!");
    this.betaUpdate = new File(filename.replace(".txt", "-update.txt"));
    this.bTable = new double[states.size()][outputs.size()];
    this.states = new HashMap<S, Integer>();
    this.outputs = new HashMap<O, Integer>();

    int i = 0;
    for (S state : states)
      this.states.put(state, i++);
    int j = 0;
    for (O output : outputs)
      this.outputs.put(output, j++);

    File file = new File(filename);
    try {
      Scanner scn = new Scanner(file);
      while (scn.hasNextLine())
        loadTrellisFromLine(scn.nextLine());
      scn.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    if (initRandom)
      bTable = populateRandom(bTable);
  }

  private double[][] populateRandom(double[][] table) {
    Random rnd = new Random();
    for (int i = 0; i < table.length; ++i) {
      double rowSum = 0;
      for (int j = 0; j < table[0].length; ++j) {
        double val = rnd.nextDouble();
        rowSum += val;
        table[i][j] = val;
      }
      for (int j = 0; j < table[0].length; ++j) {
        double rndVal = LogOperations.log(table[i][j] / rowSum);
        table[i][j] = rndVal;
      }
    }
    return table;
  }

  public void printTrellis() throws IOException {
    System.out.println("\n=======\n" + "B Table\n" + "=======");
    FileWriter fw = new FileWriter(betaUpdate);
    DecimalFormat df = new DecimalFormat("#.###");
    for (Map.Entry<S, Integer> state : states.entrySet()) {
      int i = state.getValue();
      String str = state.getKey().toString() + "\t";
      fw.write(str);
      System.out.print(str);
      for (Map.Entry<O, Integer> output : outputs.entrySet()) {
        int j = output.getValue();
        str = output.getKey() + ":" + df.format(getBValueAtIndex(i, j)) + "\t";
        fw.write(str);
        System.out.print(str);
      }
      fw.write("\n");
      System.out.println();
    }
    fw.close();
  }

  public double getBValue(S state, O output) {
    int sIndex = states.get(state);
    int oIndex = outputs.get(output);
    return bTable[sIndex][oIndex];
  }

  public double getBValueAtIndex(int s, int o) {
    return bTable[s][o];
  }

  public int getIndexFromOutput(O obs) {
    return outputs.get(obs);
  }

  public void setBValue(S state, O output, double prob) {
    int sIndex = states.get(state);
    int oIndex = outputs.get(output);
    bTable[sIndex][oIndex] = prob;
  }

  public void setBValueAtIndex(int s, int o, double prob) {
    bTable[s][o] = prob;
  }

  @SuppressWarnings("unchecked")
  public List<S> getStates() {
    S[] stSet = (S[]) states.keySet().toArray();
    return Arrays.asList(stSet);
  }

  @SuppressWarnings("unchecked")
  public List<O> getOutputs() {
    O[] otSet = (O[]) outputs.keySet().toArray();
    return Arrays.asList(otSet);
  }

  public double[][] getBackwardTable() {
    return backward;
  }

  public double[][] getBTable() {
    return bTable;
  }

  public void setBetaTable(double[][] betaTable) {
    this.bTable = betaTable;
  }

  public void setDisplayOutput(boolean displayOutput) {
    this.displayOutput = displayOutput;
  }

  protected abstract void loadTrellisFromLine(String line);

  public double getObservationProbability(PriorTable<S> priors, AlphaTable<S, O> alpha,
          String filename) {
    File file = new File(filename);
    try {
      Scanner scn = new Scanner(file);
      while (scn.hasNextLine()) {
        List<O> observation = processObservationFromTextLine(scn.nextLine());
        return backwardProbability(priors, alpha, observation);
      }
      scn.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return 0;
  }

  protected abstract List<O> processObservationFromTextLine(String nextLine);

  /**
   * Public in case you want to calculate backward prob directly...
   * 
   * @param priors
   * @param alpha
   * @param observation
   * @return
   */
  public double backwardProbability(PriorTable<S> priors, AlphaTable<S, O> alpha,
          List<O> observation) {
    backward = new double[states.size()][observation.size()];
    backward = calculateBackward(priors, alpha, observation, backward);
    double betaProbability = LogOperations.NEG_INF;
    for (Map.Entry<S, Integer> state : states.entrySet()) {
      int initialIndex = 0;
      double betaZero = backward[state.getValue()][initialIndex]; // initial beta
      double initialProbability = priors.getPrior(state.getKey());
      double emission = getBValue(state.getKey(), observation.get(initialIndex));
      double logSum = betaZero + initialProbability + emission;
      betaProbability = LogOperations.logAdd(betaProbability, logSum);
    }
    if (displayOutput) {
      System.out.println("\n==================\nBackward Algorithm\n==================");
      System.out.println("Sequence length: " + observation.size());
      System.out.println("Log Likelihood of sequence: " + betaProbability);
      System.out.println("Average Log Likelihood: " + betaProbability / observation.size());
    }
    return betaProbability;
  }

  /**
   * Helper function...
   * 
   * @param priors
   * @param alpha
   * @param observation
   * @param trellis
   * @return
   */
  private double[][] calculateBackward(PriorTable<S> priors, AlphaTable<S, O> alpha,
          List<O> observation, double[][] trellis) {
    for (int t = observation.size() - 1; t >= 0; --t) {
      if (t == observation.size() - 1) { // init step
        for (Map.Entry<S, Integer> state : states.entrySet()) {
          int i = state.getValue();
          trellis[i][t] = LogOperations.log(1.0);
        }
      } else { // "recurisve" step
        // for each state
        for (Map.Entry<S, Integer> state : states.entrySet()) {
          // get sum of next column betas, transitions, emissions
          double nextSum = LogOperations.NEG_INF;
          // loop over next column
          for (Map.Entry<S, Integer> nextState : states.entrySet()) {
            // emission prob of next output
            O obsTplusOne = observation.get(t + 1);
            double obsTplusOneBeta = getBValue(nextState.getKey(), obsTplusOne);
            // get beta trellis value of j index item in next column
            double nextBeta = trellis[nextState.getValue()][t + 1];
            // get a_ij transition probability
            double alphaIJ = alpha.getAValue(state.getKey(), nextState.getKey());
            // log sum (product) of these terms
            double logSum = obsTplusOneBeta + nextBeta + alphaIJ;
            // add to current sum
            nextSum = LogOperations.logAdd(nextSum, logSum);
          }
          // get (state) index
          int i = state.getValue();
          // update trellis...
          trellis[i][t] = nextSum;
        }
      }
    }
    return trellis;
  }

}
