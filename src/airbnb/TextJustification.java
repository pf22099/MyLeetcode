package airbnb;

import java.util.*;

/**
 * LC 68
 * 注意
 * 1）每行最后一个单词容易忘记加上，然后最后一个单词后面没有空格
 * 2）最后一行也容易忘记加上去
 * 3）每行只能放入一个单词的special case需要注意
 */
public class TextJustification {
  public List<String> fullJustify(String[] words, int maxWidth) {
    List<String> res = new ArrayList<>();
    if(words == null || words.length == 0) {
      return res;
    }
    int i = 0;
    List<String> line = new ArrayList<>();
    int len = 0;
    while(i < words.length) {
      if(len + words[i].length() <= maxWidth) {
        line.add(words[i]);
        len += (words[i].length() + 1);
        i++;
      } else {
        len--;
        int spaceNum = maxWidth - len;
        int spaceForEachWord = line.size() == 1? spaceNum : (spaceNum / (line.size()-1));
        int remaining = line.size() == 1? 0 : (spaceNum % (line.size()-1));
        StringBuilder sb = new StringBuilder();
        for(int j = 0; j < line.size()-1; j++) {
          sb.append(line.get(j)).append(" ");
          for(int k = 0; k < spaceForEachWord; k++) {
            sb.append(" ");
          }
          if(remaining > 0) {
            sb.append(" ");
            remaining--;
          }
        }
        sb.append(line.get(line.size()-1));
        while(sb.length() < maxWidth) {
          sb.append(" ");
        }
        res.add(sb.toString());
        line.clear();
        len = 0;
      }
    }
    if(!line.isEmpty()) {
      StringBuilder sb = new StringBuilder();
      for(String word : line) {
        sb.append(word).append(" ");
      }
      sb.setLength(sb.length()-1);
      while(sb.length() < maxWidth) {
        sb.append(" ");
      }
      res.add(sb.toString());
    }
    return res;
  }
}
