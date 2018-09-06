import java.io.*;
import java.util.Comparator;
import java.util.PriorityQueue;

public class DuplicateFilesFinder {

}

class FileMetaData {
  String path;
  long size;
  String sampleHash;
  String fullHash;
  BufferedReader reader;
  // Configurable
  int maxBufferSize = 1024;

  public FileMetaData(String path) {
    this.path = path;
    File file = new File(path);
    size = file.length();
  }
}
