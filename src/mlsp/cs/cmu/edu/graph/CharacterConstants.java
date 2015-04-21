package mlsp.cs.cmu.edu.graph;

public enum CharacterConstants {

  BEGIN_CHARACTER((char) 2), END_CHARACTER((char) 3);

  private final Character charValue;

  private CharacterConstants(char value) {
    this.charValue = value;
  }

  public Character getValue() {
    return charValue;
  }

}
