package mlsp.cs.cmu.edu.wavutils;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import mlsp.cs.cmu.edu.audio.AudioStrings;


public class WAVWriter {
  
  private final String sep = System.getProperty("file.separator");
  
  private AudioFileFormat.Type wavType = AudioFileFormat.Type.WAVE;
  
  private final String wavExt = ".wav";
  
  private String wavDir = "." + sep + "wav" + sep;
  
  public WAVWriter() {
    File dir = new File(wavDir);
    if(!dir.exists()) {
      dir.mkdir();
    }
  }
  
  private void writeWav(AudioInputStream stream, File file) {
    try {
      AudioSystem.write(stream, wavType, file);
    } catch (IOException e) {
      System.out.println("Failed to create file!");
      //e.printStackTrace();
    }
  }
  
  public void writeWavSegment(AudioInputStream audioInputStream, String segmentName) {
    File wavFile = new File(wavDir + segmentName + wavExt);
    writeWav(audioInputStream, wavFile);
    
  }
  
  public void writeWholeWav(AudioInputStream audioInputStream) {
    File wavFile = new File(wavDir + AudioStrings.COMPLETE_RECORDING.getValue() + wavExt);
    writeWav(audioInputStream, wavFile);
  }

}
