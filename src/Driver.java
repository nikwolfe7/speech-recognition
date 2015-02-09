import javax.sound.sampled.Mixer;

public class Driver {

  public static void main(String[] args) {
    System.out.println("Hello, world! " + AudioConstants.KHZ16.getValue());
    Record record = new Record();
    record.start();
      
  }

}
