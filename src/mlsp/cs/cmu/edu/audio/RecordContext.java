package mlsp.cs.cmu.edu.audio;

import mlsp.cs.cmu.edu.sampling.Recorder;
import mlsp.cs.cmu.edu.sampling.Sampler;
import mlsp.cs.cmu.edu.segmentation.Segmenter;

public class RecordContext {

  private static Recorder recorder = null;

  private static Sampler sampler = null;

  private static Segmenter segmenter = null;

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
    if (recorder != null)
      recorder.start(); // extends Thread
    if (sampler != null)
      sampler.start(); // extends Thread
    if (segmenter != null)
      segmenter.start(); // extends Thread
  }

  public static void stopAll() {
    if (recorder != null)
      recorder.stopRecording();
    if (sampler != null)
      sampler.stopSampling();
    if (segmenter != null)
      segmenter.stopSegmenting();
  }

}
