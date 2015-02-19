package mslp.cs.cmu.edu.wavutils;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

import mlsp.cs.cmu.edu.audio.AudioConstants;
import mlsp.cs.cmu.edu.audio.AudioFormatMono16BitPCM16kHz;
import mlsp.cs.cmu.edu.sampling.Sampleable;

public class ReadDummyWave implements Sampleable {

  private final String sep = System.getProperty("file.separator");
  private String filename = "." + sep + "testwav" + sep + "omgwav.wav";
  private Integer bufferSize = AudioConstants.KHZ16BUFFER.getValue();
  private byte[] buffer = new byte[bufferSize];
  private BufferedInputStream stream = null;
  private Integer offset = 0;
  private AudioFormat format = new AudioFormatMono16BitPCM16kHz().getAudioFormat();
  private File wavFile = new File(filename);

  public ReadDummyWave(String file) {
    if(file != null) {
      this.wavFile = new File(file);
    }
    try {
      this.stream = new BufferedInputStream(new FileInputStream(wavFile));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      System.exit(0);
    }
  }

  @Override
  public AudioInputStream sample() throws IOException {
    AudioInputStream ais = null;
    if((stream.read(buffer, offset, bufferSize)) > 0) {
      InputStream instream = new ByteArrayInputStream(buffer);
      ais = new AudioInputStream(instream, format, buffer.length/format.getFrameSize());
    }
    return ais;
  }

}
