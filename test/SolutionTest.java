import org.junit.Test;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class SolutionTest {
  private Solution solution = new Solution();
  private BlockingQueue<String> queue = new BlockingQueue<>(10);

  @Test
  public void testHappyPath() throws Exception {
    Solution solution = new Solution();
    Map<Integer, List<Integer>> edges = new HashMap<>();
    edges.put(0, Arrays.asList(1, 2));
    edges.put(1, Arrays.asList(2));
    edges.put(2, Arrays.asList(3, 0));
    edges.put(3, Arrays.asList(0, 3));
    Graph graph = new Graph(null, edges);
    List<int[]> res = solution.getCircles(graph);
    System.out.println(res);
  }

}
