package mlsp.cs.cmu.edu.hmm;

public abstract class HMMFactory<S,O> {

  public AlphaTable<S, O> A;

  public BetaTable<S, O> B;

  public PriorTable<S> Pi;
  
  public ViterbiTable<S, O> Viterbi;

  /**
   * @param a
   * @param b
   * @param pi
   * @param viterbi
   */
  public HMMFactory(AlphaTable<S, O> a, BetaTable<S, O> b, PriorTable<S> pi,
          ViterbiTable<S, O> viterbi) {
    super();
    A = a;
    B = b;
    Pi = pi;
    Viterbi = viterbi;
  }
  
  public abstract HiddenMarkovModel<S,O> initializeHMM();

}
