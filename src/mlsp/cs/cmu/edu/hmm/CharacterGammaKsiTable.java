package mlsp.cs.cmu.edu.hmm;

public class CharacterGammaKsiTable extends GammaKsiTable<String, Character> {

  public CharacterGammaKsiTable(AlphaTable<String, Character> a, BetaTable<String, Character> b,
          PriorTable<String> pi) {
    super(a, b, pi);
  }

}
