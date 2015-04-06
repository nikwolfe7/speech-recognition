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

    PriorTable<String> priors = Pi.getInstance("./hw7-data/hmm-priors.txt");
    priors.printPriors();

    BetaTable<String, Character> beta = CharacterBeta.getInstance("./hw7-data/hmm-emit.txt");
    beta.printTrellis();

    AlphaTable<String, Character> alpha = CharacterAlpha.getInstance("./hw7-data/hmm-trans.txt");
    alpha.printTrellis();

    alpha.getObservationProbability(priors, beta, "./hw7-data/hmm-train-cleaned.txt");
    beta.getObservationProbability(priors, alpha, "./hw7-data/hmm-train-cleaned.txt");

    ViterbiTable<String, Character> viterbi = new CharacterViterbi(alpha, beta, priors);
    viterbi.getViterbiBestPathFromFile("./hw7-data/hmm-decode-cleaned.txt");
    viterbi.getViterbiBestPathFromFile("./hw7-data/hmm-train-cleaned.txt");
    viterbi.getViterbiBestPathFromFile("./hw7-data/hmm-test-cleaned.txt");
  }

}
