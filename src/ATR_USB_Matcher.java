
public class ATR_USB_Matcher {

	private static String os = System.getProperty("os.name").toLowerCase();
	
	public static boolean isATRUSBMicrophone(String mixerInfo) {
		if(os.contains("mac")) {
			if(mixerInfo.contains(AudioStrings.ATR_USB_MICROPHONE.getValue().toLowerCase())) {
				return true;
			}
		} else if (os.contains("win")) {
			// not implemented
			return false;
		} else if (os.contains("linux")) {
			if(mixerInfo.contains("microphone")) {
				return true;
			}
		}
		return false;
	}
}
