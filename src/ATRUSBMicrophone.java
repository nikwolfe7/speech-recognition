import java.nio.ByteOrder;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;

public class ATRUSBMicrophone implements Microphone {

  private String preferredMicrophone = AudioStrings.ATR_USB_MICROPHONE.getValue();

  private TargetDataLine tdl;

  /**
   * Holy Shit. The amount of code it takes in Java just to open a connection to a goddamn
   * microphone. Fuck me.
   */
  public ATRUSBMicrophone() {

    new Runnable() {
      boolean found = false;

      public void run() {
        System.out.println("Looking for " + preferredMicrophone + "...");

        Integer count = 0;
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
              AudioFormat audioFormat = getAudioFormat(AudioConstants.KHZ16.getValue().floatValue());

              // Get the Dataline...
              DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);

              try {
                // Get the Target dataline...
                tdl = (TargetDataLine) mixer.getLine(dataLineInfo);
                tdl.open(audioFormat);
                tdl.start();

                // Couldn't get our preferred microphone
              } catch (Exception e) {

                // try 441, if not, give up.
                System.out
                        .println("Failed to open microphone with 16khz sample rate... Trying 441khz...");
                audioFormat = getAudioFormat(AudioConstants.KHZ441.getValue().floatValue());
                dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);

                try {
                  tdl = (TargetDataLine) mixer.getLine(dataLineInfo);
                  tdl.open(audioFormat);
                  tdl.start();

                } catch (Exception e1) {

                  System.out.println("Could not connect to " + preferredMicrophone + "!");
                  System.out.println("Try plugging the microphone in before running the program.");
                  System.exit(0);
                }
              }

              // get out of here!
              Thread.currentThread().interrupt();
              break;
            }
          }
          if (!found) {
            count += 1;
            if (count % 5000 == 0)
              System.out.println(preferredMicrophone
                      + " not found! Please find it... Don't worry, we'll wait.");
          }
        } // end thread while loop
      } // end run method

      private AudioFormat getAudioFormat(Float sampleRate) {
        Integer sampleSizeInBits = 16;
        Integer channels = AudioConstants.MONO.getValue();
        Boolean signed = true;
        Boolean bigEndian = (java.nio.ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN);
        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed,
                bigEndian);
        return format;
      }

    }.run();
  }

  @Override
  public TargetDataLine getOpenMicrophone() {
    return this.tdl;
  }
}
