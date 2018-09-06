package airbnb;

import java.util.*;

public class MenuCombinationSum {

  public List<List<Double>> getCombos(double[] prices, double target) {
    List<List<Double>> res = new ArrayList<>();
    if(prices == null || prices.length == 0) {
      return res;
    }
    search(prices, 0, target, new ArrayList<>(), res);
    return res;
  }

  private void search(double[] prices, int index, double remaining, List<Double> path, List<List<Double>> res) {
    // 记得加绝对值！
    if(Math.abs(remaining - 0.0) < 0.00001) {
      res.add(new ArrayList<>(path));
      return;
    }
    if(remaining < 0.0) {
      return;
    }
    for(int i = index; i < prices.length; i++) {
      path.add(prices[i]);
      search(prices, i, remaining - prices[i], path, res);
      path.remove(path.size()-1);
    }
  }
}
