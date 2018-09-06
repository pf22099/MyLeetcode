package airbnb;

import java.util.*;

public class MinCostFlight {

  public int minCost(List<String> lines, String source, String target, int k) {
    Map<String, Map<String, Integer>> flights = createFlights(lines);
    Queue<String> queue = new LinkedList<>();
    Map<String, Integer> visited = new HashMap<>();
    int minCost = Integer.MAX_VALUE;
    int stops = -1;
    queue.add(source);
    visited.put(source, 0);
    while(!queue.isEmpty()) {
      int size = queue.size();
      for(int i = 0; i < size; i++) {
        String curr = queue.poll();
        int cost = visited.get(curr);
        if (curr.equals(target)) {
          minCost = Math.min(minCost, visited.get(curr));
        }
        // 易错点！！如果在while处判断stops会导致queue里的cost会在pop前被更新！
        if(++stops > k) {
          break;
        }
        for(String next : flights.get(curr).keySet()) {
          int currToNextCost = flights.get(curr).get(next);
          if(!visited.containsKey(next) || (visited.containsKey(next) && visited.get(next) > cost +
                  currToNextCost)) {
            queue.add(next);
            visited.put(next, cost + currToNextCost);
          }
        }
      }
    }
    return (minCost == Integer.MAX_VALUE? -1 : minCost);
  }

  private Map<String, Map<String, Integer>> createFlights(List<String> lines) {
    Map<String, Map<String, Integer>> flights = new HashMap<>();
    for(String line : lines) {
      String[] item = line.split(",");
      String departure = item[0];
      String arrival = item[1];
      int cost = Integer.valueOf(item[2]);
      flights.putIfAbsent(departure, new HashMap<>());
      flights.putIfAbsent(arrival, new HashMap<>()); // 易错点！！ 一定要将所有点加入graph中！
      flights.get(departure).put(arrival, cost);
    }
    return flights;
  }
}
