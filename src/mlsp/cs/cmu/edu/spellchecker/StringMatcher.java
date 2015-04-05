package mlsp.cs.cmu.edu.spellchecker;

public class StringMatcher {

  private int technique;

  private DistanceMeasure<Character[], Integer> d;

  public StringMatcher() {
    this.technique = PruningTechnique.NORMAL.getValue();
    this.d = new EditDistance();
  }

  public void setPruningTechnique(PruningTechnique technique) {
    this.technique = technique.getValue();
  }
  
  private void outputTrellis(Integer[][] trellis) {
    for(int i = 0; i < trellis.length; ++i) {
      for(int j = 0; j < trellis[0].length; ++j) {
        System.out.print("\t" + trellis[i][j]);
      }
      System.out.print("\n");
    }
  }

  public Integer levenshteinDistance(String s1, String s2) {

    char[] src = s1.toCharArray();
    char[] target = s2.toCharArray();
    Integer[][] trellis = new Integer[src.length + 1][target.length + 1];
    
    outputTrellis(trellis);

    // initialize alphaTable...
    for (int i = 1; i <= src.length; ++i) {
      trellis[i][0] = i;
    }
    for (int i = 1; i <= target.length; ++i) {
      trellis[0][i] = i;
    }

    // fill alphaTable...
    Integer distance = null;
    for (int j = 1; j <= target.length; ++j) {
      for (int i = 1; i <= src.length; ++i) {
        //alphaTable[i][j] = d.getCost(alphaTable, src, target, i, j);
        distance = trellis[i][j];
      }
    }
    return distance;
  }

}
