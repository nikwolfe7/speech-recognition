import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.Mixer.Info;

public class Microphone {

	private String preferredMicrophone = AudioStrings.PREFERRED_MICROPHONE.getValue();
	private Mixer.Info foundMicrophone;

	public Microphone() {
		new Runnable() {
			boolean found = false;
			public void run() {
				System.out.println("Looking for "+preferredMicrophone+"...");
				while(!Thread.currentThread().isInterrupted()) {
					for (Mixer.Info mixer : AudioSystem.getMixerInfo()) {
						//System.out.println("Attached mixer: " + mixer.toString());
						if (mixer.toString().toLowerCase()
								.contains(preferredMicrophone.toLowerCase())) {
							found = true;
							System.out.println("Found: " + preferredMicrophone + "!");
							System.out.println("Creating connection to "
									+ preferredMicrophone + "...");
							
							// Doing register stuff now...
							
							
							
							
							
							
							
							
							Thread.currentThread().interrupt();
							break;
						} 
					}
					if(!found) {
						try {
							Thread.sleep(2000);
							System.out.println(preferredMicrophone + " not found! Please find it... Don't worry, we'll wait.");
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} // end thread while loop
			} // end run method
		}.run();
	}
}