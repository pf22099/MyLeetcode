package airbnb;

public class PourWater {

  public void pourWater(int[] heights, int water, int pos) {
    if(heights == null || heights.length == 0) {
      return;
    }
    int[] waterPos = new int[heights.length];
    while(water > 0) {
      int pourLocation = pos;
      int left = pos - 1;
      while(left >= 0 && heights[left] + waterPos[left] <= heights[left+1] + waterPos[left+1]) {
        left--;
      }
      if(heights[left+1] + waterPos[left+1] < heights[pos] + waterPos[pos]) {
        waterPos[left+1]++;
        water--;
        continue;
      }
      int right = pos + 1;
      while(right < heights.length && heights[right] + waterPos[right] <= heights[right-1] + waterPos[right-1]) {
        right++;
      }
      if(heights[right-1] + waterPos[right-1] < heights[pos] + heights[pos]) {
        waterPos[right-1]++;
        water--;
        continue;
      }
      waterPos[pourLocation]++;
      water--;
    }
    printWater(heights, waterPos);
  }

  private void printWater(int[] heights, int[] waterPos) {
    int peek = 0;
    for(int i = 0; i < heights.length; i++) {
      peek = Math.max(peek, heights[i] + waterPos[i]);
    }
    for(int i = peek; i >= 0; i--) {
      for(int j = 0; j < heights.length; j++) {
        if(i > heights[j] + waterPos[j]) {
          System.out.print(" ");
        } else if(heights[j] < i && i <= heights[j] + waterPos[j]) {
          System.out.print("W");
        } else {
          System.out.print("+");
        }
      }
      System.out.println();
    }
  }
}
