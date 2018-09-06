import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueue<E> {
  private Queue<E> queue;
  private int capacity;
  private ReentrantLock lock;
  private Condition notFull;
  private Condition notEmpty;

  public BlockingQueue(int capacity) {
    this.capacity = capacity;
    queue = new LinkedList<>();
    lock = new ReentrantLock();
    notFull = lock.newCondition();
    notEmpty = lock.newCondition();
  }

  public void put(E obj) throws InterruptedException {
    final ReentrantLock local = this.lock;
    local.lock();
    try {
      while(queue.size() == capacity) {
        notFull.await();
      }
      queue.add(obj);
      notEmpty.signal();
    } finally {
      local.unlock();
    }
  }

  public E take() throws InterruptedException {
    final ReentrantLock local = this.lock;
    local.lock();
    try {
      while(queue.isEmpty()) {
        notEmpty.await();
      }
      final E res = queue.poll();
      notFull.signal();
      return res;
    } finally {
      local.unlock();
    }
  }

  public int size() {
    final ReentrantLock local = this.lock;
    local.lock();
    try {
      return queue.size();
    } finally {
      local.unlock();
    }
  }

}
