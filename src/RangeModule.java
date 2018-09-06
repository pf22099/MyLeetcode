import java.util.TreeMap;

public class RangeModule {
  TreeMap<Integer, Integer> map;

  public RangeModule() {
    map = new TreeMap<>();
  }

  public void addRange(int left, int right) {
    Integer start = map.floorKey(left);
    Integer end = map.floorKey(right);

    if(start == null && end == null) {
      map.put(left, right);
      return;
    }
    if(start != null) {
      map.put(start, Math.max(right, Math.max(map.get(start), map.get(end))));
    } else {
      map.put(left, Math.max(right, map.get(end)));
    }

    // Clean up
    map.subMap(left, false, right, true).clear();
  }

  public boolean queryRange(int left, int right) {
    Integer start = map.floorKey(left);
    if(start == null) {
      return false;
    }
    return map.get(start) >= right;
  }

  public void removeRange(int left, int right) {
    if(left >= right) {
      return;
    }
    Integer start = map.floorKey(left);
    Integer end = map.floorKey(right);

    if(end != null && map.get(end) > right) {
      map.put(right, map.get(end));
    }

    if(start != null && map.get(start) > left) {
      map.put(start, left);
    }
    map.subMap(left, true, right, false).clear();
  }
}

/**
 * Your RangeModule object will be instantiated and called as such:
 * RangeModule obj = new RangeModule();
 * obj.addRange(left,right);
 * boolean param_2 = obj.queryRange(left,right);
 * obj.removeRange(left,right);
 */