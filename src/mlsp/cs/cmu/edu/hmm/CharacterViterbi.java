package mlsp.cs.cmu.edu.hmm;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CharacterViterbi extends ViterbiTable<String, Character> {

  public CharacterViterbi(AlphaTable<String, Character> alpha, 
          BetaTable<String, Character> beta,
          PriorTable<String> priors) {
    super(alpha, beta, priors);
  }

  @Override
  protected List<Character> parseFileForObservations(Scanner scn) {
    List<Character> charList = new ArrayList<Character>();
    while(scn.hasNextLine()) {
      char[] list = scn.nextLine().trim().toCharArray();
      for(Character c : list) {
        charList.add(c);
      }
    }
    return charList;
  }

}
