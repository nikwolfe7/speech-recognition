package mlsp.cs.cmu.edu.hmm;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public abstract class BetaTable<S, O> {

  protected Map<S, Integer> states;

  protected Map<O, Integer> outputs;

  protected double[][] betaTable;

  protected double[][] backward;

  /* Assumes full path */
  public BetaTable(String filename, List<S> states, List<O> outputs) {
    if (states.size() < 1)
      throw new IllegalStateException("No states? WTF man!");
    this.betaTable = new double[states.size()][outputs.size()];
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
  }

  public void printTrellis() {
    DecimalFormat df = new DecimalFormat("#.###");
    for (int i = 0; i < betaTable.length; ++i) {
      for (int j = 0; j < betaTable[0].length; ++j) {
        System.out.print(df.format(betaTable[i][j]) + "\t");
      }
      System.out.println("\n");
    }
  }

  public double getBetaValue(S state, O output) {
    int sIndex = states.get(state);
    int oIndex = outputs.get(output);
    return betaTable[sIndex][oIndex];
  }

  public double getBetaValueFromIndex(int s, int o) {
    return betaTable[s][o];
  }

  public void setBetaValue(S state, O output, double prob) {
    int sIndex = states.get(state);
    int oIndex = outputs.get(output);
    betaTable[sIndex][oIndex] = LogOperations.log(prob);
  }

  public void setBetaValueAtIndex(int s, int o, double prob) {
    betaTable[s][o] = prob;
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

  public double[][] getBackward() {
    return backward;
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
    double betaProbability = 0;
    for(Map.Entry<S, Integer> state : states.entrySet()) {
      int initialIndex = 0;
      double betaZero = backward[state.getValue()][initialIndex]; // initial beta
      double initialProbability = priors.getPrior(state.getKey());
      double emission = getBetaValue(state.getKey(), observation.get(initialIndex));
      double logSum = betaZero + initialProbability + emission;
      if (betaProbability == 0)
        betaProbability = logSum;
      else
        betaProbability = LogOperations.logAdd(betaProbability, logSum);
    }
    System.out.println("\n==================\nBackward Algorithm\n==================");
    System.out.println("Sequence length: " + observation.size());
    System.out.println("Log Likelihood of sequence: " + betaProbability);
    System.out.println("Average Log Likelihood: " + betaProbability/observation.size());
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
          double nextSum = 0.0;
          // loop over next column
          for (Map.Entry<S, Integer> nextState : states.entrySet()) {
            // emission prob of next output
            O obsTplusOne = observation.get(t + 1);
            double obsTplusOneBeta = getBetaValue(nextState.getKey(), obsTplusOne);
            // get beta trellis value of j index item in next column
            double nextBeta = trellis[nextState.getValue()][t + 1];
            // get a_ij transition probability
            double alphaIJ = alpha.getAlphaValue(state.getKey(), nextState.getKey());
            // log sum (product) of these terms
            double logSum = obsTplusOneBeta + nextBeta + alphaIJ;
            // add to current sum
            if (nextSum == 0.0)
              nextSum = logSum;
            else
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
