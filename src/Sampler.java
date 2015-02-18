import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;

public class Sampler extends Thread {

  private Sampleable recorder;

  private ArrayList<Short> waveform;

  public Sampler(Sampleable sampleable) {
    this.recorder = sampleable;
    this.waveform = new ArrayList<Short>();
  }

  public void run() {
    while (!Thread.currentThread().isInterrupted()) {
      try {
        byte[] twoBytes = new byte[2];
        AudioInputStream audioInputStream = recorder.sample();
        synchronized (waveform) {
          while (audioInputStream.read(twoBytes) > 0) {
            Short frame = getShort(twoBytes);
            System.out.println(frame);
            waveform.add(frame);
          }
        }
      } catch (IOException e) {
        System.out.println("Failed to get audio!");
      }
    }
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
