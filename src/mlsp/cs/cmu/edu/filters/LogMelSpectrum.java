package mlsp.cs.cmu.edu.filters;

public class LogMelSpectrum implements FrameFilter {

  @Override
  public double[] doFilter(double[] frame) {
    for(int i = 0; i < frame.length; i++) {
      frame[i] = Math.log(frame[i]);
    }
    return frame;
  }

  @Override
  public String getName() {
    return "Log Mel Spectrum";
  }

}
