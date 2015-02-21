package mlsp.cs.cmu.edu.audio;
import mlsp.cs.cmu.edu.sampling.Recorder;
import mlsp.cs.cmu.edu.sampling.Sampler;
import mlsp.cs.cmu.edu.segmentation.Segmenter;

public class RecordContext {
  
  private static Recorder recorder;
  private static Sampler sampler;
  private static Segmenter segmenter;
  
  public static void registerRecorder(Recorder rec) {
    recorder = rec;
  }
  
  public static void registerSampler(Sampler samp) {
    sampler = samp;
  }
  
  public static void registerSegmenter(Segmenter seg) {
    segmenter = seg;
  }
  
  public static void startAll() {
    recorder.start(); // extends Thread
    sampler.start(); // extends Thread
    segmenter.start(); // extends Thread
  }
  
  public static void stopAll() {
    recorder.stopRecording();
    sampler.stopSampling();
    segmenter.stopSegmenting();
  }

}
