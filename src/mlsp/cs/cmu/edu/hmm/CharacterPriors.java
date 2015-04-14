package mlsp.cs.cmu.edu.hmm;

import java.util.ArrayList;
import java.util.List;

public class CharacterPriors extends PriorTable<String> {
  
  private static boolean logProbs;
  
  private CharacterPriors(String filename, List<String> states, boolean initRandom) {
    super(filename, states, initRandom);
  }
  
  public static CharacterPriors getInstance(String filename, boolean convertToLogs, boolean initRandom) {
    logProbs = convertToLogs;
    List<String> states = new ArrayList<String>();
    return new CharacterPriors(filename, states, initRandom);
  }

  @Override
  protected void loadPriorFromLine(String nextLine) {
    String[] arr = nextLine.split(":");
    String c = arr[0];
    Double prob = Double.parseDouble(arr[1]);
    states.add(c);
    if(logProbs) 
      priors.put(c, LogOperations.log(prob));
    else
      priors.put(c, prob);
  }

}
