import java.io.IOException;

import javax.sound.sampled.AudioInputStream;

public class Driver {
  /**
   * Entry to run the program
   * @throws InterruptedException 
   * @throws IOException 
   */
  public static void main(String[] args) throws InterruptedException, IOException {
	  
	  Microphone mic = new ATRUSBMicrophone(AudioConstants.KHZ16.getValue());
	  Recorder recorder = new Recorder(mic, AudioConstants.KHZ16BUFFER.getValue());
	  Sampler sampler = new Sampler(recorder);
	  recorder.start();
	  sampler.start();
	  
	  sleep(1);
	  
	  recorder.stopRecording();
	  sampler.stopSampling();
    
  }
  
  public static void sleep(int seconds) {
    long i;
    seconds = seconds * 3;
    while(seconds-- > 0) {
      i = 0; while(i < 1000000000) { i++; }
    }
  }
  
}
