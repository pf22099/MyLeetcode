package airbnb;

import java.util.*;

public class EightPuzzle {
}

class Resolver {
  private int steps;
  private List<int[]> path;
  private boolean isSolvable;

  public Resolver(int[][] board) {
    Board initial = new Board(board, 0);
    isSolvable = initial.isSolvable();
    if(!isSolvable) {
      return;
    }
    Map<Board, Board> parent= new HashMap<>();
    // Core part of A*: F = G(steps) + H(Manhattan)
    PriorityQueue<Board> pq = new PriorityQueue<>(Comparator.comparingInt(i -> (i.getManhattan() + i.getStep())));
    pq.add(initial);
    while(!pq.isEmpty()) {
      Board curr = pq.poll();
      System.out.println(curr.toString());
      System.out.println(curr.getManhattan());
      System.out.println();
      if(curr.isGoal()) {
        steps = curr.getStep();
        path = generatePath(parent, initial, curr);
        return;
      }
      List<Board> boards = curr.getNeighbors(curr.getStep() + 1);
      for(Board next : boards) {
        if(!parent.containsKey(curr) || !parent.get(curr).equals(next)) {
          pq.add(next);
          parent.put(next, curr);
        }
      }
    }
  }

  public boolean isSolvable() {
    return isSolvable;
  }

  public int getSteps() {
    return steps;
  }

  public List<int[]> getPath() {
    return path;
  }

  private List<int[]> generatePath(Map<Board, Board> parent, Board initial, Board target) {
    List<int[]> path = new ArrayList<>();
    while(!initial.equals(target)) {
      path.add(0, target.getBlank());
      target = parent.get(target);
    }
    path.add(0, target.getBlank());
    return path;
  }
}

class Board {
  private int[][] board;
  private int size;
  private int step;
  private int inversionCount;
  private int blankX;
  private int blankY;
  private int manhattanCache;

  public Board(int[][] board, int step) {
    if(board == null || board.length == 0 || board.length != board[0].length) {
      throw new RuntimeException("Invalid board!");
    }
    this.board = new int[board.length][board[0].length];
    this.size = board.length * board[0].length;
    this.step = step;
    for(int i = 0; i < board.length; i++) {
      for(int j = 0; j < board[0].length; j++) {
        this.board[i][j] = board[i][j];
        if(this.board[i][j] == 0) {
          this.blankX = i;
          this.blankY = j;
          continue;
        }
        manhattanCache += getDistance(i, j, this.board[i][j]-1, this.board);
      }
    }
    inversionCount = getInversionCount(this.board);
  }

  public int getSize() {
    return this.size;
  }

  public int getStep() {
    return step;
  }

  public int[] getBlank() {
    return new int[] {blankX, blankY};
  }

  public int getManhattan() {
    return manhattanCache;
  }

  public boolean isSolvable() {
    if(size % 2 == 1) {
      return inversionCount % 2 == 0;
    }
    int blankXFromBottom = board.length - 1 - blankX;
    return (blankXFromBottom % 2 == 0 && inversionCount % 2 == 1)
        || (blankXFromBottom % 2 == 1 && inversionCount % 2 == 0);
  }

  public boolean isGoal() {
    for(int i = 0; i < board.length; i++) {
      for(int j = 0; j < board[0].length; j++) {
        if(board[i][j] == 0) continue;
        if(board[i][j] != (i * board[0].length + j + 1)) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for(int[] row : board) {
      sb.append(Arrays.toString(row)).append("\n");
    }
    return sb.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if(obj == this) {
      return true;
    }
    if(!(obj instanceof Board)) {
      return false;
    }
    Board b2 = (Board) obj;
    for(int i = 0; i < board.length; i++) {
      for(int j = 0; j < board[0].length; j++) {
        if(board[i][j] != b2.board[i][j]) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public int hashCode() {
    StringBuilder sb = new StringBuilder();
    for(int i = 0; i < board.length; i++) {
      for(int j = 0; j < board[0].length; j++) {
        sb.append(board[i][j]);
      }
    }
    return sb.toString().hashCode();
  }

  public List<Board> getNeighbors(int step) {
    int[][] directions = new int[][] {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    List<Board> neighbors = new ArrayList<>();
    for(int[] dir : directions) {
      int x = dir[0] + blankX;
      int y = dir[1] + blankY;
      if(x < 0 || x >= board.length || y < 0 || y >= board[0].length) {
        continue;
      }
      // 易错点！不能生成新的board后再调用neighbor.swap(...), 因为生成的board是按照原来的board计算Manhattan的
      swap(blankX, blankY, x, y);
      Board neighbor = new Board(board, step);
      swap(blankX, blankY, x, y);
      neighbors.add(neighbor);
    }
    return neighbors;
  }

  private int getInversionCount(int[][] board) {
    int count = 0;
    for(int i = 1; i < size-1; i++) {
      for(int j = i+1; j < size; j++) {
        int x1 = (i-1) / board[0].length;
        int y1 = (i-1) % board[0].length;
        int x2 = (j-1) / board[0].length;
        int y2 = (j-1) % board[0].length;
        // 易错点！需要排除0
        if(board[x1][y1] != 0 && board[x2][y2] != 0 && board[x1][y1] > board[x2][y2]) {
          count++;
        }
      }
    }
    return count;
  }

  private int getDistance(int i, int j, int index, int[][] board) {
    int x = index / board[0].length;
    int y = index % board[0].length;
    return Math.abs(i-x) + Math.abs(j-y);
  }

  private void swap(int x1, int y1, int x2, int y2) {
    int tmp = board[x1][y1];
    board[x1][y1] = board[x2][y2];
    board[x2][y2] = tmp;
  }
}