import java.util.*;

public class Airbnb {

  /**
   * Given an array of numbers A = [x1, x2, ..., xn] and T = Round(x1+x2+... +xn). We want to find a
   * way to round each element in A such that after rounding we get a new array B = [y1, y2,
   * ....,yn] such that y1+y2+...+yn = T where yi = Floor(xi) or Ceil(xi), ceiling or floor of
   * xi..We also want to minimize sum |x_i-y_i|
   *
   * @param prices
   * @return
   */
  public long[] roundPrices(double[] prices) {
    int n = prices.length;
    long[] res = new long[n];
    // Math.ceil(1.0) == 1.0, so cannot compare the diff between prices[i] and ceil(prices[i]) in the priority queue.
    PriorityQueue<Integer> pq = new PriorityQueue<>((i, j) -> Double.compare(prices[j] - Math.floor(prices[j]), prices[i] -
            Math.floor(prices[i])));
    long floorSum = 0;
    double sum = 0.0;

    for(int i = 0; i < prices.length; i++) {
      long floorPrice = (long)Math.floor(prices[i]);
      res[i] = floorPrice;
      floorSum += floorPrice;
      sum += prices[i];
      pq.add(i);
    }

    long roundSum = Math.round(sum);
    for(int i = 0; i < (int)(roundSum - floorSum); i++) {
      int index = pq.poll();
      res[index] = (long)Math.ceil(prices[index]);
    }
    return res;
  }


