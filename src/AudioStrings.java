public enum AudioStrings {

  MICROPHONE("ATR USB Microphone"), 
  MFCC("Mel Frequency Cepstral Coefficients");

  private final String value; // Whatever the constant is

  AudioStrings(String val) {
    this.value = val;
  }

  /**
   * @return the value
   */
  public String getValue() {
    return value;
  }

}
