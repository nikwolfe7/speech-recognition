package mlsp.cs.cmu.edu.spellchecker;

public class SpellChecker {
  
  public static void main(String[] args) {
    StringMatcher matcher = new StringMatcher();
    matcher.levenshteinDistance("kitten", "sitting");
  }
  

}
