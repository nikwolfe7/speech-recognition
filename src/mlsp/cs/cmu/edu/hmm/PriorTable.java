package mlsp.cs.cmu.edu.hmm;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public abstract class PriorTable<S> {

  protected List<S> states;

  protected Map<S, Double> priors;

  public PriorTable(String filename, List<S> states) {
    this.states = states;
    this.priors = new HashMap<S, Double>();
    File file = new File(filename);
    try {
      Scanner scn = new Scanner(file);
      while (scn.hasNextLine()) {
        loadPriorFromLine(scn.nextLine());
      }
      scn.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
  
  public void printPriors() {
    for(S key: priors.keySet()) {
      System.out.println(key + " : " + priors.get(key));
    }
  }
  
  public List<S> getStates() {
    return states;
  }
  
  public void setPrior(S state, Double prior) {
    priors.put(state, prior);
  }
  
  public double getPrior(S state) {
    return priors.get(state);
  }

  protected abstract void loadPriorFromLine(String nextLine);
  
}
