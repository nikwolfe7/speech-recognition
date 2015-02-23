package mlsp.cs.cmu.edu.audio;

public enum AudioConstants {

  MONO(1), STEREO(2), KHZ16(16000), KHZ441(44100), KHZ16BUFFER(320), FRAMESHIFT(10), FRAMESIZE(25);

  private final Integer value; // Whatever the constant is

  AudioConstants(Integer val) {
    this.value = val;
  }

  /**
   * @return the value
   */
  public Integer getValue() {
    return value;
  }

}
