package airbnb;

import java.util.*;

public class CollatzConjecture {

  public int findMaxSteps(int num) {
    if(num < 1) {
      return 0;
    }
    int maxSteps = 0;
    Map<Integer, Integer> map = new HashMap<>();
    for(int i = 1; i <= num; i++) {
      int steps = getSteps(i, map);
      maxSteps = Math.max(maxSteps, steps);
      map.put(i, steps);
    }
    return maxSteps;
  }

  private int getSteps(int num, Map<Integer, Integer> map) {
    if(num == 1) {
      return 1;
    }
    if(map.containsKey(num)) {
      return map.get(num);
    }
    if(num % 2 == 0) {
      return 1 + getSteps(num / 2, map);
    }
    return 1 + getSteps(num * 3 + 1, map);
  }
}
