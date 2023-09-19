package queue;

import java.util.LinkedList;
import java.util.List;

public class MyBlockingQueue<T> {

    private int maxSize = 10;
    private final List<T> queue = new LinkedList<>();
    private final Object notFull = new Object();
    private final Object notEmpty = new Object();

    public MyBlockingQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    public synchronized T take() throws InterruptedException {
        while (queue.isEmpty()) {
            notEmpty.wait();
        }
        final T removed = queue.remove(0);
        notFull.notifyAll();
        return removed;
    }

    public synchronized void put(T obj) throws InterruptedException {
        while (queue.size() == maxSize) {
            notFull.wait();
        }
        queue.add(obj);
        notEmpty.notifyAll();
    }
}
