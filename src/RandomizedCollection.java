import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RandomizedCollection {
  List<Integer> values;
  Map<Integer, List<Integer>> valueMap;
  Random rand;

  /** Initialize your data structure here. */
  public RandomizedCollection() {
    values = new ArrayList<>();
    valueMap = new HashMap<>();
    rand = new Random();
  }

  /** Inserts a value to the collection. Returns true if the collection did not already contain the specified element. */
  public boolean insert(int val) {
    List<Integer> indexes = valueMap.getOrDefault(val, new ArrayList<>());
    indexes.add(values.size());
    values.add(val);
    valueMap.put(val, indexes);
    return indexes.size() == 1;
  }

  /** Removes a value from the collection. Returns true if the collection contained the specified element. */
  public boolean remove(int val) {
    if(valueMap.containsKey(val)) {
      List<Integer> indexes = valueMap.get(val);
      int index = indexes.get(0);
      int lastValue = values.get(values.size()-1);
      List<Integer> lastValueIndexes = valueMap.get(lastValue);

      values.set(index, lastValue);
      values.remove(values.size()-1);
      indexes.remove(indexes.size()-1);
      lastValueIndexes.remove(lastValueIndexes.size()-1);
      lastValueIndexes.add(index);
      if(indexes.size() == 0) {
        valueMap.remove(val);
      }
      System.out.println(values);
      return true;
    }
    return false;
  }

  /** Get a random element from the collection. */
  public int getRandom() {
    int randIndex = rand.nextInt(values.size());
    //System.out.println(values.get(randIndex) + ": " + valueMap.get(values.get(randIndex)));
    return values.get(randIndex);
  }
}

/**
 * Your RandomizedCollection object will be instantiated and called as such:
 * RandomizedCollection obj = new RandomizedCollection();
 * boolean param_1 = obj.insert(val);
 * boolean param_2 = obj.remove(val);
 * int param_3 = obj.getRandom();
 */