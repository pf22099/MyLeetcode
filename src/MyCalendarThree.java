public class MyCalendarThree {
  private SegmentTree root;

  public MyCalendarThree() {
    root = new SegmentTree(0, 1000000000);
  }

  public int book(int start, int end) {
    add(root, start, end, 1);
    return getBooked(root);
  }

  private void add(SegmentTree root, int start, int end, int val) {
//    if(root == null || start >= root.end || end <= root.start) {
//      return;
//    }
    if(start == root.start && end == root.end) {
      root.booked += val;
      root.saved += val;
      return;
    }
    int mid = root.start + (root.end - root.start) / 2;
    if(overlap(root.start, mid, start, end)) {
      if(root.left == null) {
        root.left = new SegmentTree(root.start, mid);
      }
      add(root.left, start, end, val);
    }
    if(overlap(mid, root.end, start, end)) {
      if(root.right == null) {
        root.right = new SegmentTree(mid, root.end);
      }
      add(root.right, start, end, val);
    }
    root.saved = root.booked + Math.max(getBooked(root.left), getBooked(root.right));
  }

  private int getBooked(SegmentTree root) {
    if(root == null) {
      return 0;
    }
    return root.saved;
  }

  private boolean overlap(int start1, int end1, int start2, int end2) {
    if(start2 >= end1 || end2 <= start1) {
      return false;
    }
    return true;
  }
}

class SegmentTree {
  int start;
  int end;
  int booked;
  int saved;
  SegmentTree left;
  SegmentTree right;

  public SegmentTree(int s, int e) {
    start = s;
    end = e;
  }
}