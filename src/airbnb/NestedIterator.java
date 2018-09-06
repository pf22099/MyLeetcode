package airbnb;

import java.util.*;

public class NestedIterator implements Iterator<Integer> {
  private Iterator<NestedInteger> iterator;
  private Stack<Iterator<NestedInteger>> stack;
  private NestedInteger curr;

  public NestedIterator(List<NestedInteger> nestedList) {
    iterator = nestedList.iterator();
    stack = new Stack<>();
    curr = null;
  }

  @Override
  public Integer next() {
    Integer res = curr.getInteger();
    curr = null;
    // System.out.println(res);
    return res;
  }

  @Override
  public boolean hasNext() {
    while(curr == null || !curr.isInteger()) {
      while(!iterator.hasNext()) {
        if(stack.isEmpty()) {
          return false;
        }
        iterator = stack.pop();
      }
      curr = iterator.next();
      if(!curr.isInteger()) {
        stack.push(iterator);
        iterator = curr.getList().iterator();
      }
    }
    return true;
  }
}


// This is the interface that allows for creating nested lists.
// You should not implement it, or speculate about its implementation
interface NestedInteger {

    // @return true if this NestedInteger holds a single integer, rather than a nested list.
    public boolean isInteger();

    // @return the single integer that this NestedInteger holds, if it holds a single integer
    // Return null if this NestedInteger holds a nested list
    public Integer getInteger();

    // @return the nested list that this NestedInteger holds, if it holds a nested list
    // Return null if this NestedInteger holds a single integer
    public List<NestedInteger> getList();
}
