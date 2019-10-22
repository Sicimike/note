package com.example.utils.data;

/**
 * 数组实现循环队列
 */
public class ArrayCycleQueue<E> {

    /**
     * 默认容量
     */
    private static final int DEFAULT_CAPACITY = 10;
    /**
     * 扩容倍数
     */
    private static final int RESIZE_RATE = 2;

    private E[] table;
    private int size;
    // 头指针
    private int head;
    // 尾指针
    private int tail;

    public ArrayCycleQueue() {
        this(DEFAULT_CAPACITY);
    }

    public ArrayCycleQueue(int initCapacity) {
        table = (E[]) new Object[initCapacity + 1];
        size = 0;
        head = 0;
        tail = 0;
    }

    public boolean empty() {
        return size == 0;
    }

    public void add(E e) {
        if (size == table.length - 1) {
            resize(RESIZE_RATE * capacity());
        }
        tail = tail % table.length;
        table[tail++] = e;
        size++;
    }

    public E remove() {
        if (empty()) {
            throw new IllegalStateException("queue is empty");
        }
        E result = table[head];
        head = head % table.length;
        table[head++] = null;
        size--;
        return result;
    }

    public E peek() {
        if (empty()) {
            throw new IllegalStateException("queue is empty");
        }
        return table[head];
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return table.length - 1;
    }

    private void resize(int newCapacity) {
        E[] newArray = (E[]) new Object[newCapacity + 1];
        int j = 0;
        for (int i = head; (i % table.length) != tail; i++) {
            newArray[j++] = table[i % table.length];
        }
        table = newArray;
        head = 0;
        tail = size;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("{ capacity: " + capacity() + ", size: " + size + ", item: head -> [");
        for (int i = head; (i % table.length) != tail; i++) {
            result.append(table[(i % table.length)]);
            if ((i % table.length) != tail - 1) {
                result.append(", ");
            }
        }
        result.append("] -> tail }");
        return result.toString();
    }

}
