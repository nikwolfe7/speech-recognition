package mlsp.cs.cmu.edu.features;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import mlsp.cs.cmu.edu.audio.AudioConstants;
import mlsp.cs.cmu.edu.filters.FrameFilter;
import mlsp.cs.cmu.edu.filters.MFCC;
import mlsp.cs.cmu.edu.segmentation.Segment;

public class MFCCFeatureVectorContainer {

  private ArrayList<double[]> logMelSpectrum = new ArrayList<double[]>();
  
  private ArrayList<double[]> MFCCs = new ArrayList<double[]>();

  private ArrayList<double[]> recoveredLogMelSpectrum = new ArrayList<double[]>();
  
  private FrameFilter mfccFilter = new MFCC();

  private String segmentName;

  private final String sep = System.getProperty("file.separator");

  private String MATLABDir = "." + sep + "matlab" + sep;
  
  private String MATLABExt = ".m";

  private String csvDir = "." + sep + "csv" + sep;
  
  private String csvExt = ".csv";

  public MFCCFeatureVectorContainer(Segment seg) {
    this.segmentName = seg.getSegmentName();
    File dir = new File(csvDir);
    if (!dir.exists()) {
      dir.mkdir();
    }
    dir = new File(MATLABDir);
    if (!dir.exists()) {
      dir.mkdir();
    }
  }

  // should only be called after all feature frames have been added
  public void expand() {
    for (int t = 0; t < MFCCs.size(); t++) {
      if (t > 2 && t < MFCCs.size()) {
        double[] frame = MFCCs.get(t - 2);
        double[] expandedFrame = new double[AudioConstants.MFCC_SIZE.getValue() * 3];
        System.arraycopy(frame, 0, expandedFrame, 0, AudioConstants.MFCC_SIZE.getValue());
        // deltas
        for (int n = AudioConstants.MFCC_SIZE.getValue(); n < expandedFrame.length
                - AudioConstants.MFCC_SIZE.getValue(); n++) {
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
    this.MFCCs.add(mfccFilter.doFilter(frame));
  }

  public void addRecoveredLogMelSpectrumFeatureFrame(double[] frame) {
    this.recoveredLogMelSpectrum.add(frame);
  }

  private void printMatrix(ArrayList<double[]> matrix, String filename) {
    File csvFile = new File(csvDir + filename + csvExt);
    File MATLABFile = new File(MATLABDir + filename + MATLABExt);
    try {
      FileWriter writer = new FileWriter(csvFile);
      for (int i = matrix.get(0).length - 1; i >= 0; i--) {
        StringBuilder row = new StringBuilder();
        for (int j = 0; j < matrix.size(); j++) {
          row.append(matrix.get(j)[i] + "\t");
        }
        writer.write(row.toString().trim() + "\n");
      }
      writer.close();
      writer = new FileWriter(MATLABFile);
      writer.write(
              "figure(1)\n" +
              "A = dlmread('" + csvDir + filename + csvExt + "');\n" +
              "colormap('Jet');\n" +
              "imagesc(A);\n" +
              "title('"+ filename.replace('_', ' ') +"');"
              );
      writer.close();
      
    } catch (IOException e) {
      System.out.println("Woops! couldn't print that shit.");
      e.printStackTrace();
    }
  }

  public void printMatlabScripts() {
    printMatrix(MFCCs, segmentName + "_MFCCs");
    printMatrix(logMelSpectrum, segmentName + "_LogMels");
    printMatrix(recoveredLogMelSpectrum, segmentName + "_IDCT");
  }

}
