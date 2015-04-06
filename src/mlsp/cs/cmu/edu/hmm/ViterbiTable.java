package mlsp.cs.cmu.edu.hmm;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.math3.util.Pair;

public abstract class ViterbiTable<S, O> {

  protected Map<S, Integer> states;

  protected Map<O, Integer> outputs;

  protected AlphaTable<S, O> alpha;

  protected BetaTable<S, O> beta;

  protected PriorTable<S> priors;

  protected double[][] viterbiTable;

  /**
   * @param alpha
   * @param beta
   * @param priors
   */
  public ViterbiTable(AlphaTable<S, O> alpha, BetaTable<S, O> beta, PriorTable<S> priors) {
    super();
    this.alpha = alpha;
    this.beta = beta;
    this.priors = priors;
    updateStatesAndOutputs();
  }

  private void updateStatesAndOutputs() {
    this.states = new HashMap<S, Integer>();
    this.outputs = new HashMap<O, Integer>();
    List<S> sList = alpha.getStates();
    List<O> oList = beta.getOutputs();
    int i = 0;
    for (S state : sList)
      states.put(state, i++);
    int j = 0;
    for (O output : oList)
      outputs.put(output, j++);
  }

  public List<S> getViterbiBestPathFromFile(String filename) {
    File file = new File(filename);
    try {
      Scanner scn = new Scanner(file);
      List<O> observation = parseFileForObservations(scn);
      scn.close();
      return getViterbiBestPath(observation);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * How to read in a file to get the observation. Assumes one observation per file.
   * 
   * @param scn
   * @return
   */
  protected abstract List<O> parseFileForObservations(Scanner scn);

  /* Defer to subclass... */
  protected abstract void calculateAccuracy(List<S> bestPath, List<O> observation);

  public List<S> getViterbiBestPath(List<O> observation) {
    viterbiTable = new double[states.size()][observation.size()];
    Pair<double[][], List<S>> viterbiResult = viterbi(observation, viterbiTable);
    viterbiTable = viterbiResult.getFirst();
    List<S> bestPath = viterbiResult.getSecond();
    System.out.println("\n==================\nViterbi Algorithm\n==================");
    System.out.println("Most Likely State Sequence: " + bestPath.toString());
    System.out.println(" Actual Character Sequence: " + observation.toString());
    calculateAccuracy(bestPath, observation);
    return bestPath;
  }

  private Pair<double[][], List<S>> viterbi(List<O> observation, double[][] trellis) {
    Pair<double[][], List<S>> viterbiResult = new Pair<double[][], List<S>>(trellis,
            new ArrayList<S>());
    for (int t = 0; t < observation.size(); ++t) {
      if (t == 0) {
        for (Map.Entry<S, Integer> state : states.entrySet()) {
          int i = state.getValue();
          O currObs = observation.get(t);
          trellis[i][t] = priors.getPrior(state.getKey())
                  + beta.getBetaValue(state.getKey(), currObs);
        }
      } else {
        S maxState = priors.getStates().get(0);
        double maxProb = -1.0e100;
        for (Map.Entry<S, Integer> state : states.entrySet()) {
          O currObs = observation.get(t);
          double emissionProb = beta.getBetaValue(state.getKey(), currObs);
          int i = state.getValue();
          for (Map.Entry<S, Integer> prevState : states.entrySet()) {
            int j = prevState.getValue();
            double prevProb = trellis[j][t - 1];
            double alphaJI = alpha.getAlphaValue(prevState.getKey(), state.getKey());
            double stateProb = emissionProb + prevProb + alphaJI;
            if (stateProb > maxProb) {
              maxProb = stateProb;
              maxState = prevState.getKey();
            }
          }
          trellis[i][t] = maxProb;
        }
        viterbiResult.getSecond().add(maxState);
      }
    }
    /* Get the last state, return */
    S maxState = priors.getStates().get(0);
    double maxProb = -1.0e100;
    for (Map.Entry<S, Integer> state : states.entrySet()) {
      int i = state.getValue();
      double lastCol = trellis[i][observation.size() - 1];
      if (lastCol > maxProb) {
        maxProb = lastCol;
        maxState = state.getKey();
      }
    }
    viterbiResult.getSecond().add(maxState);
    return viterbiResult;
  }

  /**
   * ============================ 
   * Dumb getters and setters... 
   * ============================
   * 
   * @return
   */
  public AlphaTable<S, O> getAlpha() {
    return alpha;
  }

  public void setAlpha(AlphaTable<S, O> alpha) {
    this.alpha = alpha;
    updateStatesAndOutputs();
  }

  public BetaTable<S, O> getBeta() {
    return beta;
  }

  public void setBeta(BetaTable<S, O> beta) {
    this.beta = beta;
    updateStatesAndOutputs();
  }

  public PriorTable<S> getPriors() {
    return priors;
  }

  public void setPriors(PriorTable<S> priors) {
    this.priors = priors;
  }

  public double[][] getViterbiTable() {
    return viterbiTable;
  }
}
