package mlsp.cs.cmu.edu.features;

import java.util.ArrayList;

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
    // TODO Auto-generated method stub
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

  public void printMatlabScripts() {
    
  }

}
