**
 * 最小堆
 * 利用动态数组ArrayList实现
 * 从数组第0个索引开始存储元素
 */
public class MinHeap<E extends Comparable<E>> {

    /**
     * 此处是之前博客中实现的ArrayList，并非JDK提供的ArrayList
     * 实现过程：https://blog.csdn.net/Baisitao_/article/details/102575180
     * 源代码：https://github.com/Sicimike/note/blob/master/source/ArrayList.java
     */
    private ArrayList<E> data;

    public MinHeap(int initCapacity) {
        data = new ArrayList(initCapacity);
    }

    public MinHeap() {
        data = new ArrayList();
    }

    /**
     * 利用构造函数实现heapify功能
     * heapify：根据数组生成最小堆
     *
     * @param arr
     */
    public MinHeap(E[] arr) {
        data = new ArrayList(arr);
        for (int i = parent(data.size() - 1); i >= 0; i--) {
            siftDown(i);
        }
    }

    public int size() {
        return data.size();
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }

    /**
     * 根据子节点找到父节点
     *
     * @param child
     * @return
     */
    private int parent(int child) {
        if (child <= 0 || child > size()) {
            throw new IllegalArgumentException("illegal argument:" + child);
        }
        return (child - 1) / 2;
    }

    /**
     * 根据父节点找到左子节点
     *
     * @param parent
     * @return
     */
    private int left(int parent) {
        return parent * 2 + 1;
    }

    /**
     * 根据父节点找到右子节点
     *
     * @param parent
     * @return
     */
    private int right(int parent) {
        return parent * 2 + 2;
    }

    /**
     * 添加元素
     *
     * @param e
     */
    public void add(E e) {
        data.add(e);
        siftUp(size() - 1);
    }

    /**
     * 把节点上浮（siftUp）到合适的地方
     *
     * @param index
     */
    private void siftUp(int index) {
        while (index > 0 && data.get(index).compareTo(data.get(parent(index))) < 0) {
            // 当前节点的元素小于父节点的元素，需要上移（交换）
            data.swap(index, parent(index));
            // 更新当前元素的index
            index = parent(index);
        }
    }

    /**
     * 查看堆中的最小元素
     * 因为是最小堆，所以就是data.get(0)
     *
     * @return
     */
    public E min() {
        return data.get(0);
    }

    /**
     * 移除，并返回最小的元素
     *
     * @return
     */
    public E extractMin() {
        // 获取最小的元素用于返回
        E result = min();
        // 把最小的元素和最后一个元素交换位置
        data.swap(0, size() - 1);
        // 删除最后一个元素（最小值）
        data.remove();
        // 堆顶元素下沉到合适的位置
        siftDown(0);
        return result;
    }

    /**
     * 把节点下沉（siftDown）到合适的地方
     *
     * @param index
     */
    private void siftDown(int index) {
        while (left(index) < size()) {
            int k = left(index);
            if (right(index) < size() && data.get(right(index)).compareTo(data.get(left(index))) < 0) {
                // 当前节点有右孩子，且右孩子的值小于左孩子的值，则把右孩子记录为待交换的节点k，否则记录左节点为k
                k = right(index);
            }

            if (data.get(index).compareTo(data.get(k)) <= 0) {
                break;
            }
            // 当前节点的值大于k节点的值，进行交换
            data.swap(index, k);
            index = k;
        }
    }

}
