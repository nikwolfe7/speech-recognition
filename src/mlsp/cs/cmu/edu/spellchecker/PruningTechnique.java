package mlsp.cs.cmu.edu.spellchecker;

public enum PruningTechnique {

  NORMAL(0),
  BEAM_WIDTH(3),
  ABSOLUTE(3);
  
  private final int value;
  
  private PruningTechnique(int val) {
    this.value = val;
  }
  
  public int getValue() {
    return value;
  }
  
}
