import java.io.*;
import java.nio.ByteOrder;

import javax.sound.sampled.*;

public class Record {

  public static final long RECORD_TIME = 5000;

  File wavFile = new File("testaudio.wav");

  private AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;

  private TargetDataLine line;

  public AudioFormat getAudioFormat() {
    Float sampleRate = new Float(AudioConstants.KHZ16.getValue());
    Integer sampleSizeInBits = 16;
    Integer channels = AudioConstants.MONO.getValue();
    Boolean signed = true;
    Boolean bigEndian = (java.nio.ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN);
    AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
    return format;
  }

  public void start() {
    AudioFormat format = getAudioFormat();
    DataLine.Info info  = new DataLine.Info(TargetDataLine.class, format);
  }

  // WAV to output

}
