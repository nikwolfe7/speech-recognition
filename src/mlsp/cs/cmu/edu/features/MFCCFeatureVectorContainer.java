package mlsp.cs.cmu.edu.features;

import java.util.ArrayList;

public class MFCCFeatureVectorContainer {
  
  ArrayList<double[]> features = new ArrayList<double[]>();
  ArrayList<double[]> melSpectrum = new ArrayList<double[]>();
  ArrayList<double[]> logMelSpectrum = new ArrayList<double[]>();
  ArrayList<double[]> cepstrum = new ArrayList<double[]>();
  
  public void addFeatureFrame(double[] frame){
    this.features.add(frame);
  }

  // should only be called after all feature frames have been added
  public void expand() {
    // TODO Auto-generated method stub
  }
  
  public void accept(FeatureVisitor visitor) {
    visitor.visit(this);
  }
  
  public void addMelSpectrumFeatureFrame(double[] frame) {
    this.melSpectrum.add(frame);
  }
  
  public void addLogMelSpectrumFeatureFrame(double[] frame) {
    this.logMelSpectrum.add(frame);
  }
  
  public void addCepstralFeatureFrame(double[] frame) {
    this.cepstrum.add(frame);
  }
  
  public void printMatlabScripts() {
    
  }
  

}
