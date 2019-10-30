
public class ArrayQueue<E> {

    private ArrayList<E> list;

    public ArrayQueue() {
        list = new ArrayList();
    }

    public ArrayQueue(int initCapacity) {
        list = new ArrayList(initCapacity);
    }

    public boolean empty() {
        return list.size() == 0;
    }

    /**
     * 出队
     * @param e
     */
    public void offer(E e) {
        list.add(e);
    }

    /**
     * 入队
     * @return
     */
    public E poll() {
        return list.remove(0);
    }

    /**
     * 查看队列头部元素
     * @return
     */
    public E peek() {
        return list.get(0);
    }

    public int size() {
        return list.size();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("{ capacity: " + list.capacity() + ", size: " + list.size() + ", item: head -> [");
        for (int i = 0; i < list.size(); i++) {
            result.append(list.get(i));
            if (i != list.size() - 1) {
                result.append(", ");
            }
        }
        result.append("] -> tail }");
        return result.toString();
    }

}
