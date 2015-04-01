package mlsp.cs.cmu.edu.segmentation;

import mlsp.cs.cmu.edu.audio.AudioFormatMono16BitPCM16kHz;
import mlsp.cs.cmu.edu.audio.RecordContext;
import mlsp.cs.cmu.edu.features.AudioFeatureExtractor;
import mlsp.cs.cmu.edu.features.FeatureExtractor;
import mlsp.cs.cmu.edu.filters.PreEmphasis;
import mlsp.cs.cmu.edu.filters.RemoveDCOffset;
import mlsp.cs.cmu.edu.sampling.FrameSequence;

/**
 * This class is called a simple segmenter because it uses simple threshold based endpointing, a
 * simple 16bit PCM audio format, and only uses a pre-emphasis filter...
 * 
 * @author nwolfe
 *
 */
public class SimpleSegmenter extends Segmenter {

  private boolean SOUND_STARTED = false;

  private int SILENCE_STRING_CUTOFF = 3000; // assumes 10ms frame

  private int SPEECH_SEGMENT_CUTOFF = 25;

  private int frameBackoff = 10;

  private int speechStart = 0;

  private int speechEnd = 0;

  private int speechDuration = 0;

  private int silenceCount = 0;

  public SimpleSegmenter(FrameSequence fs) {
    super(fs, new AudioFormatMono16BitPCM16kHz(), new EnergyBasedEndpointing());
    //attachFilter(new RemoveDCOffset());
    //attachFilter(new PreEmphasis());
  }

  private int getBackoff(int index) {
    int backoff = index - frameBackoff;
    if (backoff > 0) {
      return backoff;
    } else {
      return 0;
    }
  }

  @Override
  // 1 frame is 10ms, so we want
  protected void classifyAndSegmentFrame(double energy, boolean isSpeech) {
    if (energy > 0) {

      if (isSpeech) {

        if (!SOUND_STARTED) {
          System.out.println("\n[SEGMENTER] >>>>>>>>>>>>>>>> Sound started!\n");
          SOUND_STARTED = true;
          speechEnd = 0;
          speechDuration = 0;

        } else {
          silenceCount = 0; // reset

          if (speechDuration > 0) {
            speechDuration++; // count the duration
            speechEnd = 0; // reset
          }

          // condition for new segment...
          if (speechEnd == 0 && speechDuration == 0) {
            speechStart = getBackoff(getFrameIndex());
            speechDuration++;
            System.out.println("\n[SEGMENTER] >>>>>>>>>>>>>>>> Speech started at " + speechStart
                    + "\n");
          }
        }
      } else { // not speech

        if (SOUND_STARTED) {
          silenceCount++;

          // System.out.println("S I L E N C E... " + silenceCount);
          if (speechDuration > 0) { // in the middle of a segment
            speechEnd++;
          }

          if (speechEnd >= SPEECH_SEGMENT_CUTOFF) {
            int endMarker = getFrameIndex();
            System.out.println("\n[SEGMENTER] >>>>>>>>>>>>>>>> Speech ENDED at: " + endMarker
                    + " and started at " + speechStart + " with " + speechDuration
                    + " speech frames\n");
            registerSegment(speechStart, endMarker);
            speechEnd = 0; // reset
            speechDuration = 0;
          }
        }
      }
      if (silenceCount >= SILENCE_STRING_CUTOFF) {
        System.out.println("\n[SEGMENTER] >>>>>>>>>>>>>>>> Looks like we're done!\n");
        RecordContext.stopAll();
      }
    }
  }

  @Override
  protected void runFeatureExtraction() {
    FeatureExtractor extractor = new AudioFeatureExtractor(this);
    extractor.start();
  }

}
