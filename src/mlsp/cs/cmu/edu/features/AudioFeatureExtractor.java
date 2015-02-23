package mlsp.cs.cmu.edu.features;

import mlsp.cs.cmu.edu.filters.DiscreteFourierTransform;
import mlsp.cs.cmu.edu.filters.HammingWindowFilter;
import mlsp.cs.cmu.edu.filters.MelFilterBank;
import mlsp.cs.cmu.edu.filters.PreEmphasisFilter;
import mlsp.cs.cmu.edu.segmentation.Segment;
import mlsp.cs.cmu.edu.segmentation.Segmenter;

public class AudioFeatureExtractor extends FeatureExtractor {

  public AudioFeatureExtractor(Segmenter segmenter) {
    super();
    if (segmenter != null) {
      for(Segment seg : segmenter.getSegments()) {
        registerSegment(seg);
      }
    }
    attachFilter(new PreEmphasisFilter());
    attachFilter(new HammingWindowFilter());
    attachFilter(new DiscreteFourierTransform());
    attachFilter(new MelFilterBank());
  }

}
