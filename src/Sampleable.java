import java.io.IOException;

import javax.sound.sampled.AudioInputStream;

public interface Sampleable {
  
  public AudioInputStream sample() throws IOException;

}
