package mlsp.cs.cmu.edu.hmm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nwolfe
 *
 */
public abstract class HiddenMarkovModel<S,O> {
  
  protected Map<S, Integer> states;

  protected Map<O, Integer> outputs;

  public AlphaTable<S, O> A;

  public BetaTable<S, O> B;

  public PriorTable<S> Pi;
  
  public ViterbiTable<S, O> Viterbi;

  public HiddenMarkovModel(ViterbiTable<S, O> viterbiTable) {
    this.A = viterbiTable.getAlpha();
    this.B = viterbiTable.getBeta();
    this.Pi = viterbiTable.getPriors();
    this.Viterbi = viterbiTable;
    updateStatesAndOutputs();
  }

  private void updateStatesAndOutputs() {
    this.states = new HashMap<S, Integer>();
    this.outputs = new HashMap<O, Integer>();
    List<S> sList = A.getStates();
    List<O> oList = B.getOutputs();
    int i = 0;
    for (S state : sList)
      states.put(state, i++);
    int j = 0;
    for (O output : oList)
      outputs.put(output, j++);
  }
  
  
}
