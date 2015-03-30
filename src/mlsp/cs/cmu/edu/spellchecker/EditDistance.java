package mlsp.cs.cmu.edu.spellchecker;

public class EditDistance implements DistanceMeasure<Character[], Integer> {

  @Override
  public int getCost(Integer[][] trellis, Character[] src, Character[] target, int i, int j) {
    if(src[i] == target[j]) {
      if(i > 0 && j > 0) {
        return trellis[i-1][j-1];
      }else {
        return 0;
      }
    } else {
      int deletion = trellis[i-1][j] + 1;
      int insertion = trellis[i][j-1] + 1;
      int substitution = trellis[i-1][j-1] + 1;
      return Math.min(substitution, Math.min(deletion, insertion));
    }
  }

  @Override
  public int getScore(Integer[][] trellis, Character[] src, Character[] target, int i, int j) {
    if(src[i] == target[j]) {
      if(i > 0 && j > 0) {
        return trellis[i-1][j-1];
      }else {
        return 0;
      }
    } else {
      int deletion = trellis[i-1][j] + 1;
      int insertion = trellis[i][j-1] + 1;
      int substitution = trellis[i-1][j-1] + 1;
      return Math.max(substitution, Math.max(deletion, insertion));
    }
  }

}
