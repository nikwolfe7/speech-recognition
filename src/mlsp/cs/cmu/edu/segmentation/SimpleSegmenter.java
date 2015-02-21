package mlsp.cs.cmu.edu.segmentation;
import mlsp.cs.cmu.edu.audio.AudioFormatMono16BitPCM16kHz;
import mlsp.cs.cmu.edu.filters.PreEmphasisFilter;
import mlsp.cs.cmu.edu.filters.RemoveDCOffsetFilter;
import mlsp.cs.cmu.edu.sampling.FrameSequence;


/**
 * This class is called a simple segmenter because it uses
 * simple threshold based endpointing, a simple 16bit PCM
 * audio format, and only uses a pre-emphasis filter...
 * @author nwolfe
 *
 */
public class SimpleSegmenter extends Segmenter {
  
  private boolean SOUND_STARTED = false;
  private Integer SILENCE_STRING_CUTOFF;
  private Double SILENCE_THRESHOLD;
  private Double BACKGROUND_ENERGY;
  private Integer frameCount = 0;
  
  public SimpleSegmenter(FrameSequence fs) {
    super(fs, new AudioFormatMono16BitPCM16kHz(), new EnergyBasedEndpointing());
    attachFilter(new RemoveDCOffsetFilter());
    attachFilter(new PreEmphasisFilter());
  }

  @Override
  protected void classifyAndSegmentFrame(Double energy, boolean isSpeech) {
    if(isSpeech) {
      //System.out.println("You're talking!!!");
    }
    // TODO Auto-generated method stub
    
  }

}
