public enum AudioStrings {

  ATR_USB_MICROPHONE("ATR USB Microphone");

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
