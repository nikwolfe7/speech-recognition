package mlsp.cs.cmu.edu.hmm;

import java.io.IOException;

public class HMMDriver {
  
  public static void main(String[] args) throws IOException {
    
    DataCleaner cleaner = new DataCleaner();
    cleaner.cleanData("./hw7-data/hmm-train.txt", true);
    cleaner.cleanData("./hw7-data/hmm-test.txt", false);
    cleaner.cleanData("./hw7-data/hmm-decode.txt", false);
    cleaner.cleanData("./hw7-data/hmm-train-japanese.txt", false);
    cleaner.cleanData("./hw7-data/hmm-test-japanese.txt", false);
   
    BetaTable<String,Character> beta = Beta.getInstance("./hw7-data/hmm-emit.txt");
    beta.printTrellis();
    System.out.println(beta.getStates());
    System.out.println(beta.getOutputs());
    
    AlphaTable<String> alpha = Alpha.getInstance("./hw7-data/hmm-trans.txt");
    alpha.printTrellis();
    System.out.println(alpha.getStates());
    /**/
    
  }

}
