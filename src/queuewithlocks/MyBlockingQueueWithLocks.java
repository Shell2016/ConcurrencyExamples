package queuewithlocks;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MyBlockingQueueWithLocks<T> {

    private final int maxSize = 10;
    private final List<T> queue = new LinkedList<>();

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    public void put(T obj) throws InterruptedException {
        lock.lock();
        try {
            while (queue.size() == maxSize) {
                notFull.await();
            }
            notEmpty.signalAll();
            queue.add(obj);
        } finally {
            lock.unlock();
        }
    }

    public T take() throws InterruptedException {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                notEmpty.await();
            }
            T result = queue.remove(0);
            notFull.signalAll();
            return result;
        } finally {
            lock.unlock();
        }
    }


}
