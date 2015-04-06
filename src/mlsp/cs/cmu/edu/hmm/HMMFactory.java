package mlsp.cs.cmu.edu.hmm;

public abstract class HMMFactory<S,O> {

  protected AlphaTable<S, O> A;

  protected BetaTable<S, O> B;

  protected PriorTable<S> Pi;
  
  protected ViterbiTable<S, O> Viterbi;
  
  protected GammaKsiTable<S,O> Ksi;

  /**
   * 
   * @param a
   * @param b
   * @param pi
   * @param viterbi
   * @param ksi
   */
  public HMMFactory(AlphaTable<S, O> a, BetaTable<S, O> b, PriorTable<S> pi,
          ViterbiTable<S, O> viterbi, GammaKsiTable<S, O> ksi) {
    super();
    A = a;
    B = b;
    Pi = pi;
    Viterbi = viterbi;
    Ksi = ksi;
  }
  
  public abstract HiddenMarkovModel<S,O> initializeHMM();

}
