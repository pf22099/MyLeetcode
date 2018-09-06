import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class AirbnbTests {

  Airbnb airbnb = new Airbnb();

  @Test
  public void testRoundPrices() throws Exception {
    long[] res = airbnb.roundPrices(new double[] {1.2, 2.5, 3.6, 4.0});
    System.out.println(Arrays.toString(res));
  }

  @Test
  public void test2DIterator() {
    List<List<Integer>> input = new ArrayList<>();
    input.add(new ArrayList<>(Arrays.asList(1, 2)));
    input.add(new ArrayList<>(Arrays.asList(3)));
    Vector2D iterator = new Vector2D(input);
    if (iterator.hasNext()) iterator.next();
    if (iterator.hasNext()) iterator.next();
    iterator.remove();
    System.out.println(input);
  }

  @Test
  public void testDisplayPages() throws Exception {
    String[] input =
        new String[] {
          "1,28,310.6,SF",
          "4,5,204.1,SF",
          "20,7,203.2,Oakland",
          "6,8,202.2,SF",
          "6,10,199.1,SF",
          "1,16," + "190.4,SF",
          "6,29,185.2,SF",
          "7,20,180.1,SF",
          "6,21,162.1,SF",
          "2,18,161.2,SF",
          "2,30,149.1,SF",
          "3,76,146.2,SF",
          "2,14,141.1,San Jose"
        };
    List<String> res = airbnb.displayPages1(input, 5);
    System.out.println(res);
  }

  @Test
  public void testGetShortestPath() throws Exception {
    List<List<Integer>> input = new ArrayList<>();
    input.add(Arrays.asList(1, 2));
    input.add(Arrays.asList(3));
    input.add(Arrays.asList(3,4));
    input.add(Arrays.asList(4));
    List<Integer> res = airbnb.getShortestPath(input, 0, 4);
    System.out.println(res);
  }
}
