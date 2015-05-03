package mlsp.cs.cmu.edu.hmm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.MutablePair;

public abstract class GammaKsiTable<S, O> {

  protected Map<S, Integer> states;

  protected Map<O, Integer> outputs;

  protected AlphaTable<S, O> A;

  protected BetaTable<S, O> B;

  protected PriorTable<S> Pi;

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

  public List<MutablePair<double[][], double[][][]>> getGammaKsiLookupTable(List<List<O>> observations) {
    List<MutablePair<double[][], double[][][]>> gammaKsiLookup = new ArrayList<MutablePair<double[][], double[][][]>>();
    for (List<O> observation : observations) {
      calculateForwardBackwardTables(observation);
      double[][] gamma = calculateGammaTable(observation);
      double[][][] ksi = calculateKsiTable(observation);
      MutablePair<double[][], double[][][]> gammaKsi = new MutablePair<double[][], double[][][]>(gamma, ksi);
      gammaKsiLookup.add(gammaKsi);
    }
    return gammaKsiLookup;
  }

  private void calculateForwardBackwardTables(List<O> observation) {
    double fProb = A.forwardProbability(Pi, B, observation);
    double bProb = B.backwardProbability(Pi, A, observation);
    if (fProb != bProb) {
      // System.out.println("Forward/Backward probs differ by: " + Math.abs(fProb-bProb));
    }
  }

  private double[][] calculateGammaTable(List<O> observation) {
    double[][] gammaTrellis = new double[states.size()][observation.size()];
    gammaTrellis = populateGammaTrellis(gammaTrellis, A.getForwardTable(), B.getBackwardTable());
    return gammaTrellis;
  }

  private double[][] populateGammaTrellis(double[][] trellis, double[][] alpha, double[][] beta) {
    for (int t = 0; t < trellis[0].length; ++t) {
      // denominator -- calculate once
      double alphaBetaDenominator = LogOperations.NEG_INF;
      for (Map.Entry<S, Integer> jState : states.entrySet()) {
        int j = jState.getValue();
        double denominatorTerm = alpha[j][t] + beta[j][t];
        alphaBetaDenominator = LogOperations.logAdd(alphaBetaDenominator, denominatorTerm);
      }
      for (Map.Entry<S, Integer> iState : states.entrySet()) {
        int i = iState.getValue();
        double alphaBetaNumerator = alpha[i][t] + beta[i][t];
        double gammaTerm = alphaBetaNumerator - alphaBetaDenominator;
        trellis[i][t] = gammaTerm;
      }
    }
    return trellis;
  }

  private double[][][] calculateKsiTable(List<O> observation) {
    double[][][] ksiTrellis = new double[observation.size() - 1][states.size()][states.size()];
    ksiTrellis = populateKsiTrellis(ksiTrellis, A.getForwardTable(), B.getBackwardTable(),
            observation);
    return ksiTrellis;
  }

  private double[][][] populateKsiTrellis(double[][][] trellis, double[][] alpha, double[][] beta,
          List<O> observation) {
    double alphaBetaSum = LogOperations.NEG_INF;
    for (int t = 0; t < observation.size() - 1; ++t) {
      for (Map.Entry<S, Integer> kState : states.entrySet()) {
        int k = kState.getValue();
        double alphaBeta = alpha[k][t] + beta[k][t];
        alphaBetaSum = LogOperations.logAdd(alphaBetaSum, alphaBeta);
      }
      for (Map.Entry<S, Integer> iState : states.entrySet()) {
        for (Map.Entry<S, Integer> jState : states.entrySet()) {
          int i = iState.getValue();
          int j = jState.getValue();
          double itAlpha = alpha[i][t];
          double ijA = A.getAValueAtIndex(i, j);
          double jtp1Beta = beta[j][t + 1];
          int oTp1 = B.getIndexFromOutput(observation.get(t + 1));
          double jtp1B = B.getBValueAtIndex(j, oTp1);
          double numerator = itAlpha + ijA + jtp1Beta + jtp1B;
          double ksiTerm = numerator - alphaBetaSum;
          trellis[t][i][j] = ksiTerm;
        }
      }
    }
    return trellis;
  }

}
