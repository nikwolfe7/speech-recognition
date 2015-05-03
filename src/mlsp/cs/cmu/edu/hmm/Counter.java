package mlsp.cs.cmu.edu.hmm;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.MutablePair;

class Counter<K> extends HashMap<K, Integer> {
    private static final long serialVersionUID = 1L;
    private Double total = 0.0;

    public void add(K k) {
      total += 1;
      if (this.containsKey(k)) {
        put(k, get(k) + 1);
      } else {
        put(k, 1);
      }
    }

    public List<Map.Entry<K, Integer>> getSortedNgrams() {
      List<Map.Entry<K, Integer>> list = new ArrayList<Map.Entry<K, Integer>>(entrySet());
      Collections.sort(list, new Comparator<Map.Entry<K, Integer>>() {
        public int compare(Map.Entry<K, Integer> o1, Map.Entry<K, Integer> o2) {
          return o2.getValue().compareTo(o1.getValue());
        }
      });
      return list;
    }
    
    public List<MutablePair<K, Double>> getProbabilites() {
      List<Map.Entry<K, Integer>> sortedItems = getSortedNgrams();
      List<MutablePair<K, Double>> wordProbs = new ArrayList<MutablePair<K,Double>>();
      for(Map.Entry<K, Integer> item : sortedItems) {
        MutablePair<K,Double> pair = new MutablePair<K, Double>(item.getKey(), item.getValue()/total);
        wordProbs.add(pair);
      }
      return wordProbs;
    }
  }