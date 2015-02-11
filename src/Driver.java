import javax.sound.sampled.TargetDataLine;


public class Driver {
  /**
   * Entry to run the program
   */
  public static void main(String[] args) {
	  
	  Microphone mic = new ATRUSBMicrophone();
	  Recorder recorder = new Recorder(mic);
	  recorder.start();
	  
	  
	  /*
	  final Record recorder = new Record();

      // creates a new thread that waits for a specified
      // of time before stopping
      Thread stopper = new Thread(new Runnable() {
          public void run() {
              try {
                  Thread.sleep(Record.RECORD_TIME);
              } catch (InterruptedException ex) {
                  ex.printStackTrace();
              }
              recorder.finish();
          }
      });

      stopper.start();

      // start recording
      recorder.start();
  	*/
  }
}
