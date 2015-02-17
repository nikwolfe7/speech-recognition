import java.nio.ByteOrder;

import javax.sound.sampled.AudioFormat;


public class AudioFormatMono16BitPCM16kHz implements AudioFormatFactory {

  @Override
  public AudioFormat getAudioFormat() {
    return getMono16BitPCMAudio16KHZFormat(AudioConstants.KHZ16.getValue().floatValue());
  }
  
  private AudioFormat getMono16BitPCMAudio16KHZFormat(Float sampleRate) {
    Integer sampleSizeInBits = 16;
    Integer channels = AudioConstants.MONO.getValue();
    Boolean signed = true;
    Boolean bigEndian = (java.nio.ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN);
    AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed,
            bigEndian);
    return format;
  }

}
