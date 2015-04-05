package mlsp.cs.cmu.edu.hmm;

import java.util.Arrays;
import java.util.List;

public class Beta extends BetaTable<String, Character> {

  private Beta(String filename, List<String> states, List<Character> outputs) {
    super(filename, states, outputs);
  }

  public static Beta getInstance(String filename) {
    List<String> states = Arrays.asList("V","C");
    List<Character> outputs = Arrays.asList(
            ' ','A','B','C','D','E','F','G','H',
            'I','J','K','L','M','N','O','P','Q',
            'R','S','T','U','V','W','X','Y','Z');
    return new Beta(filename, states, outputs);
  }

  @Override
  protected void loadTrellisFromLine(String line) {
    String[] arr = line.split("\t");
    String state = arr[0];
    for(int i = 1; i < arr.length; ++i) {
      Character output = arr[i].split(":")[0].charAt(0);
      double prob = Double.parseDouble(arr[i].split(":")[1]);
      setBetaValue(state, output, prob);
    }
  }

}