  /**
   * flatten 2D list
   */
  class Vector2D implements Iterator<Integer> {
    private Iterator<List<Integer>> listIterator;
    private Iterator<Integer> numIterator;

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
        List<Integer> list = listIterator.next();
        numIterator = (list == null? Collections.emptyIterator() : list.iterator());
      }
      return numIterator.hasNext();
    }

    @Override
    public void remove() {
      numIterator.remove();
    }
  }

  /**
   * O((n+k)*(n/k)) = O(n^2)
   * Display Pages with as many unique hosts
   * @param input "host_id,listing_id,score,city"
   * @param pageSize
   * @return
   */
  public List<String> displayPages1(String[] input, int pageSize) {
    List<String> linkedResults = new LinkedList<>(Arrays.asList(input));
    List<String> res = new ArrayList<>();
    Set<String> visited = new HashSet<>();
    Iterator<String> iterator = linkedResults.iterator();
    while(!linkedResults.isEmpty()) {
      while(iterator.hasNext() && visited.size() < pageSize) {
        String result = iterator.next();
        String host = result.split(",")[0];
        if(!visited.contains(host)) {
          res.add(result);
          visited.add(host);
          iterator.remove();
        }
      }
      int count = pageSize - visited.size();
      iterator = linkedResults.iterator();
      while(!linkedResults.isEmpty() && count > 0) {
        if(!iterator.hasNext()) {
          iterator = linkedResults.iterator();
          continue;
        }
        res.add(iterator.next());
        iterator.remove();
        count--;
      }
      res.add("\n");
      iterator = linkedResults.iterator();
      visited.clear();
    }
    return res;
  }

  /**
   * LC 125
   * O(n)
   * @param s
   * @return
   */
  public boolean isPalindromeStr(String s) {
    if(s == null) {
      return false;
    }
    int l = 0;
    int r = s.length() - 1;
    while(l < r) {
      char lChar = s.charAt(l);
      if(!Character.isLetterOrDigit(lChar)) {
        l++;
        continue;
      }
      char rChar = s.charAt(r);
      if(!Character.isLetterOrDigit(rChar)) {
        r--;
        continue;
      }
      if(Character.toLowerCase(lChar) != Character.toLowerCase(rChar)) {
        return false;
      }
      l++;
      r--;
    }
    return true;
  }

  /**
   * LC 787
   * O(m + n)
   * @param n
   * @param flights
   * @param src
   * @param dst
   * @param K
   * @return
   */
  public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
    Map<Integer, Map<Integer, Integer>> flightMap = new HashMap<>();
    Queue<int[]> queue = new LinkedList<>();
    Map<Integer, Integer> visited = new HashMap<>();
    createGraph(n, flights, flightMap);

    queue.add(new int[] {src, 0});
    visited.put(src, 0);
    int stops = -1;
    int minPrice = Integer.MAX_VALUE;
    while(!queue.isEmpty() && stops <= K) {
      int size = queue.size();
      for(int i = 0; i < size; i++) {
        int[] curr = queue.poll();
        int city = curr[0];
        int price = curr[1];
        if(city == dst) {
          minPrice = Math.min(minPrice, price);
        }
        for(Map.Entry<Integer, Integer> nextCity : flightMap.get(city).entrySet()) {
          int next = nextCity.getKey();
          int nextPrice = nextCity.getValue();
          if(!visited.containsKey(next)
                  || (visited.containsKey(next)) && visited.get(next) > price + nextPrice) {
            queue.add(new int[] {next, price + nextPrice});
            visited.put(next, price + nextPrice);
            // System.out.println(city + " -> " + next + " : " + (price + nextPrice));
          }
        }
      }
      stops++;
    }
    return minPrice == Integer.MAX_VALUE? -1 : minPrice;
  }

  private void createGraph(int n, int[][] flights, Map<Integer, Map<Integer, Integer>> flightMap) {
    for(int i = 0; i < n; i++) {
      flightMap.put(i, new HashMap<>());
    }
    for(int[] flight : flights) {
      flightMap.get(flight[0]).put(flight[1], flight[2]);
    }
  }

  /**
   * 10 wizards
   * O(m + n)
   * @param wizards
   * @param source
   * @param target
   * @return
   */
  public List<Integer> getShortestPath(List<List<Integer>> wizards, int source, int target) {
    if (wizards == null || wizards.size() == 0) {
      return null;
    }
    List<Integer> res = new ArrayList<>();
    Map<Integer, List<Integer>> map = new HashMap<>();
    int[] parent = new int[10];
    Queue<int[]> queue = new LinkedList<>();
    Map<Integer, Integer> visited = new HashMap<>();

    initialize(wizards, map, parent);
    queue.add(new int[] {source, 0});
    visited.put(source, 0);
    while(!queue.isEmpty()) {
      int[] curr = queue.poll();
      int wizard = curr[0];
      int weightSum = curr[1];
      if(wizard == target) {
        continue;
      }
      for(int next : map.get(wizard)) {
        int weight = (int)Math.pow((wizard - next), 2);
        int sum = weightSum + weight;
        int past = visited.getOrDefault(next, Integer.MAX_VALUE);
        if(sum < past) {
          visited.put(next, sum);
          queue.add(new int[] {next, sum});
          parent[next] = wizard;
        }
      }
    }
    while(target != -1) {
      res.add(0, target);
      target = parent[target];
    }
    return res;
  }

  private void initialize(List<List<Integer>> wizards, Map<Integer, List<Integer>> map, int[] parent) {
    Arrays.fill(parent, -1);
    for(int i = 0; i < 10; i++) {
      map.put(i, new ArrayList<>());
    }
    for(int i = 0; i < wizards.size(); i++) {
      map.get(i).addAll(wizards.get(i));
    }
  }

  /**
   * LC 280
   * O(n)
   * @param nums
   */
  public void wiggleSort1(int[] nums) {
    if(nums == null || nums.length == 0) {
      return;
    }
    for(int i = 1; i < nums.length; i++) {
      if(i % 2 == 0) {
        if(nums[i] > nums[i-1]) {
          swap(nums, i, i-1);
        }
      } else {
        if(nums[i] < nums[i-1]) {
          swap(nums, i, i-1);
        }
      }
    }
  }

  private void swap(int[] nums, int i, int j) {
    int t = nums[i];
    nums[i] = nums[j];
    nums[j] = t;
  }

  /**
   * LC 324
   * O(nlogn)
   * @param nums
   */
  public void wiggleSort2(int[] nums) {
    if(nums == null || nums.length < 2) {
      return;
    }
    int[] copy = Arrays.copyOf(nums, nums.length);
    Arrays.sort(copy);
    int mid = (nums.length + 1) / 2;
    int start = 0;
    int end = nums.length % 2 == 0? nums.length-1 : nums.length-2;
    for(int i = mid-1; i >= 0; i--) {
      nums[start] = copy[i];
      start += 2;
    }
    for(int i = mid; i < nums.length; i++) {
      nums[end] = copy[i];
      end -= 2;
    }
  }
}


