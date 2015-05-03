package mlsp.cs.cmu.edu.spellchecker;

public enum CharacterConstants {

  BEGIN_CHARACTER((char) Character.MAX_LOW_SURROGATE), END_CHARACTER((char) Character.MAX_HIGH_SURROGATE);

  private final Character charValue;

  private CharacterConstants(char value) {
    this.charValue = value;
  }

  public Character getValue() {
    return charValue;
  }

}
