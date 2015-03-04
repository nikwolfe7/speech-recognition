import java.io.IOException;

import mlsp.cs.cmu.edu.audio.RecordContext;
import mlsp.cs.cmu.edu.sampling.ReadDummyWave;
import mlsp.cs.cmu.edu.sampling.Sampleable;
import mlsp.cs.cmu.edu.sampling.Sampler;
import mlsp.cs.cmu.edu.segmentation.Segmenter;
import mlsp.cs.cmu.edu.segmentation.SimpleSegmenter;

public class DriverWavInput {
  /**
   * Entry to run the program
   * @throws InterruptedException 
   * @throws IOException 
   */
  public static void main(String[] args) throws InterruptedException, IOException {
    
    /*Sampler takes a Sampleable object, such as a recording device or stored WAV file*/
    /* For testing only */
    Sampleable storedWavFile = new ReadDummyWave("./testwav/polly-spot-1-malinke.wav");
    Sampler sampler = new Sampler(storedWavFile);
    
    /*Segmenter finds speech segments using a sampler*/
    Segmenter segmenter = new SimpleSegmenter(sampler);
    
    // Push-to-talk
    System.out.print("Press Enter to start recording...");
    System.in.read();
    
    RecordContext.startAll();
    
  }
  
  public static void sleep(int seconds) {
    long i;
    seconds = seconds * 3;
    while(seconds-- > 0) {
      i = 0; while(i < 1000000000) { i++; }
    }
  }
  
}
