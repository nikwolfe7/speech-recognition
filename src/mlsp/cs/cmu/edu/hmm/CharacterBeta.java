package mlsp.cs.cmu.edu.hmm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CharacterBeta extends BetaTable<String, Character> {

  private CharacterBeta(String filename, List<String> states, List<Character> outputs) {
    super(filename, states, outputs);
  }

  public static CharacterBeta getInstance(String filename) {
    List<String> states = Arrays.asList("V", "C", "D");
    /*List<Character> outputs = Arrays.asList(' ', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z');
    */
    List<Character> outputs = Arrays.asList('M','L','S','P');
    return new CharacterBeta(filename, states, outputs);
  }

  @Override
  protected void loadTrellisFromLine(String line) {
    String[] arr = line.split("\t");
    String state = arr[0];
    for (int i = 1; i < arr.length; ++i) {
      Character output = arr[i].split(":")[0].charAt(0);
      double prob = Double.parseDouble(arr[i].split(":")[1]);
      setBetaValue(state, output, LogOperations.log(prob));
    }
  }

  @Override
  protected List<Character> processObservationFromTextLine(String nextLine) {
    char[] list = nextLine.trim().toCharArray();
    List<Character> charList = new ArrayList<Character>();
    for (Character c : list) {
      charList.add(c);
    }
    return charList;
  }
}
