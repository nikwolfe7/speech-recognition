package mlsp.cs.cmu.edu.microphone;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.TargetDataLine;

import mlsp.cs.cmu.edu.audio.AudioFormatFactory;

public abstract class Microphone {
  
  protected AudioFormat audioFormat;
  
  public Microphone(AudioFormatFactory audioFormatFactory) {
    this.audioFormat = audioFormatFactory.getAudioFormat();
  }
  
  public abstract TargetDataLine getOpenMicrophone();

}
