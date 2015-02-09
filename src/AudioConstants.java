public enum AudioConstants {

  MONO(1), STEREO(2), KHZ16(16000), KHZ441(44100);

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
