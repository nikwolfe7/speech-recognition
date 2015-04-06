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

    HMMFactory<String, Character> hmmFactory = CharacterHMMFactory.getInstance();
    HiddenMarkovModel<String, Character> HMM = hmmFactory.initializeHMM();

    HMM.A.getObservationProbability(HMM.Pi, HMM.B, "./hw7-data/hmm-train-cleaned.txt");
    HMM.B.getObservationProbability(HMM.Pi, HMM.A, "./hw7-data/hmm-train-cleaned.txt");

    HMM.Viterbi.getViterbiBestPathFromFile("./hw7-data/hmm-decode-cleaned.txt");
    HMM.Viterbi.getViterbiBestPathFromFile("./hw7-data/hmm-train-cleaned.txt");
    HMM.Viterbi.getViterbiBestPathFromFile("./hw7-data/hmm-test-cleaned.txt");
    HMM.Viterbi.getViterbiBestPathFromFile("./hw7-data/hmm-train-japanese-cleaned.txt");
    HMM.Viterbi.getViterbiBestPathFromFile("./hw7-data/hmm-test-japanese-cleaned.txt");
  }

}
