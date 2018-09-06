package airbnb;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FindMedianInFile {

  public double findMedian(String fileName) {
    try {
      BufferedReader reader = new BufferedReader(new FileReader(fileName));
      int start = Integer.MIN_VALUE;
      int end = Integer.MAX_VALUE;
      int len = 0;
      while (readFile(reader) != null) {
        len++;
      }
      int k = (len + 1) / 2;
      if (len % 2 == 1) {
        return findKthSmallest(fileName, k, start, end);
      }
      return (findKthSmallest(fileName, k, start, end)
              + findKthSmallest(fileName, k + 1, start, end))
          / 2.0;
    } catch (IOException e) {
      throw new RuntimeException("Error occurred while reading file.", e);
    }
  }

  private int findKthSmallest(String fileName, int k, long start, long end) throws IOException {
    if(start >= end) {
      return (int)start;
    }
    long guess = start + (end - start) / 2; // start and end must be long, otherwise there will be Integer overflow.
    long next = start;
    int count = 0;
    BufferedReader reader = new BufferedReader(new FileReader(fileName));
    Integer num = readFile(reader);
    while (num != null) {
      if (num <= guess) {
        count++;
        next = Math.max(next, num);
      }
      num = readFile(reader);
    }
    if (count == k) {
      return (int)next;
    }
    if (count < k) {
      return findKthSmallest(fileName, k, Math.max(next+1, guess), end);
    } else {
      return findKthSmallest(fileName, k, start, next);
    }
  }

  private Integer readFile(BufferedReader reader) throws IOException {
    String str = reader.readLine();
    if (str == null) {
      reader.close();
      return null;
    }
    return Integer.valueOf(str);
  }
}
