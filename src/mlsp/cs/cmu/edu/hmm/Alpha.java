package mlsp.cs.cmu.edu.hmm;

import java.util.Arrays;
import java.util.List;

public class Alpha extends AlphaTable<String> {

  private Alpha(String filename, List<String> states) {
    super(filename, states);
  }
  
  public static Alpha getInstance(String filename) {
    List<String> states = Arrays.asList("V","C");
    return new Alpha(filename, states);
  }

  @Override
  protected void loadTrellisFromLine(String nextLine) {
    String[] arr = nextLine.split(" ");
    String fromState = arr[0];
    for(int i = 1; i < arr.length; ++i) {
      String toState = arr[i].split(":")[0];
      double prob = Double.parseDouble(arr[i].split(":")[1]);
      setAlphaValue(fromState, toState, prob);
    }
  }
}
