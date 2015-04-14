package mlsp.cs.cmu.edu.hmm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CharacterAlpha extends AlphaTable<String, Character> {
  
  private static boolean logProbs;

  private CharacterAlpha(String filename, List<String> states, boolean initRandom) {
    super(filename, states, initRandom);
  }

  public static AlphaTable<String, Character> getInstance(String filename, boolean convertToLogs, boolean initRandom) {
    logProbs = convertToLogs;
    List<String> states = Arrays.asList("V", "C");
    return new CharacterAlpha(filename, states, initRandom);
  }

  @Override
  protected void loadTrellisFromLine(String nextLine) {
    String[] arr = nextLine.split(" ");
    String fromState = arr[0];
    for (int i = 1; i < arr.length; ++i) {
      String toState = arr[i].split(":")[0];
      double prob = Double.parseDouble(arr[i].split(":")[1]);
      if(logProbs)
        setAValue(fromState, toState, LogOperations.log(prob));
      else
        setAValue(fromState, toState, prob);
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
