import java.io.IOException;

public class DriverWavInput {
  /**
   * Entry to run the program
   * @throws InterruptedException 
   * @throws IOException 
   */
  public static void main(String[] args) throws InterruptedException, IOException {
    
    /*Audio Format Factory vends proper audio format*/
    AudioFormatFactory audioFormatFactory = new AudioFormatMono16BitPCM16kHz();
    
    /*Microphone implementation takes an AudioFormatFactory */
//    Microphone microphone = new ATRUSBMicrophone(audioFormatFactory);
    
    /*Recording module takes a microphone and a buffersize to read from the sound card*/
//	  Recorder recorder = new Recorder(microphone, AudioConstants.KHZ16BUFFER.getValue());
	  
	  /*Sampler takes a Sampleable object, such as a recording device or stored WAV file*/
    /* For testing only */
    Sampleable storedWavFile = new ReadDummyWave("./testwav/omgwav.wav");
	  Sampler sampler = new Sampler(storedWavFile);
//	  Sampler sampler = new Sampler(recorder);
	  
	  /*Strategy for deciding if a frame is speech or not...*/
	  SegmentStrategy strategy = new EnergyBasedEndpointing();
	  
	  /*Segmenter finds speech segments using a sampler*/
	  Segmenter segmenter = new Segmenter(sampler, audioFormatFactory, strategy);
	  
	  // Push-to-talk
	  //System.out.print("Press Enter to start recording...");
	  //System.in.read();
	  
//    recorder.start(); // extends Thread
	  sampler.start(); // extends Thread
	  segmenter.start(); // extends Thread
	  
//	  sleep(10);
	 
//	  recorder.stopRecording();
//	  sampler.stopSampling();
//	  segmenter.stopSegmenting();
    
  }
  
  public static void sleep(int seconds) {
    long i;
    seconds = seconds * 3;
    while(seconds-- > 0) {
      i = 0; while(i < 1000000000) { i++; }
    }
  }
  
}
