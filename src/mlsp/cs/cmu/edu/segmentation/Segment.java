package mlsp.cs.cmu.edu.segmentation;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

import mlsp.cs.cmu.edu.audio.AudioConstants;

/* Iterator behavior is for feature extraction. Yields frames of 25 ms each*/
public class Segment implements Iterator<short[]>, Cloneable {
  
  private AudioFormat audioFormat;

  private ArrayList<Short> waveform;

  private ArrayList<Double> decibelWaveform;

  private ArrayList<double[]> waveframes;
  
  private int startFrame = 0;
  
  private int endFrame = 0;
  
  private int frameSize = 0;
  
  private ArrayList<Short> flattenedWavFrames;
  
  private ArrayList<short[]> iterableWaveform;
  
  private boolean startedIteration = false;
  
  public Segment(AudioFormat audioFormat, ArrayList<Short> waveform,
          ArrayList<Double> decibelWaveform, ArrayList<double[]> waveframes) {
    this.audioFormat = audioFormat;
    this.waveform = waveform;
    this.decibelWaveform = decibelWaveform;
    this.waveframes = waveframes;
    this.startFrame = 0;
    this.endFrame = waveframes.size()-1; // defaults to size of array
    this.frameSize = waveframes.get(0).length;
    this.iterableWaveform = new ArrayList<short[]>();
  }
  
  // make a clone of this thing!
  public Object clone() {
    try {
      return super.clone();
    } catch (CloneNotSupportedException e) {
      System.out.println("Could not clone object!");
      e.printStackTrace();
    }
    return null;
  }
  
  private ArrayList<short[]> buildIterableWaveform() {
    ArrayList<Short> wav = getProcessedWaveform();
    ArrayList<short[]> queue = new ArrayList<short[]>();
    int samplesPerMillisecond = (int) audioFormat.getSampleRate() / 1000;
    int windowSize = AudioConstants.FRAMESIZE.getValue() * samplesPerMillisecond;
    int frameShift = AudioConstants.FRAMESHIFT.getValue() * samplesPerMillisecond;
    for(int i = 0; i < (wav.size()-windowSize-1); i += frameShift) {
      List<Short> frame = new ArrayList<Short>();
      frame = wav.subList(i, (i+windowSize));
      short[] sFrame = new short[windowSize];
      for(int j = 0; j < frame.size(); j++) {
        sFrame[j] = frame.get(j);
      }
      queue.add(sFrame);
    }
    return queue;
  }

  public void setEndpoints(int start, int end) {
    this.startFrame = start;
    this.endFrame = end;
  }
  
  public AudioInputStream getAudioStream() {
    ArrayList<Short> wav = getProcessedWaveform();
    ByteArrayInputStream buff = new ByteArrayInputStream(convertShortsToBytes(wav));
    return new AudioInputStream(buff, audioFormat, (wav.size()*2));
  }

  public double getFrameEnergy(int frameIndex) {
    return decibelWaveform.get(frameIndex);
  }
  
  public ArrayList<Short> getWaveform() {
    return getWaveformSegmentSubset(waveform);
  }
  
  public ArrayList<Short> getProcessedWaveform() {
    return getWaveformSegmentSubset(getFlattenedWavFrames());
  }
  
  private ArrayList<Short> getFlattenedWavFrames() {
    flattenedWavFrames = new ArrayList<Short>();
    for(double[] frame : waveframes) {
      for(Double sample : frame) {
        flattenedWavFrames.add(sample.shortValue());
      }
    }
    return flattenedWavFrames;
  }
  
  private byte[] convertShortsToBytes(ArrayList<Short> arr) {
    byte[] byteArray = new byte[arr.size() * 2];
    int i = 0;
    for(short s : arr) {
      ByteBuffer bb = ByteBuffer.allocate(2);
      bb.order(java.nio.ByteOrder.nativeOrder());
      bb.putShort(s);
      byteArray[i++] = bb.get(0);
      byteArray[i++] = bb.get(1);
    }
    return byteArray;
  }
  
  private int getStartSample() {
    return Math.max(((startFrame-1) * frameSize), 0);
  }
  
  private int getEndSample() {
    return Math.min(((endFrame-1) * frameSize), waveform.size()-1);
  }
  
  private ArrayList<Short> getWaveformSegmentSubset(ArrayList<Short> arr) {
    int startIndex = getStartSample();
    int endIndex = getEndSample();
    List<Short> returnList = arr.subList(startIndex, endIndex);
    return new ArrayList<Short>(returnList);
  }

  public double getStartFrameTimestamp() {
    return (double) getStartSample() / audioFormat.getSampleRate();
  }

  public double getEndFrameTimestamp() {
    return (double) getEndSample() / audioFormat.getSampleRate();
  }

  @Override
  public boolean hasNext() {
    if(!startedIteration) {
      iterableWaveform = buildIterableWaveform();
      startedIteration = true;
    }
    return !iterableWaveform.isEmpty();
  }

  @Override
  public short[] next() {
    if(!startedIteration) {
      iterableWaveform = buildIterableWaveform();
      startedIteration = true;
    }
    return iterableWaveform.remove(0);
  }

}
