import java.nio.ByteOrder;

import javax.sound.sampled.AudioFormat;


public class AudioFormatFactory {
  
  public static AudioFormat getMono16BitPCMAudioFormat(Float sampleRate) {
    Integer sampleSizeInBits = 16;
    Integer channels = AudioConstants.MONO.getValue();
    Boolean signed = true;
    Boolean bigEndian = (java.nio.ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN);
    AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed,
            bigEndian);
    return format;
  }


}
