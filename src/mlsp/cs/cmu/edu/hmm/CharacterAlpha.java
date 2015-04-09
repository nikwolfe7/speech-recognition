package mlsp.cs.cmu.edu.hmm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CharacterAlpha extends AlphaTable<String, Character> {

  private CharacterAlpha(String filename, List<String> states) {
    super(filename, states);
  }

  public static AlphaTable<String, Character> getInstance(String filename) {
    List<String> states = Arrays.asList("V", "C", "D");
    return new CharacterAlpha(filename, states);
  }

  @Override
  protected void loadTrellisFromLine(String nextLine) {
    String[] arr = nextLine.split(" ");
    String fromState = arr[0];
    for (int i = 1; i < arr.length; ++i) {
      String toState = arr[i].split(":")[0];
      double prob = Double.parseDouble(arr[i].split(":")[1]);
      setAlphaValue(fromState, toState, LogOperations.log(prob));
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
