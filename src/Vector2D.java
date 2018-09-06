import java.util.*;

public class Vector2D implements Iterator<Integer> {
  Iterator<List<Integer>> listIterator;
  Iterator<Integer> numIterator;

  public Vector2D(List<List<Integer>> vec2d) {
    listIterator = vec2d.iterator();
    numIterator = Collections.emptyIterator();
  }

  @Override
  public Integer next() {
    return numIterator.next();
  }

  @Override
  public boolean hasNext() {
    while(!numIterator.hasNext()) {
      if(!listIterator.hasNext()) {
        return false;
      }
      char c = 'a';
      char cc = 'A';
      int i = 'a' - 'A';
      numIterator = listIterator.next().iterator();
    }
    return true;
  }

  @Override
  public void remove() {
    numIterator.remove();
  }
}