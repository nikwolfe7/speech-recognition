package mlsp.cs.cmu.edu.sampling;
/*Gets a frame of 16bit data*/
public interface FrameSequence {

  public short getFrame() throws InterruptedException;
  
}
