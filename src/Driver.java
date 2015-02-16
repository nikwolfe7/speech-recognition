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
	  WAVWriter wavWriter = new WAVWriter();
	  recorder.start();
	  
	  sleep(5);
	  
	  AudioInputStream ais = recorder.sample();
	  System.out.println("Frame length: " + ais.getFrameLength());
	  wavWriter.writeWholeWav(ais);
    
    recorder.stopRecording();
  }
  
  public static void sleep(int seconds) {
    long i;
    seconds = seconds * 3;
    while(seconds-- > 0) {
      i = 0; while(i < 1000000000) { i++; }
    }
  }
  
}
