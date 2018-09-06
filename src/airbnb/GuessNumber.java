package airbnb;

import java.util.*;

// http://www.1point3acres.com/bbs/thread-290126-1-1.html

/**
 * Soket connection: https://www.careerbless.com/samplecodes/java/beginners/socket/SocketBasic1.php
 */
public class GuessNumber {
  GuessServer server = new GuessServer();
  
  public int[] guessNumber() {
    String base = "1111";
    char[] res = new char[4];
    int counter = 0;
    int baseGuessResult = server.guess(Integer.valueOf(base));
    counter++;
    if(baseGuessResult == 4) {
      return new int[] {Integer.valueOf(base), counter};
    }
    for(int i = 0; i < 4; i++) {
      char[] candidate = base.toCharArray();
      for(int j = 2; j < 6; j++) {
        candidate[i] = (char)(j + '0');
        int num = Integer.valueOf(new String(candidate));
        int guessResult = server.guess(num);
        counter++;
        if(guessResult == 4) {
          return new int[] {num, counter};
        }
        if(guessResult < baseGuessResult) {
          res[i] = '1';
          break;
        }
        if(guessResult > baseGuessResult) {
          res[i] = (char)(j + '0');
          break;
        }
      }
      if(res[i] == 0) {
        res[i] = '6';
      }
    }
    return new int[] {Integer.valueOf(new String(res)), counter};
  }
}

class GuessServer {

  private String answer = "6666";

  public int guess(int num) {
    String numStr = String.valueOf(num);
    int count = 0;
    for(int i = 0; i < numStr.length(); i++) {
      if(answer.charAt(i) == numStr.charAt(i)) {
        count++;
      }
    }
    return count;
  }
}
