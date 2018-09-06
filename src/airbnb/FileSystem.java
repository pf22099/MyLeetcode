package airbnb;

import java.util.*;

public class FileSystem {
  private Map<String, Integer> map;
  private Map<String, Runnable> callbackMap;

  public FileSystem() {
    map = new HashMap<>();
    callbackMap = new HashMap<>();
  }

  public boolean create(String path, int value) {
    int parentIndex = path.lastIndexOf("/");
    String parent = path.substring(0, parentIndex);
    if(parentIndex > 0 && !map.containsKey(parent)) {
      return false;
    }
    map.put(path, value);
    while(!parent.isEmpty()) {
      if(callbackMap.containsKey(parent)) {
        callbackMap.get(parent).run();
      }
      parent = parent.substring(0, parent.lastIndexOf("/")); // !!!易错点
    }
    return true;
  }

  public boolean set(String path, int value) {
    if(!map.containsKey(path)) {
      return false;
    }
    map.put(path, value);
    while(!path.isEmpty()) {
      if(callbackMap.containsKey(path)) {
        callbackMap.get(path).run();
      }
      path = path.substring(0, path.lastIndexOf("/")); // !!!易错点
    }
    return true;
  }

  public int get(String path) {
    if(!map.containsKey(path)) {
      throw new RuntimeException("Invalid path!");
    }
    int res = map.get(path); // !!!提前取出value, 因为path会被后面的callback操作覆盖
    while(!path.isEmpty()) {
      if(callbackMap.containsKey(path)) {
        callbackMap.get(path).run();
      }
      path = path.substring(0, path.lastIndexOf("/")); // !!!易错点
    }
    return res;
  }

  public boolean watch(String path, Runnable callback) {
    if(!map.containsKey(path)) {
      return false;
    }
    callbackMap.put(path, callback);
    return true;
  }
}