package queue;

import java.util.LinkedList;
import java.util.List;

public class MyBlockingQueue<T> {

    private int maxSize = 10;
    private final List<T> myBlockingQueue = new LinkedList<>();

    public MyBlockingQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    public synchronized T take() {
        while (myBlockingQueue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if (myBlockingQueue.size() == maxSize) {
            notifyAll();
        }
        return myBlockingQueue.remove(0);

    }

    public synchronized void put(T obj) {
        while (myBlockingQueue.size() == maxSize) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if (myBlockingQueue.isEmpty()) {
            notifyAll();
        }
        myBlockingQueue.add(obj);
    }
}
