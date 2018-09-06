package airbnb;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AirbnbTests {

  @Test
  public void testCollatzConjecture() throws Exception {
    CollatzConjecture obj = new CollatzConjecture();

    int res = obj.findMaxSteps(7);
    System.out.println(res);
  }

  @Test
  public void testQueueWithArray() throws Exception {
    QueueWithArray queue = new QueueWithArray(5);
    queue.offer(1);
    System.out.println("offer: " + queue.size());
    System.out.println("poll: " + queue.poll());
    System.out.println("Size: " + queue.size());
    queue.offer(2);
    System.out.println("offer: " + queue.size());
    queue.offer(3);
    System.out.println("offer: " + queue.size());
    queue.offer(4);
    System.out.println("offer: " + queue.size());
    System.out.println("poll: " + queue.poll());
    System.out.println("Size: " + queue.size());
    System.out.println("poll: " + queue.poll());
    System.out.println("Size: " + queue.size());
    queue.offer(5);
    System.out.println("Size: " + queue.size());
    queue.offer(6);
    System.out.println("Size: " + queue.size());
    queue.offer(7);
    System.out.println("Size: " + queue.size());
    queue.offer(8);
    System.out.println("Size: " + queue.size());
    queue.offer(9);
    System.out.println("Size: " + queue.size());
    queue.offer(10);
    System.out.println("Size: " + queue.size());
    while(!queue.isEmpty()) {
      System.out.println("poll: " + queue.poll());
      System.out.println("Size: " + queue.size());
    }
  }

  @Test
  public void testIterator2D() throws Exception {
    List<List<Integer>> input = new ArrayList<>();
    input.add(new ArrayList<>(Arrays.asList(1,2)));
    input.add(new ArrayList<>(Arrays.asList(3)));
    input.add(new ArrayList<>(Arrays.asList(4,5,6)));
    Iterator2D iterator = new Iterator2D(input);
    while(iterator.hasNext()) {
      System.out.println("Iterating: " + iterator.next());
      iterator.remove();
    }
    while(iterator.hasNext()) {
      System.out.println("Iterating: " + iterator.next());
    }
  }

  @Test
  public void testTravelBuddy() throws Exception {
    TravelBuddy body = new TravelBuddy();
    Map<String, List<String>> map = new HashMap<>();
    map.put("Zhang", new ArrayList<>(Arrays.asList("a", "b", "c", "d")));
    map.put("Li", new ArrayList<>(Arrays.asList("a", "b", "e", "f")));
    map.put("Wang", new ArrayList<>(Arrays.asList("a", "g", "c", "d")));
    map.put("Zhao", new ArrayList<>(Arrays.asList("a", "t", "q", "z")));
    List<String> res = body.findTravelBuddies(map, "Zhang", Arrays.asList("Li", "Wang", "Zhao"));
    System.out.println(res);
  }

  @Test
  public void testFileSystem() throws Exception {
    FileSystem fileSystem = new FileSystem();
    fileSystem.create("/a", 1);
    fileSystem.watch("/a", new Runnable() {
      @Override
      public void run() {
        System.out.println("Watching /a");
        Thread.currentThread().interrupt();
      }
    });
    fileSystem.create("/a/b", 2);
    System.out.println("Created!");
    fileSystem.create("/a/b/c", 3);
    System.out.println("Created!");
    fileSystem.create("/a/c/d", 3);
    fileSystem.set("a/b", 4);
    System.out.println("set!");
    System.out.println(fileSystem.get("/a"));
    System.out.println(fileSystem.get("/a/b"));
    System.out.println(fileSystem.get("/a/b/c"));
    System.out.println(fileSystem.get("/a/d/c"));
  }

  @Test
  public void testFindMedianFromFile() throws Exception {
    double res = new FindMedianInFile().findMedian("/Users/ericliang/workspace/Leetcode/src/airbnb/nums.txt");
    System.out.println(res);
  }

  @Test
  public void testIpToCIDR() throws Exception {
    List<String> res = new IpToCIDR().smallestCIDRToCoverAllIp("1.1.1.0", 4);
    System.out.print(res);
  }

  @Test
  public void testCSVParser() throws Exception {
    CSVParser parser = new CSVParser();
    String res = parser.parseCSVFile("/Users/ericliang/workspace/Leetcode/src/airbnb/resource/csv.txt");
    System.out.println(res);
  }

  @Test
  public void testPourWater() throws Exception {
    PourWater obj = new PourWater();
    obj.pourWater(new int[] {5,4,2,1,2,3,2,1,0,1,2,4}, 8, 5);
  }

  @Test
  public void testEightPuzzle_Board() throws Exception {
    int[][] board = new int[][] {{3,0,1}, {4,2,5}};
    Board initial = new Board(board, 0);
    Assert.assertEquals(6, initial.getManhattan());
    Assert.assertTrue(initial.isSolvable());
  }

  @Test
  public void testEightPuzzleResolver() throws Exception {
    int[][] board = new int[][] {{0,1,3}, {4,2,5}, {7,8,6}};
    Resolver resolver = new Resolver(board);
    if(!resolver.isSolvable()) {
      System.out.println("Unsolvable!!");
    }
    System.out.println(resolver.getSteps() + " steps.");
    for (int[] step : resolver.getPath()) {
      System.out.println(Arrays.toString(step));
    }
    System.out.println(resolver.getPath().size());
  }

  @Test
  public void testGuessNumber() throws Exception {
    GuessNumber guessNumber = new GuessNumber();
    int[] res = guessNumber.guessNumber();
    System.out.println(Arrays.toString(res));
  }

  @Test
  public void testMinCostFlight() throws Exception {
    MinCostFlight minCostFlight = new MinCostFlight();
    int res = minCostFlight.minCost(Arrays.asList("a,b,1", "b,d,2", "a,d,100", "a,c,1", "c,d,3"), "a", "d", 0);
    System.out.println(res);
  }

  @Test
  public void testMenuCombinationSum() throws Exception {
    MenuCombinationSum sum = new MenuCombinationSum();
    List<List<Double>> res = sum.getCombos(new double[] {2.4, 2.5, 5.2}, 15.0);
    System.out.println(res);
  }
}
