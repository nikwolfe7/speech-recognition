package mlsp.cs.cmu.edu.hmm;

public class LogOperations {

  public static double logAdd(double left, double right) {
    if (right < left) {
      return left + Math.log1p(Math.exp(right - left));
    } else if (right > left) {
      return right + Math.log1p(Math.exp(left - right));
    } else {
      return left + Math.log1p(1);
    }
  }

  public static double logb(double value, double base) {
    if (value == 0.0)
      return Math.log(1.0e-25) / Math.log(base);
    else
      return Math.log(value) / Math.log(base);
  }

  public static double log2(double a) {
    return logb(a, 2);
  }
  
  public static double log(double a) {
    return logb(a, Math.E);
  }

}
