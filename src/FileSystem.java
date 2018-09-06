import java.util.*;

public class FileSystem {

  Map<String, Integer>  fileMap;
  Map<String, Runnable> callbackMap;

  public FileSystem() {
    fileMap = new HashMap<>();
    callbackMap = new HashMap<>();
  }

  public boolean create(String path, int val) {
    if(fileMap.containsKey(path)) {
      return false;
    }
    int index = path.lastIndexOf("/");
    if(index != 0 && !fileMap.containsKey(path.substring(0, index))) {
      return false;
    }
    fileMap.put(path, val);
    callback(path);
    return true;
  }

  public boolean set(String path, int val) {
    if(!fileMap.containsKey(path)) {
      return false;
    }
    fileMap.put(path, val);
    callback(path);
    return true;
  }

  public Integer get(String path) {
    if(!fileMap.containsKey(path)) {
      return null;
    }
    callback(path);
    return fileMap.get(path);
  }

  public boolean watch(String path, Runnable callback) {
    if(!fileMap.containsKey(path)) {
      return false;
    }
    callbackMap.put(path, callback);
    return true;
  }

  private void callback(String path) {
    while(!path.isEmpty()) {
      if(callbackMap.containsKey(path)) {
        callbackMap.get(path).run();
      }
      int index = path.lastIndexOf("/");
      path = path.substring(0, index);
    }
  }

  public static void main(String[] args) {
    FileSystem fs = new FileSystem();
    System.out.println(fs.get("/a"));
    System.out.println(fs.create("/a", 1));
    System.out.println(fs.create("/a/b", 2));
    System.out.println(fs.create("/a/b", 2));
    System.out.println(fs.create("/c/b", 2));
    System.out.println(fs.set("/a/b", 3));
    System.out.println(fs.get("/a/b"));

    fs.watch("/a", new Runnable() {
      @Override
      public void run() {
        System.out.println("visiting a/");
//        System.exit(0);
      }
    });
    System.out.println(fs.create("/a/b/c", 4));
    System.out.println(fs.get("/a/b/c"));
    int[] nums = {1,2};
    nums[0] ^= nums[1];
    nums[1] ^= nums[0];
    nums[0] ^= nums[1];
    System.out.println("a".compareTo("b"));

  }
}
