package airbnb;

import java.io.*;
import java.util.*;

public class CSVParser {

  public String parseCSVFile(String fileName) {
    try {
      BufferedReader reader = new BufferedReader(new FileReader(fileName));
      StringBuilder sb = new StringBuilder();
      String line = reader.readLine();
      while(line != null) {
        sb.append(parseCSV(line));
        sb.append("|\n");
        line = reader.readLine();
      }
      return sb.toString();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }


  public String parseCSV(String str) {
    if(str == null || str.isEmpty()) {
      return "";
    }
    List<String> res = new ArrayList<>();
    StringBuilder sb = new StringBuilder();
    boolean isInQuote = false;

    for(int i = 0; i < str.length(); i++) {
      char c = str.charAt(i);
      if(isInQuote) {
        if(c == '\"') {
          if(i < str.length()-1 && str.charAt(i+1) == '\"') {
            sb.append(c);
            i++;
          } else {
            isInQuote = false;
          }
        } else {
          sb.append(c);
        }
      } else {
        if(c == '\"') {
          isInQuote = true;
        } else if(c == ',') {
          res.add(sb.toString());
          sb.setLength(0);
        } else {
          sb.append(c);
        }
      }
    }
    if(sb.length() != 0) {
      res.add(sb.toString());
    }
    return String.join("|", res);
  }
}
