package airbnb;

import java.util.*;

public class PanlindromPairs {

  /**
   * TC: O(n * m^2), n is the number of words, while m is the max length of word.
   * @param words
   * @return
   */
  public List<List<Integer>> palindromePairs(String[] words) {
    List<List<Integer>> res = new ArrayList<>();
    if(words == null || words.length == 0) {
      return res;
    }
    Map<String, Integer> wordMap = createWordMap(words);
    addressEmptyString(wordMap, res);
    addressReversedString(wordMap, res);
    addressPartialPalindrome(wordMap, res);
    return res;
  }

  private void addressPartialPalindrome(Map<String, Integer> wordMap, List<List<Integer>> res) {
    for(String word : wordMap.keySet()) {
      int i = wordMap.get(word);
      for(int k = 1; k < word.length(); k++) {
        String left = word.substring(0, k);
        String right = word.substring(k);
        if(isPalindrome(left)) {
          String reversedRight = new StringBuilder(right).reverse().toString();
          if(wordMap.containsKey(reversedRight)) {
            res.add(Arrays.asList(wordMap.get(reversedRight), i));
          }
        }
        if(isPalindrome(right)) { // 注意不是else if，否则会有miss一些结果
          String reversedLeft = new StringBuilder(left).reverse().toString();
          if(wordMap.containsKey(reversedLeft)) {
            res.add(Arrays.asList(i, wordMap.get(reversedLeft)));
          }
        }
      }
    }
  }

  private void addressReversedString(Map<String, Integer> wordMap, List<List<Integer>> res) {
    for(String word : wordMap.keySet()) {
      int i = wordMap.get(word);
      String reversed = new StringBuilder(word).reverse().toString();
      int j = wordMap.getOrDefault(reversed, -1);
      if(j != -1 && i != j) {
        res.add(Arrays.asList(i, j));
      }
    }
  }

  private void addressEmptyString(Map<String, Integer> wordMap, List<List<Integer>> res) {
    if(wordMap.containsKey("")) {
      int i = wordMap.get("");
      for(String word : wordMap.keySet()) {
        if(isPalindrome(word) && wordMap.get(word) != i) {
          int j = wordMap.get(word);
          res.add(Arrays.asList(i, j));
          res.add(Arrays.asList(j, i));
        }
      }
    }
  }

  private Map<String, Integer> createWordMap(String[] words) {
    Map<String, Integer> map = new HashMap<>();
    for(int i = 0; i < words.length; i++) {
      map.put(words[i], i);
    }
    return map;
  }

  private boolean isPalindrome(String str) {
    int l = 0;
    int r = str.length()-1;
    while(l < r) {
      if(str.charAt(l++) != str.charAt(r--)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Using Trie
   * 每个Trie节点存word index和word从后往前到达这个index时，之前为palindrome的所有word
   * 将每个word，倒序插入到Trie中，每到一个节点看word开始到这个char是否为palindrome，如果是，插入到当前节点的list中，当遍历完单词，
   * 将这个单词的index插入到node的index和list中
   *
   * search的时候，每个单词正向在trie中查找，每到一个节点，看齐是否为一个单词的结束节点，如果是，判断单词中的剩余字母是否为palindrome，
   * 是，加入res中。等遍历完单词，将当前node的list中所有index跟当前word的index拼起来加入res，因为该单词跟list中的单词的后半部分palindrome，
   * 而这些单词的前半部分都self-palindrome，所以是palindrome pair。
   */
  public List<List<Integer>> palindromePairs_WithTrie(String[] words) {
    List<List<Integer>> res = new ArrayList<>();
    if(words == null || words.length == 0) {
      return res;
    }
    Trie root = new Trie();
    for(int i = 0; i < words.length; i++) {
      add(words[i], i, root);
    }
    for(int i = 0; i < words.length; i++) {
      search(words[i], i, root, res);
    }
    return res;
  }

  private void search(String word, int index, Trie root, List<List<Integer>> res) {
    for(int i = 0; i < word.length(); i++) {
      if(root.index >= 0 && index != root.index && isPalindrome(word, i, word.length()-1)) {
        res.add(Arrays.asList(index, root.index));
      }
      int j = word.charAt(i) - 'a';
      if(root.children[j] == null) {
        return;
      }
      root = root.children[j];
    }

    for(int p : root.list) {
      if(p == index) continue;
      res.add(Arrays.asList(index, p));
    }
  }

  private void add(String word, int index, Trie root) {
    for(int i = word.length()-1; i >= 0; i--) {
      int j = word.charAt(i) - 'a';
      if(root.children[j] == null) {
        root.children[j] = new Trie();
      }
      if(isPalindrome(word, 0, i)) {
        root.list.add(index);
      }//System.out.println(root.index + " : " + root.list);
      root = root.children[j];
    }
    root.index = index;
    root.list.add(index);
    // System.out.println(root.index + " : " + root.list);
  }

  private boolean isPalindrome(String word, int i, int j) {
    while(i < j) {
      if(word.charAt(i++) != word.charAt(j--)) {
        return false;
      }
    }
    return true;
  }
}

class Trie {
  int index;
  List<Integer> list;
  Trie[] children;
  public Trie() {
    index = -1;
    children = new Trie[26];
    list = new ArrayList<>();
  }
}
