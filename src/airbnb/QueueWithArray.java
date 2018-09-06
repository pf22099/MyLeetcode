package airbnb;

import java.util.Queue;

public class QueueWithArray {
  ListNode head;
  ListNode tail;
  int capacity;
  int size;

  public QueueWithArray(int capacity) {
    this.capacity = capacity;
    head = new ListNode(capacity);
    tail = head;
    size = 0;
  }

  public int size() {
    return size;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public void offer(int num) {
    if(!tail.isFull()) {
      tail.offer(num);
    } else {
      tail.next = new ListNode(capacity);
      tail = tail.next;
      tail.offer(num);
    }
    size++;
  }

  public int peek() {
    if(size == 0) {
      throw new IllegalStateException("The Queue is empty.");
    }
    if(head.isEmpty()) {
      head = head.next;
    }
    return head.peek();
  }

  public int poll() {
    if(size == 0) {
      throw new IllegalStateException("The Queue is empty.");
    }
    if(head.isEmpty()) {
      head = head.next;
    }
    int num = head.poll();
    size--;
    return num;
  }
}

class ListNode {
  int[] nums;
  int size;
  int head;
  int tail;
  ListNode next;

  public ListNode(int size) {
    this.size = size;
    nums = new int[size];
    head = 0;
    tail = 0;
    next = null;
  }

  public boolean isEmpty() {
    return head == tail;
  }

  public boolean isFull() {
    return head + 1 == tail;
  }

  public void offer(int num) {
    if(isFull()) {
      throw new IllegalStateException("The array is full for adding new number.");
    }
    nums[tail] = num;
    tail = (tail + 1) % size;
  }

  public int peek() {
    if(isEmpty()) {
      throw new IllegalStateException("The array is Empty.");
    }
    return nums[head];
  }

  public int poll() {
    if(isEmpty()) {
      throw new IllegalStateException("The array is Empty.");
    }
    int num = nums[head];
    head = (head + 1) % size;
    return num;
  }
}
