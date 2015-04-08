package mlsp.cs.cmu.edu.hmm;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
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

  @Override
  protected void calculateAccuracy(List<String> bestPath, List<Character> observation) {
    //List<Character> vowels = new ArrayList<Character>(Arrays.asList('A','E','I','O','U','Y',' '));
    List<Character> vowels = new ArrayList<Character>(Arrays.asList('M'));
    int correct = 0;
    double total = observation.size();
    for(int i = 0; i < observation.size(); i++) {
      Character obs = observation.get(i);
      Character pred = bestPath.get(i).charAt(0);
      if(vowels.contains(obs) && pred == 'V')
        correct++;
      else if (!vowels.contains(obs) && pred == 'C')
        correct++;
    }
    double accuracy = correct/total;
    DecimalFormat df = new DecimalFormat("#.###");
    System.out.println("Accuracy: "+df.format(accuracy));
  }

}
