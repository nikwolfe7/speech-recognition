import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.LinkedBlockingQueue;

import javax.sound.sampled.AudioInputStream;

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
    this.recorder = sampleable;
    this.waveform = new LinkedBlockingQueue<Short>();
  }

  public void run() {
    while (!Thread.currentThread().isInterrupted()) {
      try {
        byte[] twoBytes = new byte[2];
        AudioInputStream audioInputStream = recorder.sample();
        while (audioInputStream.read(twoBytes) > 0) {
          Short frame = getShort(twoBytes);
          // System.out.println(frame);
          waveform.put(frame);
        }
      } catch (IOException | InterruptedException e) {
        System.out.println("Sampling interrupted! Terminating...");
        Thread.currentThread().interrupt();
      }
    }
  }

  public Short getFrame() throws InterruptedException {
    return waveform.take();
  }

  public void stopSampling() {
    this.interrupt();
  }

  private Short getShort(byte[] twoBytes) {
    ByteBuffer byteBuff = ByteBuffer.wrap(twoBytes);
    byteBuff.order(java.nio.ByteOrder.nativeOrder());
    return byteBuff.getShort();
  }

}
