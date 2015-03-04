package mlsp.cs.cmu.edu.sampling;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.imageio.spi.RegisterableService;
import javax.sound.sampled.AudioInputStream;

import mlsp.cs.cmu.edu.audio.RecordContext;

/**
 * The purpose of this class is to collect the waveform buffer that the Recorder retrieves from the
 * hardware interface, and it provides an easy interface for implementing classes to get the data
 * frame by frame and either write to a wav or extract features...
 * 
 * @author nwolfe
 *
 */
public class Sampler extends Thread implements FrameSequence {

  private Sampleable recorder;

  private LinkedBlockingQueue<Short> waveform;

  public Sampler(Sampleable sampleable) {
    RecordContext.registerSampler(this);
    this.recorder = sampleable;
    this.waveform = new LinkedBlockingQueue<Short>();
  }

  public void run() {
    while (!Thread.currentThread().isInterrupted()) {
      try {
        byte[] twoBytes = new byte[2];
        AudioInputStream audioInputStream = recorder.sample();
        if (audioInputStream != null) {
          while (audioInputStream.read(twoBytes) > 0) {
            short frame = getshort(twoBytes);
            // System.out.println(frame);
            waveform.put(frame);
          }
        }
      } catch (IOException | InterruptedException e) {
        System.out.println("Sampling interrupted! Terminating...");
        Thread.currentThread().interrupt();
      }
    }
  }

  public short getFrame() throws InterruptedException {
    Short result = waveform.poll(1,TimeUnit.SECONDS); 
    if(result == null) {
      RecordContext.stopAll();
      return 0;
    } else {
      return result;
    }
  }

  public void stopSampling() {
    this.interrupt();
  }
  

  private short getshort(byte[] twoBytes) {
    ByteBuffer byteBuff = ByteBuffer.wrap(twoBytes);
    byteBuff.order(java.nio.ByteOrder.nativeOrder());
    return byteBuff.getShort();
  }

}
