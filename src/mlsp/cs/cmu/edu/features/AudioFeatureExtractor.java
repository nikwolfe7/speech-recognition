package mlsp.cs.cmu.edu.features;

import mlsp.cs.cmu.edu.filters.DiscreteFourierTransform;
import mlsp.cs.cmu.edu.filters.HammingWindow;
import mlsp.cs.cmu.edu.filters.InverseDiscreteFourierTransform;
import mlsp.cs.cmu.edu.filters.LogMelFilter;
import mlsp.cs.cmu.edu.filters.MelSpectrum;
import mlsp.cs.cmu.edu.filters.PreEmphasis;
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
    attachFilter(new PreEmphasis());
    attachFilter(new HammingWindow());
    attachFilter(new DiscreteFourierTransform());
    attachFilter(new MelSpectrum());
    attachFilter(new LogMelFilter());
    attachFilter(new InverseDiscreteFourierTransform());
  }

}
