package mlsp.cs.cmu.edu.hmm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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

  private Integer maxIterations = 250;

  private Double convergenceCriteria = 0.0001;

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
   * Public in case someone wants to use it directly...
   * 
   * @param observations
   */
  public void trainHMM(List<List<O>> observations) {
    double converge = convergenceCriteria + 1;
    double avgLL = LogOperations.NEG_INF;
    double perCharLL = LogOperations.NEG_INF;
    int numIterations = 0;
    A.setDisplayOutput(false);
    B.setDisplayOutput(false);
    System.out.println("\n==================================");
    System.out.println("Running Forward-Backward Algorithm");
    System.out.println("==================================\n");

    while (converge > convergenceCriteria && ++numIterations < maxIterations) {
      doParameterReestimation(observations);
      double prevPerCharLL = perCharLL;
      avgLL = LogOperations.NEG_INF; // reset
      double numChars = 0;
      for (List<O> observation : observations) {
        double forwardProb = A.forwardProbability(Pi, B, observation);
        double backwardProb = B.backwardProbability(Pi, A, observation);
        //Viterbi.getViterbiBestPath(observation);
        avgLL = LogOperations.logAdd(avgLL, Math.max(forwardProb, backwardProb));
        numChars += observation.size();
      }
      perCharLL = avgLL / numChars;
      avgLL = avgLL / observations.size();
      converge = Math.abs(perCharLL - prevPerCharLL);
      System.out.println("Iteration " + numIterations + " Avg LL: " + avgLL
              + ", PC-LL: " + perCharLL);
    }
    System.out.println("Converged after " + numIterations + " iterations. Final Avg LL: "
            + avgLL + " Final PC-LL: " + perCharLL);
    System.out.println("Printing final Prior, Transition and Emission Probabilities...");
    try {
      Pi.printPriors();
      A.printTrellis();
      B.printTrellis();
    } catch (IOException e) {
      e.printStackTrace();
    }
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
    List<Pair<double[][], double[][][]>> gammaKsiLookup = Ksi.getGammaKsiLookupTable(observations);
    /* ======================================================= */
    // M-step
    // Re-estimate Pi
    /* ======================================================= */
    for (Map.Entry<S, Integer> state : states.entrySet()) {
      double gammaPiSum = LogOperations.NEG_INF;
      int i = state.getValue();
      for (int m = 0; m < M; ++m) {
        double[][] gammaTable = gammaKsiLookup.get(m).getFirst();
        double gammaValue = gammaTable[i][0];
        gammaPiSum = LogOperations.logAdd(gammaPiSum, gammaValue);
      }
      double priorUpdate = gammaPiSum / M;
      Pi.setPriorAtIndex(i, priorUpdate);
    }
    /* ======================================================= */
    // Re-estimate A
    /* ======================================================= */
    for (Map.Entry<S, Integer> iState : states.entrySet()) {
      for (Map.Entry<S, Integer> jState : states.entrySet()) {
        int i = iState.getValue();
        int j = jState.getValue();
        double numerator = LogOperations.NEG_INF;
        double denominator = LogOperations.NEG_INF;
        for (int m = 0; m < M; ++m) {
          double[][][] ksiTable = gammaKsiLookup.get(m).getSecond();
          List<O> observation = observations.get(m);
          for (int t = 0; t < observation.size() - 1; ++t) {
            double ksiVal = ksiTable[t][i][j];
            numerator = LogOperations.logAdd(numerator, ksiVal);
            for (Map.Entry<S, Integer> jSubState : states.entrySet()) {
              int jSub = jSubState.getValue();
              double jSubKsiVal = ksiTable[t][i][jSub];
              denominator = LogOperations.logAdd(denominator, jSubKsiVal);
            }
          }
        } // end of M loop
        double newAlphaIJ = numerator - denominator; // division
        // UPDATE A table
        A.setAValueAtIndex(i, j, newAlphaIJ);
      }
    }
    /* ======================================================= */
    // Re-estimate B
    /* ======================================================= */
    double[][] newBeta = new double[B.getBTable().length][B.getBTable()[0].length];
    for (Map.Entry<O, Integer> output : outputs.entrySet()) {
      O Vk = output.getKey();
      int j = B.getIndexFromOutput(Vk);
      for (Map.Entry<S, Integer> state : states.entrySet()) {
        int i = state.getValue();
        double gammaDenominator = LogOperations.NEG_INF;
        double gammaNumerator = LogOperations.NEG_INF;
        for (int m = 0; m < M; ++m) {
          List<O> observation = observations.get(m);
          double[][] gammaTable = gammaKsiLookup.get(m).getFirst();
          for (int t = 0; t < observation.size(); ++t) {
            O Ot = observation.get(t);
            double gammaVal = gammaTable[i][t];
            gammaDenominator = LogOperations.logAdd(gammaDenominator, gammaVal);
            if (Ot.equals(Vk)) { // delta function
              gammaNumerator = LogOperations.logAdd(gammaNumerator, gammaVal);
            }
          }
        } // end iteration over data
        double betaUpdate = gammaNumerator - gammaDenominator; // division
        newBeta[i][j] = betaUpdate;
      }
    } // end beta table update
    // REPLACE BETA TABLE
    B.setBetaTable(newBeta);
  }

}
