import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.TargetDataLine;

public abstract class Microphone {
  
  protected AudioFormat audioFormat;
  
  public Microphone(AudioFormatFactory audioFormatFactory) {
    this.audioFormat = audioFormatFactory.getAudioFormat();
  }
  
  public abstract TargetDataLine getOpenMicrophone();

}
