
/**
 * This class is called a simple segmenter because it uses
 * simple threshold based endpointing, a simple 16bit PCM
 * audio format, and only uses a pre-emphasis filter...
 * @author nwolfe
 *
 */
public class SimpleSegmenter extends Segmenter {
  
  public SimpleSegmenter(FrameSequence fs) {
    super(fs, new AudioFormatMono16BitPCM16kHz(), new EnergyBasedEndpointing());
    attachFilter(new PreEmphasisFilter());
  }

  @Override
  protected void classifyAndSegmentFrame(Double energy, boolean isSpeech) {
    // TODO Auto-generated method stub
    
  }

}
