package mlsp.cs.cmu.edu.audio;

public enum AudioConstants {

  MONO(1), STEREO(2), KHZ16(16000), KHZ441(44100), KHZ16BUFFER(320), FRAMESHIFT(10), FRAMESIZE(25), 
  MELFREQUENCY_MAX_16KHZ(6800), MELFREQUENCY_MIN_16KHZ(100), MELFREQUENCY_BINS(40), MFCC_SIZE(13);

  private final int value; // Whatever the constant is

  AudioConstants(int val) {
    this.value = val;
  }

  /**
   * @return the value
   */
  public int getValue() {
    return value;
  }

}
