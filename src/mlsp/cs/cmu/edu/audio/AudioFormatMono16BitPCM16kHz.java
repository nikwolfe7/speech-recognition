package mlsp.cs.cmu.edu.audio;
import java.nio.ByteOrder;

import javax.sound.sampled.AudioFormat;


public class AudioFormatMono16BitPCM16kHz implements AudioFormatFactory {

  @Override
  public AudioFormat getAudioFormat() {
    return getMono16BitPCMAudio16KHZFormat((float) AudioConstants.KHZ16.getValue());
  }
  
  private AudioFormat getMono16BitPCMAudio16KHZFormat(float sampleRate) {
    int sampleSizeInBits = 16;
    int channels = AudioConstants.MONO.getValue();
    Boolean signed = true;
    Boolean bigEndian = (java.nio.ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN);
    AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed,
            bigEndian);
    return format;
  }

}
