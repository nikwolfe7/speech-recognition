package mlsp.cs.cmu.edu.filters;

public class RemoveDCOffset implements FrameFilter {

  @Override
  public Double[] doFilter(Double[] frame) {
    Double sum = 0.0;
    for(int i = 0; i < frame.length; i++) {
      sum += frame[i];
    }
    Double avg = sum / frame.length;
    for(int i = 0; i < frame.length; i++) {
      frame[i] = frame[i] - avg;
    }
    return frame;
  }

  @Override
  public String getName() {
    return "DC Offset Filter";
  }

}
