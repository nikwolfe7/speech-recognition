package mlsp.cs.cmu.edu.hmm;

public class CharacterHMMFactory extends HMMFactory<String, Character> {
  
  private static String rootFolder = "./hw7-data/";
  
  private CharacterHMMFactory(AlphaTable<String, Character> a, BetaTable<String, Character> b,
          PriorTable<String> pi, ViterbiTable<String, Character> viterbi, GammaKsiTable<String, Character> ksi) {
    super(a, b, pi, viterbi, ksi);
    // TODO Auto-generated constructor stub
  }
  
  public static CharacterHMMFactory getInstance() {
    PriorTable<String> priors = CharacterPriors.getInstance(rootFolder + "bhiksha-hmm-priors.txt");
    BetaTable<String, Character> beta = CharacterBeta.getInstance(rootFolder +"bhiksha-hmm-emit.txt");
    AlphaTable<String, Character> alpha = CharacterAlpha.getInstance(rootFolder + "bhiksha-hmm-trans.txt");
    ViterbiTable<String, Character> viterbi = new CharacterViterbi(alpha, beta, priors);
    GammaKsiTable<String, Character> ksi = new CharacterGammaKsiTable(alpha, beta, priors);
    return new CharacterHMMFactory(alpha, beta, priors, viterbi, ksi);
  }

  @Override
  public HiddenMarkovModel<String, Character> initializeHMM() {
    return new CharacterHMM(Viterbi, Ksi); 
  }


}
