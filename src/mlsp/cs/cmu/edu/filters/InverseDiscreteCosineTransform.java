package mlsp.cs.cmu.edu.filters;

import mlsp.cs.cmu.edu.audio.AudioConstants;
import mlsp.cs.cmu.edu.features.MFCCFeatureVectorContainer;

public class InverseDiscreteCosineTransform extends FrameFilter {
  
  /**
   * From http://developer.download.nvidia.com/assets/cuda/files/dct8x8.pdf, Page 5
   */
  @Override
  protected double[] doFilterImplementation(double[] frame) {
    int N = AudioConstants.IDCT_SIZE.getValue();
    double[] idct = new double[N];
    for(int i = 0; i < idct.length; i++) {
      for(int j = 0; j < frame.length; j++) {
        idct[i] += alpha(j, N) * frame[j] * Math.cos((Math.PI * (2 * i + 1) * j) / (2 * N));
      }
    }
    return idct;
  }
  
  private double alpha(int u, int N) {
    if(u == 0) {
      return Math.sqrt(1.0/N);
    } else {
      return Math.sqrt(2.0/N);
    }
  }

  /*
  private double[] getPowerOfTwoArrayPlusOne(double[] frame) {
    int padding = 2;
    while (padding < frame.length) {
      padding = padding * 2;
    }
    double[] signal = new double[padding + 1];
    System.arraycopy(frame, 0, signal, 0, frame.length);
    return signal;
  }
  */

  @Override
  public void visit(MFCCFeatureVectorContainer container) {
    container.addRecoveredLogMelSpectrumFeatureFrame(getLastProcessedFrame());
  }

  @Override
  public String getName() {
    return "Inverse Discrete Cosine Transform";
  }
}
