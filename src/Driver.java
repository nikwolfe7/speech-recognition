import java.io.IOException;

import mlsp.cs.cmu.edu.audio.AudioConstants;
import mlsp.cs.cmu.edu.audio.AudioFormatFactory;
import mlsp.cs.cmu.edu.audio.AudioFormatMono16BitPCM16kHz;
import mlsp.cs.cmu.edu.audio.RecordContext;
import mlsp.cs.cmu.edu.microphone.ATRUSBMicrophone;
import mlsp.cs.cmu.edu.microphone.Microphone;
import mlsp.cs.cmu.edu.sampling.Recorder;
import mlsp.cs.cmu.edu.sampling.Sampler;
import mlsp.cs.cmu.edu.segmentation.Segmenter;
import mlsp.cs.cmu.edu.segmentation.SimpleSegmenter;

public class Driver {
  /**
   * Entry to run the program
   * @throws InterruptedException 
   * @throws IOException 
   */
  public static void main(String[] args) throws InterruptedException, IOException {
    
    /*Audio Format Factory vends proper audio format*/
    AudioFormatFactory audioFormatFactory = new AudioFormatMono16BitPCM16kHz();
    
    /*Microphone implementation takes an AudioFormatFactory */
    Microphone microphone = new ATRUSBMicrophone(audioFormatFactory);
    
    /*Recording module takes a microphone and a buffersize to read from the sound card*/
	  Recorder recorder = new Recorder(microphone, AudioConstants.KHZ16BUFFER.getValue());
	  
	  /*Sampler takes a Sampleable object, such as a recording device or stored WAV file*/
    /* For testing only */
    //Sampleable storedWavFile = new ReadDummyWave();
	  //Sampler sampler = new Sampler(storedWavFile);
	  Sampler sampler = new Sampler(recorder);
	  
	  /*Segmenter finds speech segments using a sampler*/
	  Segmenter segmenter = new SimpleSegmenter(sampler);
	  
	  // Push-to-talk
	  System.out.print("Press Enter to start recording...");
	  System.in.read();
	  
    RecordContext.startAll();
	  
	  sleep(10);
	 
	  RecordContext.stopAll();
    
  }
  
  
  
  public static void sleep(int seconds) {
    long i;
    seconds = seconds * 3;
    while(seconds-- > 0) {
      i = 0; while(i < 1000000000) { i++; }
    }
  }
  

  
}
