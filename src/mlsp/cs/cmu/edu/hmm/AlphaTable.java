package mlsp.cs.cmu.edu.hmm;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Scanner;

public abstract class AlphaTable<S> {
  
    protected List<S> states;
    protected double[][] trellis;
    
    public AlphaTable(String filename, List<S> states) {
      this.trellis = new double[states.size()][states.size()];
      this.states = states;
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
    
    public double getAlphaValue(S fromState, S toState) {
      int fIndex = states.indexOf(fromState);
      int tIndex = states.indexOf(toState);
      return trellis[fIndex][tIndex];
    }
    
    public double getAlphaValueFromIndex(int from, int to) {
      return trellis[from][to];
    }
    
    public void setAlphaValue(S fromState, S toState, double prob) {
      int fIndex = states.indexOf(fromState);
      int tIndex = states.indexOf(toState);
      trellis[fIndex][tIndex] = LogOperations.log(prob);
    }
    
    public void setAlphaValueAtIndex(int from, int to, double prob) {
      trellis[from][to] = prob;
    }

    public List<S> getStates() {
      return states;
    }

    protected abstract void loadTrellisFromLine(String nextLine); 
    
    
    
}
