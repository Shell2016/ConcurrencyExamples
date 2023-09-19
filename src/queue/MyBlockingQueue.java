package queue;

import java.util.LinkedList;
import java.util.List;

public class MyBlockingQueue<T> {

    private int maxSize = 10;
    private final List<T> myBlockingQueue = new LinkedList<>();
    private Object notFull = new Object();
    private Object notEmpty = new Object();

    public MyBlockingQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    public synchronized T take() {
        while (myBlockingQueue.isEmpty()) {
            try {
                notEmpty.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        final T removed = myBlockingQueue.remove(0);
        notFull.notifyAll();
        return removed;
    }

    public synchronized void put(T obj) {
        while (myBlockingQueue.size() == maxSize) {
            try {
                notFull.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        myBlockingQueue.add(obj);
        notEmpty.notifyAll();
    }
}
