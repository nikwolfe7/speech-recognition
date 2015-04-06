package mlsp.cs.cmu.edu.hmm;

import java.util.ArrayList;
import java.util.List;

public class CharacterHMM extends HiddenMarkovModel<String, Character> {

  public CharacterHMM(ViterbiTable<String, Character> viterbiTable, GammaKsiTable<String, Character> ksi) {
    super(viterbiTable, ksi);
    // TODO Auto-generated constructor stub
  }

  @Override
  protected List<Character> readLineFromFile(String nextLine) {
    char[] list = nextLine.trim().toCharArray();
    List<Character> charList = new ArrayList<Character>();
    for (Character c : list) {
      charList.add(c);
    }
    return charList;
  }

}
