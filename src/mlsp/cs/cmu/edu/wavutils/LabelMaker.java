package mlsp.cs.cmu.edu.wavutils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import mlsp.cs.cmu.edu.segmentation.Segment;

public class LabelMaker {

  private String sep = System.getProperty("file.separator");

  private String wavDir = "." + sep + "wav" + sep;

  public void writeLabelFile(Iterable<Segment> segments) {
    File file = new File(wavDir + "labels.txt");
    try {
      int count = 1;
      FileWriter fw = new FileWriter(file);
      for (Segment seg : segments) {
        String line = seg.getStartFrameTimestamp() + "\t" + seg.getEndFrameTimestamp()
                + "\tsegment-" + count++ + "\n";
        fw.write(line);
      }
      fw.close();
    } catch (IOException e) {
      System.out.println("Failed to write label file!");
      e.printStackTrace();
    }
  }

}
