package mlsp.cs.cmu.edu.hmm;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.math3.util.Pair;

/**
 * @author nwolfe
 *
 */
public abstract class HiddenMarkovModel<S, O> {

  protected Map<S, Integer> states;

  protected Map<O, Integer> outputs;

  public AlphaTable<S, O> A;

  public BetaTable<S, O> B;

  public PriorTable<S> Pi;

  public ViterbiTable<S, O> Viterbi;

  private GammaKsiTable<S, O> Ksi;

  private Integer maxIterations = 100;

  private Double convergenceCriteria = 1.0;

  public HiddenMarkovModel(ViterbiTable<S, O> viterbiTable, GammaKsiTable<S, O> ksi) {
    this.A = viterbiTable.getAlpha();
    this.B = viterbiTable.getBeta();
    this.Pi = viterbiTable.getPriors();
    this.Viterbi = viterbiTable;
    this.Ksi = ksi;
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

  /**
   * Should be overridden if a different parsing strategy is needed...
   * 
   * Meant for when the M observations in the data set are on separate lines in a file...
   * 
   * @param filename
   */
  public void trainHMMFromFile(String filename) {
    File file = new File(filename);
    try {
      Scanner scn = new Scanner(file);
      List<List<O>> observations = new ArrayList<List<O>>();
      while (scn.hasNextLine())
        observations.add(readLineFromFile(scn.nextLine()));
      scn.close();
      trainHMM(observations);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  protected abstract List<O> readLineFromFile(String nextLine);

  /**
   * Train HMM by iteratively running Forward-Backward algorithm and checking for convergence
   * 
   * @param observations
   */
  private void trainHMM(List<List<O>> observations) {
    double converge = convergenceCriteria + 1;
    double averageLogLikelihood = 0;
    double perCharacterLogLikelihood = 0;
    int numIterations = 0;
    A.setDisplayOutput(false);
    B.setDisplayOutput(false);
    System.out.println("\n==================================");
    System.out.println("Running Forward-Backward Algorithm");
    System.out.println("==================================\n");

    while (converge > convergenceCriteria && ++numIterations < maxIterations) {
      doParameterReestimation(observations);
      double prevAverageLogLikelihood = averageLogLikelihood;
      averageLogLikelihood = 0; // reset
      double numChars = 0;
      for (List<O> observation : observations) {
        double forwardProb = A.forwardProbability(Pi, B, observation);
        double backwardProb = B.backwardProbability(Pi, A, observation);
        Viterbi.getViterbiBestPath(observation);
        averageLogLikelihood += Math.min(forwardProb, backwardProb);
        numChars += observation.size();
      }
      perCharacterLogLikelihood = averageLogLikelihood / numChars;
      averageLogLikelihood = averageLogLikelihood / observations.size();
      converge = Math.abs(averageLogLikelihood - prevAverageLogLikelihood);
      System.out.println("Iteration " + numIterations + " Avg LL: " + averageLogLikelihood
              + ", PC-LL: " + perCharacterLogLikelihood);
    }
    System.out.println("Converged after " + numIterations + " iterations. Final Avg LL: "
            + averageLogLikelihood + " Final PC-LL: " + perCharacterLogLikelihood);
  }

  /**
   * oh FUCK! Baum Welch Bitches!
   * 
   * @param observation
   */
  private void doParameterReestimation(List<List<O>> observations) {
    /* ======================================================= */
    // E-step
    // Calculate Gamma and Ksi values for all observations
    /* ======================================================= */
    double M = observations.size();
    List<Pair<double[][], double[][][]>> gammaKsiLookup = new ArrayList<Pair<double[][], double[][][]>>();
    for (List<O> observation : observations) {
      double[][] gamma = Ksi.calculateGammaTable(observation);
      double[][][] ksi = Ksi.calculateKsiTable(gamma, observation);
      Pair<double[][], double[][][]> gammaKsi = new Pair<double[][], double[][][]>(gamma, ksi);
      gammaKsiLookup.add(gammaKsi);
    }
    /* ======================================================= */
    // M-step
    // Re-estimate Pi
    /* ======================================================= */
    for (Map.Entry<S, Integer> state : states.entrySet()) {
      double gammaPiSum = 0;
      int i = state.getValue();
      for (int m = 0; m < M; ++m) {
        double[][] gammaTable = gammaKsiLookup.get(m).getFirst();
        double gammaValue = gammaTable[i][0];
        if (gammaPiSum == 0) {
          gammaPiSum = gammaValue;
        } else {
          gammaPiSum = LogOperations.logAdd(gammaPiSum, gammaValue);
        }
      }
      double mTerm = LogOperations.log(1.0 / M);
      double priorUpdate = gammaPiSum + mTerm;
      Pi.setPrior(state.getKey(), priorUpdate);
    }
    /* ======================================================= */
    // Re-estimate A
    /* ======================================================= */
    for (Map.Entry<S, Integer> iState : states.entrySet()) {
      for (Map.Entry<S, Integer> jState : states.entrySet()) {
        int i = iState.getValue();
        int j = jState.getValue();
        double numerator = 0;
        double denominator = 0;
        for (int m = 0; m < M; ++m) {
          double[][][] ksiTable = gammaKsiLookup.get(m).getSecond();
          List<O> observation = observations.get(m);
          for (int t = 0; t < observation.size() - 1; ++t) {
            double ksiVal = ksiTable[t][i][j];
            if (numerator == 0) {
              numerator = ksiVal;
            } else {
              numerator = LogOperations.logAdd(numerator, ksiVal);
            }
            for (Map.Entry<S, Integer> jSubState : states.entrySet()) {
              int jSub = jSubState.getValue();
              double jSubKsiVal = ksiTable[t][i][jSub];
              if (denominator == 0) {
                denominator = jSubKsiVal;
              } else {
                denominator = LogOperations.logAdd(denominator, jSubKsiVal);
              }
            }
          }
        } // end of M loop
        double newAlphaIJ = numerator - denominator; // division
        // UPDATE A table
        A.setAlphaValue(iState.getKey(), jState.getKey(), newAlphaIJ);
      }
    }
    /* ======================================================= */
    // Re-estimate B
    /* ======================================================= */
    double[][] newBeta = new double[B.getBetaTable().length][B.getBetaTable()[0].length];
    for (Map.Entry<O, Integer> output : outputs.entrySet()) {
      O Vk = output.getKey();
      for (Map.Entry<S, Integer> state : states.entrySet()) {
        int i = state.getValue();
        double gammaDenominator = 0;
        double gammaNumerator = 0;
        for (int m = 0; m < M; ++m) {
          List<O> observation = observations.get(m);
          double[][] gammaTable = gammaKsiLookup.get(m).getFirst();
          for (int t = 0; t < observation.size(); ++t) {
            O Ot = observation.get(t);
            double gammaVal = gammaTable[i][t];
            if (gammaDenominator == 0)
              gammaDenominator = gammaVal;
            else
              gammaDenominator = LogOperations.logAdd(gammaDenominator, gammaVal);
            if (Ot.equals(Vk)) { // delta function
              if (gammaNumerator == 0)
                gammaNumerator = gammaVal;
              else
                gammaNumerator = LogOperations.logAdd(gammaNumerator, gammaVal);
            }
          }
        } // end iteration over data
        double betaUpdate = gammaNumerator - gammaDenominator; // division
        newBeta[i][outputs.get(Vk)] = betaUpdate;
      }
    } // end beta table update
    // REPLACE BETA TABLE
    B.setBetaTable(newBeta);
  }

}
