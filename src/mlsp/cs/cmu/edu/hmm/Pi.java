package mlsp.cs.cmu.edu.hmm;

import java.util.ArrayList;
import java.util.List;

public class Pi extends PriorTable<String> {

  private Pi(String filename, List<String> states) {
    super(filename, states);
  }
  
  public static Pi getInstance(String filename) {
    List<String> states = new ArrayList<String>();
    return new Pi(filename, states);
  }

  @Override
  protected void loadPriorFromLine(String nextLine) {
    String[] arr = nextLine.split(":");
    String c = arr[0];
    Double prob = Double.parseDouble(arr[1]);
    states.add(c);
    priors.put(c, prob);
  }

}
