import java.util.ArrayList;
import java.util.List;

class MyCalendarTwo {
  List<int[]> intervals;
  List<int[]> overlaps;

  public MyCalendarTwo() {
    intervals = new ArrayList<>();
    overlaps = new ArrayList<>();
    int a = Integer.MAX_VALUE;
    int b = (int) 1e7;
    boolean c = (a > b+b);
  }

  public boolean book(int start, int end) {
    for(int[] interval : intervals) {
      // Overlap
      int overlapStart = Math.max(start, interval[0]);
      int overlapEnd = Math.min(end, interval[1]);
      if(overlapStart > overlapEnd) {
        for(int[] overlap : overlaps) {
          if(Math.max(overlapStart, overlap[0]) > Math.min(overlapEnd, overlap[1])) {
            overlaps.clear();
            return false;
          }
        }
        overlaps.add(new int[] {overlapStart, overlapEnd});
      }
    }
    intervals.add(new int[] {start, end});
    return true;
  }
}

/**
 * Your MyCalendarTwo object will be instantiated and called as such:
 * MyCalendarTwo obj = new MyCalendarTwo();
 * boolean param_1 = obj.book(start,end);
 */