import java.util.*;

class Solution {

  public List<int[]> getCircles(Graph graph) {
    Map<Integer, List<Integer>> edges = graph.edges;
    Set<Edge> visited = new HashSet<>();
    List<int[]> res = new ArrayList<>();
    search(edges, edges.keySet().iterator().next(), visited, res);
    return res;
  }

  private void search (Map<Integer, List<Integer>> edges, int node, Set<Edge> visited, List<int[]> res) {
    for(int next : edges.get(node)) {
      Edge edge = new Edge(node, next);
      Edge opposite = new Edge(next, node);
      if(!visited.contains(edge)) {
        if(node != next && visited.contains(opposite)) {
          res.add(new int[] {node, next});
        }
        visited.add(edge);
        search(edges, next, visited, res);
      }
    }
  }

  class Edge {
    int start;
    int end;

    public Edge(int start, int end) {
      this.start = start;
      this.end = end;
    }

    @Override
    public int hashCode() {
      return Integer.hashCode(start * 2 + end);
    }

    @Override
    public boolean equals(Object o) {
      if(o == this) {
        return true;
      }
      if(!(o instanceof Edge)) {
        return false;
      }
      Edge e = (Edge) o;
      return (e.start == this.start && e.end == this.end);
    }

  }
}
