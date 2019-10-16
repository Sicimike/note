package com.example.utils.data;

/**
 * 动态数组实现ArrayList
 */
public class ArrayList<E> {

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

    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public ArrayList(int initCapacity) {
        table = (E[]) new Object[initCapacity];
        size = 0;
    }

    /**
     * 集合是否为空
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 获取元素个数
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * 获取容量
     * @return
     */
    public int capacity() {
        return table.length;
    }

    /**
     * 获取指定位置元素
     * @param index 指定位置
     * @return 指定位置元素
     */
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("illegal index, max " + size + " min 0 but index is " + index);
        }
        return table[index];
    }

    /**
     * 指定位置新增
     * @param index 下标
     * @param e 新增元素
     * @return 是否成功
     */
    public boolean add(int index, E e) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("illegal index, max " + table.length + " min 0 but index is " + index);
        }
        if (size == table.length) {
            resize(RESIZE_RATE * size);
        }
        // 移动数组项时，必须按照从后到前的顺序
        for (int i = size; i > index; i--) {
            table[i] = table[i - 1];
        }
        table[index] = e;
        size++;
        return true;
    }

    /**
     * 尾部新增
     * @param e 新增元素
     * @return 是否成功
     */
    public boolean add(E e) {
        return add(size, e);
    }

    /**
     * 删除指定位置的元素
     * @param index 下标
     * @return 是否成功
     */
    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("illegal index, max " + size + " min 0 but index is " + index);
        }
        // 记录删除的项，用于返回
        E ret = table[index];
        // 移动数组项时，必须按照从前到后的顺序
        for (int i = index; i < size - 1; i++) {
            table[i] = table[i + 1];
        }
        table[size - 1] = null;
        size--;
        if ((size == table.length / 4) && (table.length / RESIZE_RATE != 0)) {
            resize(table.length / RESIZE_RATE);
        }
        return ret;
    }

    /**
     * 删除尾部元素
     * @return
     */
    public E remove() {
        return remove(size - 1);
    }

    /**
     * 替换指定位置元素
     * @param index 下标
     * @param e
     */
    public void replace(int index, E e) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("illegal index, max " + size + " min 0 but index is " + index);
        }
        table[index] = e;
    }

    /**
     * 查找某个元素的下标
     * @param e 指定元素
     * @return 该元素的下标 -1 表示不存在该元素
     */
    public int indexOf(E e) {
        for (int i = 0; i < size; i++) {
            if (e.equals(table[i])) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 是否包含某个元素
     * @param e 指定元素
     * @return
     */
    public boolean contains(E e) {
        return indexOf(e) > -1;
    }

    /**
     * 清除所有元素
     * @return
     */
    public boolean clear() {
        for (int i = 0; i < size; i++) {
            table[i] = null;
        }
        size = 0;
        return true;
    }

    // 扩容
    private void resize(int newCapacity) {
        E[] newArray = (E[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newArray[i] = table[i];
        }
        table = newArray;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("{ capacity: " + capacity() + ", size: " + size() + ", item: [");
        for (int i = 0; i < size; i++) {
            result.append(table[i]);
            if (i != size - 1) {
                result.append(", ");
            }
        }
        result.append("] }");
        return result.toString();
    }

}
