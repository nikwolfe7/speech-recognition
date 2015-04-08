package mlsp.cs.cmu.edu.hmm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Pair;

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
  
  public List<Pair<double[][], double[][][]>> getGammaKsiLookupTable(List<List<O>> observations) {
    List<Pair<double[][], double[][][]>> gammaKsiLookup = new ArrayList<Pair<double[][], double[][][]>>();
    for (List<O> observation : observations) {
      calculateForwardBackwardTables(observation);
      double[][] gamma = calculateGammaTable(observation);
      double[][][] ksi = calculateKsiTable(gamma, observation);
      Pair<double[][], double[][][]> gammaKsi = new Pair<double[][], double[][][]>(gamma, ksi);
      gammaKsiLookup.add(gammaKsi);
    }
    return gammaKsiLookup;
  }
  
  private void calculateForwardBackwardTables(List<O> observation) {
    double fProb = A.forwardProbability(Pi, B, observation);
    double bProb = B.backwardProbability(Pi, A, observation);
    if(fProb != bProb) {
      //System.out.println("Forward/Backward probs differ by: " + Math.abs(fProb-bProb));
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

  private double[][][] calculateKsiTable(double[][] gamma, List<O> observation) {
    double[][][] ksiTrellis = new double[observation.size()-1][states.size()][states.size()];
    ksiTrellis = populateKsiTrellis(ksiTrellis, gamma, A.getForwardTable(), B.getBackwardTable(), observation);
    return ksiTrellis;
  }
  
   
//  private void computeKsi() {
//        for(int t=0;t<len_obseq-1;t++) {
//            double sum = 0.0;
//            for(int i=0;i<getNumStates();i++) {
//                for(int j=lambda.getLLimit(i);j<=lambda.getRLimit(i);j++) {
//                    ksi[t][i][j] = alpha[t][i] * beta[t+1][i] * lambda.getA(i,j) * lambda.getB(j,obSeq[t+1]);
//                    sum += ksi[t][i][j];
//                }    
//            }
//            for(int i=0;i<getNumStates();i++) {
//                for(int j=lambda.getLLimit(i);j<=lambda.getRLimit(i);j++) {
//                    ksi[t][i][j] /= sum;
//                }    
//            }
//        }    
//    }
   

  private double[][][] populateKsiTrellis(double[][][] trellis, double[][] gamma, double[][] alpha,
          double[][] beta, List<O> observation) {
    for (int t = 0; t < observation.size() - 1; ++t) {
      double ksiSum = LogOperations.NEG_INF;
      for(Map.Entry<S, Integer> iState : states.entrySet()) {
        for(Map.Entry<S, Integer> jState: states.entrySet()) {
          int i = iState.getValue();
          int j = jState.getValue();
          O Otp1 = observation.get(t + 1);
          double alphaVal = alpha[i][t];
          double aTableVal = A.getAlphaValue(iState.getKey(), jState.getKey());
          double betaVal = beta[j][t+1];
          double bTableVal = B.getBetaValue(jState.getKey(), Otp1);
          double top = alphaVal + aTableVal + bTableVal + betaVal;
          ksiSum = LogOperations.logAdd(ksiSum, top);
          trellis[t][i][j] = top;
        }
      }
      for(Map.Entry<S, Integer> iState : states.entrySet()) {
        for(Map.Entry<S, Integer> jState: states.entrySet()) {
          int i = iState.getValue();
          int j = jState.getValue();
          double top = trellis[t][i][j];
          double ksiVal = top - ksiSum; // division
          trellis[t][i][j] = ksiVal; 
        }
      }
    }
    return trellis;
  }
}
