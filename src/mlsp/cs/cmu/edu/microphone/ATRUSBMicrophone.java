package mlsp.cs.cmu.edu.microphone;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;

import mlsp.cs.cmu.edu.audio.AudioFormatFactory;
import mlsp.cs.cmu.edu.audio.AudioStrings;

public class ATRUSBMicrophone extends Microphone {

  private String preferredMicrophone = AudioStrings.ATR_USB_MICROPHONE.getValue();

  private TargetDataLine tdl;

  /**
   * Holy Shit. The amount of code it takes in Java just to open a connection to a goddamn
   * microphone. Fuck me.
   */
  public ATRUSBMicrophone(AudioFormatFactory audioFormatFactory) {
    super(audioFormatFactory);

    new Runnable() {
      boolean found = false;

      public void run() {
        System.out.println("Looking for " + preferredMicrophone + "...");

        int count = 0;
        while (!Thread.currentThread().isInterrupted()) {
          for (Mixer.Info mixerInfo : AudioSystem.getMixerInfo()) {
            if (mixerInfo.getDescription().toLowerCase()
                    .contains(preferredMicrophone.toLowerCase())) {
              found = true;

              System.out.println("Found: " + preferredMicrophone + "!");
              System.out.println("Creating connection to " + preferredMicrophone + "...");

              // Grab the Mixer...
              Mixer mixer = AudioSystem.getMixer(mixerInfo);
              System.out.println("Connected to " + mixer + "...");

              // Audio Format... if this doesn't work we try 441Khz...
              AudioFormat audioFormat = audioFormatFactory.getAudioFormat();

              // Get the Dataline...
              DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);

              try {
                // Get the Target dataline...
                tdl = (TargetDataLine) mixer.getLine(dataLineInfo);
                tdl.open(audioFormat);
                tdl.start();

                // Couldn't get our preferred microphone
              } catch (Exception e) {
                e.printStackTrace();

                // try 441, if not, give up.
                System.out.println("Failed to open microphone with "+audioFormat.getSampleRate()+" sample rate... Try a different sample rate!");
                System.out.println("Try unplugging/replugging in the microphone again before running the program.");
                System.exit(0);
              }
              // get out of here!
              Thread.currentThread().interrupt();
              break;
            }
          }
          if (!found) {
            count += 1;
            if (count % 100000 == 0)
              System.out.println(preferredMicrophone
                      + " not found! Please find it... Don't worry, we'll wait.");
          }
        } // end thread while loop
      } // end run method
    }.run();
  }

  @Override
  public TargetDataLine getOpenMicrophone() {
    return this.tdl;
  }
}
