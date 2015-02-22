package mlsp.cs.cmu.edu.segmentation;

import mlsp.cs.cmu.edu.audio.AudioFormatMono16BitPCM16kHz;
import mlsp.cs.cmu.edu.audio.RecordContext;
import mlsp.cs.cmu.edu.filters.PreEmphasisFilter;
import mlsp.cs.cmu.edu.filters.RemoveDCOffsetFilter;
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

  private Integer SILENCE_STRING_CUTOFF = 250; // assumes 10ms frame
  
  private Integer SPEECH_SEGMENT_CUTOFF = 35;

  private Double BACKGROUND_ENERGY = 0.0;

  private Integer frameBackoff = 25;

  private Integer frameCount = 0;

  private Integer speechStart = 0;

  private Integer speechEnd = 0;

  private Integer speechDuration = 0;

  private Double totalEnergy = 0.0;

  private Integer silenceCount = 0;

  public SimpleSegmenter(FrameSequence fs) {
    super(fs, new AudioFormatMono16BitPCM16kHz(), new EnergyBasedEndpointing());
    attachFilter(new RemoveDCOffsetFilter());
    attachFilter(new PreEmphasisFilter());
  }

  private Integer getBackoff(Integer index) {
    return index - frameBackoff;
  }
  
  private Integer getLookahead(Integer index) {
    return index + frameBackoff;
  }

  @Override
  // 1 frame is 10ms, so we want
  protected void classifyAndSegmentFrame(Double energy, boolean isSpeech) {
    frameCount++;
    if (energy > 0) {
      totalEnergy += energy;
      BACKGROUND_ENERGY = frameCount / totalEnergy;
      //System.out.println("Energy: " + energy + " Average Energy: " + averageEnergy);
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
            speechEnd = 0;
          }
          
          // condition for new segment...
          if (speechEnd == 0 && speechDuration == 0) {
            speechStart = getBackoff(getFrameIndex());
            speechDuration++;
            System.out.println("\n[SEGMENTER] >>>>>>>>>>>>>>>> Speech started at " + speechStart + "\n");
          }
        }
      } else { // not speech
        
        if (SOUND_STARTED) {
          silenceCount++;
          
          //System.out.println("S I L E N C E... " + silenceCount);
          if (speechDuration > 0) { // in the middle of a segment
            speechEnd++;
          }
          
          if(speechEnd >= SPEECH_SEGMENT_CUTOFF) {
            Integer endMarker = getFrameIndex();
            System.out.println("\n[SEGMENTER] >>>>>>>>>>>>>>>> Speech ENDED at: " + endMarker + " and started at " + speechStart + " with duration: " + speechDuration + "\n");
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

}
