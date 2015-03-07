package mlsp.cs.cmu.edu.features;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import mlsp.cs.cmu.edu.audio.AudioConstants;
import mlsp.cs.cmu.edu.segmentation.Segment;

public class MFCCFeatureVectorContainer {

  private ArrayList<double[]> logMelSpectrum = new ArrayList<double[]>();

  private ArrayList<double[]> MFCCs = new ArrayList<double[]>();

  private ArrayList<double[]> recoveredLogMelSpectrum = new ArrayList<double[]>();

  private String segmentName;

  public MFCCFeatureVectorContainer(Segment seg) {
    this.segmentName = seg.getSegmentName();
  }

  // should only be called after all feature frames have been added
  public void expand() {
    for (int t = 0; t < MFCCs.size(); t++) {
      if (t > 2 && t < MFCCs.size()) {
        double[] frame = MFCCs.get(t - 2);
        double[] expandedFrame = new double[AudioConstants.MFCC_SIZE.getValue() * 3];
        System.arraycopy(frame, 0, expandedFrame, 0, AudioConstants.MFCC_SIZE.getValue());
        // deltas 
        for (int n = AudioConstants.MFCC_SIZE.getValue(); n < expandedFrame.length - AudioConstants.MFCC_SIZE.getValue(); n++) {
          double prev = MFCCs.get(t - 3)[n - AudioConstants.MFCC_SIZE.getValue()];
          double next = MFCCs.get(t - 1)[n - AudioConstants.MFCC_SIZE.getValue()];
          double delta = (next - prev) / 2;
          expandedFrame[n] = delta;
        }
        MFCCs.set(t - 2, expandedFrame);
        
      }
      double[] frame = MFCCs.get(t);
      double[] expandedFrame = new double[AudioConstants.MFCC_SIZE.getValue() * 3];
      System.arraycopy(frame, 0, expandedFrame, 0, AudioConstants.MFCC_SIZE.getValue());
      MFCCs.set(t, expandedFrame);
    }
  }

  public void accept(FeatureVisitor visitor) {
    visitor.visit(this);
  }

  public void addLogMelSpectrumFeatureFrame(double[] frame) {
    this.logMelSpectrum.add(frame);
  }

  public void addMFCCFeatureFrame(double[] frame) {
    this.MFCCs.add(frame);
  }

  public void addRecoveredLogMelSpectrumFeatureFrame(double[] frame) {
    this.recoveredLogMelSpectrum.add(frame);
  }
  
  private void printMatrix(ArrayList<double[]> matrix, String filename) {
    File file = new File(filename);
    try {
      FileWriter writer = new FileWriter(file);
      for(int i = matrix.get(0).length - 1; i >= 0; i--) {
        StringBuilder row = new StringBuilder();
        for(int j = 0; j < matrix.size(); j++) {
          row.append(matrix.get(j)[i] + "\t");
        }
        writer.write(row.toString().trim() + "\n");
      }
      writer.close();
    } catch (IOException e) {
      System.out.println("Woops! couldn't print shit.");
      e.printStackTrace();
    }
  }

  public void printMatlabScripts() {
    printMatrix(MFCCs, segmentName + "-MFCCs.csv");
  }

}
