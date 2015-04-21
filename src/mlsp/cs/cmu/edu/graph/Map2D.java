package mlsp.cs.cmu.edu.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Map2D<X, Y, Z> {

  private Map<X, Map<Y, Z>> map;

  public Map2D() {
    this.map = new HashMap<X, Map<Y, Z>>();
  }

  public void put(X x, Y y, Z value) {
    if (map.containsKey(x)) {
      map.get(x).put(y, value);
    } else {
      map.put(x, new HashMap<Y, Z>());
      map.get(x).put(y, value);
    }
  }

  public Z get(X x, Y y) {
    if (map.containsKey(x)) {
      return map.get(x).get(y);
    } else {
      return null;
    }
  }
  
  public Set<X> xKeyset() {
    return map.keySet();
  }
  
  public Set<Y> yKeyset(X x) {
    return map.get(x).keySet();
  }
  
  public List<Z> values() {
    List<Z> set = new ArrayList<Z>();
    for(X x : xKeyset()) {
      for(Y y : yKeyset(x)) {
        set.add(get(x, y));
      }
    }
    return set;
  }
  
  public boolean containsKey(X x, Y y) {
    if(map.containsKey(x)) {
      return map.get(x).containsKey(y);
    } else {
      return false;
    }
  }
  
  public static void main(String[] args) {
    Map2D<Character, Character, String> map = new Map2D<Character, Character, String>();
    map.put('x', 'y', "a");
    map.put('a', 'b', "b");
    map.put('m', 'n', "c");
    System.out.println(map.get('x', 'y'));
    System.out.println(map.get('a', 'b'));
    System.out.println(map.get('m', 'n'));
    System.out.println(map.containsKey('x', 'y'));
    System.out.println(map.containsKey('a', 'b'));
    System.out.println(map.containsKey('x', 'b'));
    System.out.println(map.xKeyset());
    for(Character x : map.xKeyset()) {
      System.out.println(map.yKeyset(x));
    }
    System.out.println(map.values());
  }
  
}
