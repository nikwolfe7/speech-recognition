package mlsp.cs.cmu.edu.hmm;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public abstract class AlphaTable<S, O> {

  protected Map<S, Integer> states;

  protected double[][] alphaTable;

  protected double[][] forward;

  private boolean displayOutput = true;

  public AlphaTable(String filename, List<S> states) {
    if (states.size() < 1)
      throw new IllegalStateException("No states? WTF man!");
    this.alphaTable = new double[states.size()][states.size()];
    this.states = new HashMap<S, Integer>();
    int i = 0;
    for (S state : states)
      this.states.put(state, i++);
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
    for (int i = 0; i < alphaTable.length; ++i) {
      for (int j = 0; j < alphaTable[0].length; ++j) {
        System.out.print(df.format(alphaTable[i][j]) + "\t");
      }
      System.out.println("\n");
    }
  }

  public double getAlphaValue(S fromState, S toState) {
    int fIndex = states.get(fromState);
    int tIndex = states.get(toState);
    return alphaTable[fIndex][tIndex];
  }

  public void setAlphaValue(S fromState, S toState, double prob) {
    int fIndex = states.get(fromState);
    int tIndex = states.get(toState);
    alphaTable[fIndex][tIndex] = prob;
  }

  @SuppressWarnings("unchecked")
  public List<S> getStates() {
    S[] stSet = (S[]) states.keySet().toArray();
    return Arrays.asList(stSet);
  }

  public double getAlphaValueFromIndex(int from, int to) {
    return alphaTable[from][to];
  }

  public void setAlphaValueAtIndex(int from, int to, double prob) {
    alphaTable[from][to] = prob;
  }

  public double[][] getForwardTable() {
    return forward;
  }

  public void setDisplayOutput(boolean displayOutput) {
    this.displayOutput = displayOutput;
  }

  protected abstract void loadTrellisFromLine(String nextLine);

  public double getObservationProbability(PriorTable<S> priors, BetaTable<S, O> beta,
          String filename) {
    File file = new File(filename);
    try {
      Scanner scn = new Scanner(file);
      while (scn.hasNextLine()) {
        List<O> observation = processObservationFromTextLine(scn.nextLine());
        return forwardProbability(priors, beta, observation);
      }
      scn.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return 0;
  }

  /* subclasses decide how to process a line of text */
  protected abstract List<O> processObservationFromTextLine(String nextLine);

  /**
   * Public in case you want to calculate forward in an application where you're not reading from a
   * file. Otherwise, use getObservationProbability()
   * 
   * @param priors
   * @param beta
   * @param observation
   * @return
   */
  public double forwardProbability(PriorTable<S> priors, BetaTable<S, O> beta, List<O> observation) {
    forward = new double[states.size()][observation.size()];
    forward = calculateForward(priors, beta, observation, forward);
    double obsProb = forward[0][observation.size() - 1];  
    for (int i = 1; i < forward.length; ++i) {
      obsProb = LogOperations.logAdd(obsProb, forward[i][observation.size() - 1]);
    }
    if (displayOutput) {
      System.out.println("\n=================\nForward Algorithm\n=================");
      System.out.println("Sequence length: " + observation.size());
      System.out.println("Log Likelihood of sequence: " + obsProb);
      System.out.println("Average Log Likelihood: " + obsProb / observation.size());
    }
    return obsProb;
  }

  /**
   * Recursive helper function ...
   * 
   * @param priors
   * @param beta
   * @param observation
   * @param trellis
   * @param t
   * @return
   */
  private double[][] calculateForward(PriorTable<S> priors, BetaTable<S, O> beta,
          List<O> observation, double[][] trellis) {
    for (int t = 0; t < observation.size(); ++t) {
      if (t >= observation.size())
        return trellis;
      else if (t == 0) { // initialization step
        for (Map.Entry<S, Integer> state : states.entrySet()) {
          int i = state.getValue();
          O currObs = observation.get(t);
          trellis[i][t] = priors.getPrior(state.getKey())
                  + beta.getBetaValue(state.getKey(), currObs);
        }
      } else { // "recursion" step
        for (Map.Entry<S, Integer> state : states.entrySet()) {
          double prevAlphaSum = LogOperations.NEG_INF;
          for (Map.Entry<S, Integer> prevState : states.entrySet()) {
            double alphaIJ = getAlphaValue(prevState.getKey(), state.getKey());
            double prevAlpha = trellis[prevState.getValue()][t - 1];
            double prevAlpha_alphaIJ = prevAlpha + alphaIJ;
            prevAlphaSum = LogOperations.logAdd(prevAlphaSum, prevAlpha_alphaIJ);
          }
          // update the trellis
          O currObs = observation.get(t);
          double betaValue = beta.getBetaValue(state.getKey(), currObs);
          double alphaJ = prevAlphaSum + betaValue;
          int j = state.getValue();
          trellis[j][t] = alphaJ;
        }
      }
    }
    return trellis;
  }

}
