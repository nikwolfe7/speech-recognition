package mlsp.cs.cmu.edu.hmm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class GammaKsiTable<S, O> {

  protected Map<S, Integer> states;

  protected Map<O, Integer> outputs;

  protected AlphaTable<S, O> A;

  protected BetaTable<S, O> B;

  protected PriorTable<S> Pi;

  private double[][][] ksiTrellis;

  private double[][] gammaTrellis;

  /**
   * @param a
   * @param b
   */
  public GammaKsiTable(AlphaTable<S, O> a, BetaTable<S, O> b, PriorTable<S> pi) {
    this.A = a;
    this.B = b;
    this.Pi = pi;
    updateStatesAndOutputs();
  }

  private void updateStatesAndOutputs() {
    this.states = new HashMap<S, Integer>();
    this.outputs = new HashMap<O, Integer>();
    List<S> sList = A.getStates();
    List<O> oList = B.getOutputs();
    int i = 0;
    for (S state : sList)
      states.put(state, i++);
    int j = 0;
    for (O output : oList)
      outputs.put(output, j++);
  }

  public double[][] calculateGammaTable(List<O> observation) {
    gammaTrellis = new double[states.size()][observation.size()];
    A.forwardProbability(Pi, B, observation);
    double[][] alpha = A.getForwardTable();
    B.backwardProbability(Pi, A, observation);
    double[][] beta = B.getBackwardTable();
    gammaTrellis = populateGammaTrellis(gammaTrellis, alpha, beta);
    return gammaTrellis;
  }

  private double[][] populateGammaTrellis(double[][] trellis, double[][] alpha, double[][] beta) {
    for (int t = 0; t < alpha[0].length; ++t) {
      // denominator -- calculate once
      double alphaBetaSum = 0.0;
      for (Map.Entry<S, Integer> state : states.entrySet()) {
        int j = state.getValue();
        double alphaTerm = alpha[j][t];
        double betaTerm = beta[j][t];
        double alphaBetaTerm = alphaTerm + betaTerm;
        if (alphaBetaSum == 0) {
          alphaBetaSum = alphaBetaTerm;
        } else {
          alphaBetaSum = LogOperations.logAdd(alphaBetaSum, alphaBetaTerm);
        }
      }
      // numerator
      for (Map.Entry<S, Integer> state : states.entrySet()) {
        int i = state.getValue();
        double alphaTerm = alpha[i][t];
        double betaTerm = beta[i][t];
        double alphaBetaTerm = alphaTerm + betaTerm;
        double gammaTerm = alphaBetaTerm - alphaBetaSum;
        trellis[i][t] = gammaTerm;
      }
    }
    return trellis;
  }
  
  public double[][][] calculateKsiTable(double[][] gamma, List<O> observation) {
    ksiTrellis = new double[observation.size()-1][states.size()][states.size()];
    B.backwardProbability(Pi, A, observation);
    double[][] beta = B.getBackwardTable();
    ksiTrellis = populateKsiTrellis(ksiTrellis, gamma, beta, observation);
    return ksiTrellis;
  }

  private double[][][] populateKsiTrellis(double[][][] trellis, double[][] gamma,
          double[][] beta, List<O> observation) {
    for(int t = 0; t < observation.size()-1; ++t) {
      for(Map.Entry<S, Integer> state : states.entrySet()) {
        for(Map.Entry<S, Integer> subState : states.entrySet()) {
          int i = state.getValue();
          int j = state.getValue();
          double gammaTerm = gamma[i][t];
          double alphaIJ = A.getAlphaValue(state.getKey(), subState.getKey());
          O nextObs = observation.get(t+1);
          double betaNextObs = B.getBetaValue(subState.getKey(), nextObs);
          double betaNext = beta[j][t+1];
          double betaCurr = beta[i][t]; // denominator
          double numerator = gammaTerm + alphaIJ + betaNextObs + betaNext;
          double ksiTerm = numerator - betaCurr; // log subtract = divide
          trellis[t][state.getValue()][subState.getValue()] = ksiTerm;
        }
      }
    }
    return trellis;
  }

  /**
   * Dumb getters and setters
   * 
   * @return
   */
  public AlphaTable<S, O> getA() {
    return A;
  }

  public void updateA(AlphaTable<S, O> a) {
    A = a;
  }

  public BetaTable<S, O> getB() {
    return B;
  }

  public void updateB(BetaTable<S, O> b) {
    B = b;
  }

}
