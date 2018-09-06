import java.util.*;

class SnakeGame {
  int m;
  int n;
  Queue<int[]> snake;
  Set<Integer> snakeSet;
  Queue<int[]> food;
  int score;
  int length;
  int[] current;

  /** Initialize your data structure here.
   @param width - screen width
   @param height - screen height
   @param food - A list of food positions
   E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */
  public SnakeGame(int width, int height, int[][] food) {
    Arrays.sort(food, (a,b) -> b[0] - a[0]);
    int[] coins = new int[10];
    m = height;
    n = width;
    snake = new LinkedList<>();
    snakeSet = new HashSet<>();
    this.food = new LinkedList<>(Arrays.asList(food));
    snake.add(new int[] {0, 0});
    snakeSet.add(0);
    score = 0;
    length = 1;
    current = new int[] {0, 0};
  }

  /** Moves the snake.
   @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down
   @return The game's score after the move. Return -1 if game over.
   Game over when snake crosses the screen boundary or bites its body. */
  public int move(String direction) {
    int[] next = getNext(current, direction);
    int nextIndex = getIndex(next[0], next[1]);

    if(snake.size() >= length) {
      snakeSet.remove(getIndex(snake.peek()[0], snake.peek()[1]));
      snake.poll();
    }
    if(next[0] < 0 || next[0] >= m || next[1] < 0 || next[1] >= n || snakeSet.contains(nextIndex)) {
      return -1;
    }
    snake.add(next);
    snakeSet.add(nextIndex);
    current = next;
    if(isEatingFood(next, food)) {
      food.poll();
      score++;
      length++;
    }
    return score;
  }

  private boolean isEatingFood(int[] pos, Queue<int[]> food) {
    if(!food.isEmpty() && pos[0] == food.peek()[0] && pos[1] == food.peek()[1]) {
      return true;
    }
    return false;
  }

  private int[] getNext(int[] current, String direction) {
    switch(direction) {
      case "U":
        return new int[] {current[0]-1, current[1]};
      case "L":
        return new int[] {current[0], current[1]-1};
      case "R":
        return new int[] {current[0], current[1]+1};
      case "D":
        return new int[] {current[0]+1, current[1]};
      default:
        return null;
    }
  }

  private int getIndex(int x, int y) {
    return x * n + y;
  }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */