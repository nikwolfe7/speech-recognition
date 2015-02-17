import java.io.IOException;

import javax.sound.sampled.AudioInputStream;


public class Sampler extends Thread {
  
  private Sampleable recorder;
  private WAVWriter wavWriter;
  
  public Sampler(Sampleable sampleable) {
    this.recorder = sampleable;
    this.wavWriter = new WAVWriter();
  }
  
  public void run() {
    while(!Thread.currentThread().isInterrupted()) {
      try {
        AudioInputStream audioInputStream = recorder.sample();
        
        System.out.println("Frame length: "+audioInputStream.getFrameLength());
        wavWriter.writeWavSegment(audioInputStream);
      
      } catch (IOException e) {
        System.out.println("Failed to get audio!");
      }
    }
  }
  
  public void stopSampling(){
    this.interrupt();
  }
  
}
