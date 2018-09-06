import java.util.*;

public class Graph {
  List<Integer> nodes;
  Map<Integer, List<Integer>> edges;

  public Graph(List<Integer> nodes, Map<Integer, List<Integer>> edges) {
    this.nodes = nodes;
    this.edges = edges;
  }
}
