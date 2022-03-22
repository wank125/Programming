package com.mike.generic;

interface Iterators<T> {
    boolean hasNext();

    T next();
}

/**
 * 队列
 *
 * @param <T> 参数类型
 */
public class MyQueue<T> {

    private final int max;
    private int front;
    private int rear;

    private Object[] arr;

    public MyQueue(int maxSize) {
        this.max = maxSize;
        this.arr = new Object[max];
        front = -1;
        rear = -1;
    }

    public boolean isEmpty() {
        return front == rear;
    }

    public boolean isFull() {
        return rear == max - 1;
    }

    public void offer(T value) {
        if (isFull()) {
            return;
        }
        rear++;
        arr[rear] = value;
    }

    public T poll() {
        if (isEmpty()) {
            new RuntimeException("");
        }
        return (T) arr[front + 1];
    }

    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int ipos = 0;

            @Override
            public boolean hasNext() {
                return ipos < arr.length;
            }

            @Override
            public T next() {
                return (T) arr[ipos++];
            }
        };
    }


    public static void main(String[] args) {
        MyQueue<String> stringMyQueue = new MyQueue<String>(10);
        stringMyQueue.offer("A");
        stringMyQueue.offer("B");
        System.out.printf(stringMyQueue.poll());
        System.out.printf(stringMyQueue.poll());
        System.out.printf(stringMyQueue.poll());
    }
}
