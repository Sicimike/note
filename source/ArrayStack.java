package com.example.utils.data;

public class ArrayStack<E> {

    private ArrayList<E> list;

    public ArrayStack() {
        list = new ArrayList();
    }

    public ArrayStack(int initCapacity) {
        list = new ArrayList(initCapacity);
    }

    public boolean empty() {
        return list.size() == 0;
    }

    /**
     * 入栈
     * @param e
     * @return
     */
    public E push(E e) {
        list.add(e);
        return e;
    }

    /**
     * 出栈
     * @return
     */
    public E pop() {
        return list.remove();
    }

    /**
     * 查看栈顶元素
     * @return
     */
    public E peek() {
        return list.get(list.size() - 1);
    }

    public int size() {
        return list.size();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("{ capacity: " + list.capacity() + ", size: " + list.size() + ", item: [");
        for (int i = 0; i < list.size(); i++) {
            result.append(list.get(i));
            if (i != list.size() - 1) {
                result.append(", ");
            }
        }
        result.append("] -> top }");
        return result.toString();
    }
}
