package mlsp.cs.cmu.edu.hmm;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Scanner;

public abstract class BetaTable<S,O> {

  protected List<S> states;
  protected List<O> outputs;
  protected double[][] trellis;
  
  /* Assumes full path */
  public BetaTable(String filename, List<S> states, List<O> outputs) {
    this.trellis = new double[states.size()][outputs.size()];
    this.states = states;
    this.outputs = outputs;
    File file = new File(filename);
    try {
      Scanner scn = new Scanner(file);
      while(scn.hasNextLine()) {
        loadTrellisFromLine(scn.nextLine());
      }
      scn.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
  
  public void printTrellis() {
    DecimalFormat df = new DecimalFormat("#.###");
    for(int i = 0; i < trellis.length; ++i) {
      for(int j = 0; j < trellis[0].length; ++j) {
        System.out.print(df.format(trellis[i][j]) + "\t");
      }
      System.out.println("\n");
    }
  }
  
  public double getBetaValue(S state, O output) {
    int sIndex = states.indexOf(state);
    int oIndex = outputs.indexOf(output);
    return trellis[sIndex][oIndex];
  }
  
  public double getBetaValueFromIndex(int s, int o) {
    return trellis[s][o];
  }
  
  public void setBetaValue(S state, O output, double prob) {
    int sIndex = states.indexOf(state);
    int oIndex = outputs.indexOf(output);
    trellis[sIndex][oIndex] = LogOperations.log(prob);
  }
  
  public void setBetaValueAtIndex(int s, int o, double prob) {
    trellis[s][o] = prob;
  }
  
  public List<S> getStates() {
    return states;
  }

  public List<O> getOutputs() {
    return outputs;
  }

  protected abstract void loadTrellisFromLine(String line);

}
