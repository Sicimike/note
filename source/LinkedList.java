
public class LinkedList<E> {

    private class Node {
        private E e;
        private Node next;

        public Node() {
            this(null, null);
        }

        public Node(E e) {
            this(e, null);
        }

        public Node(E e, Node next) {
            this.e = e;
            this.next = next;
        }
    }

    private int size;
    private Node dummyHead;

    public LinkedList() {
        this.size = 0;
        dummyHead = new Node();
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean add(int index, E e) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("illegal index");
        }
        Node prev = dummyHead;
        for (int i = 0; i < index; i++) {
            prev = prev.next;
        }
        Node node = new Node(e);
        node.next = prev.next;
        prev.next = node;
        // 以上行代码等价于这行代码
        // prev.next = new Node(e, prev.next);
        size++;
        return true;
    }

    public boolean add(E e) {
        return add(size, e);
    }

    public boolean addFirst(E e) {
        return add(0, e);
    }

    public E remove(int index) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("illegal index");
        }
        if (size == 0) {
            throw new IllegalArgumentException("list is empty");
        }
        Node prev = dummyHead;
        for (int i = 0; i < index; i++) {
            prev = prev.next;
        }
        Node result = prev.next;
        prev.next = result.next;
        result.next = null;
        size--;
        return result.e;
    }

    public E removeFirst() {
        return remove(0);
    }

    public E remove() {
        return remove(size - 1);
    }

    /**
     * 删除指定元素
     *
     * @param e 指定元素
     * @return
     */
    public boolean remove(E e) {
        if (size == 0) {
            throw new IllegalArgumentException("list is empty");
        }
        Node prev = dummyHead;
        while (prev.next != null && !e.equals(prev.next.e)) {
            prev = prev.next;
        }
        if (prev.next != null && e.equals(prev.next.e)) {
            Node result = prev.next;
            prev.next = result.next;
            result.next = null;
            size--;
            return true;
        }
        return false;
    }

    public boolean set(int index, E e) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("illegal index");
        }
        Node curr = dummyHead.next;
        for (int i = 0; i < index; i++) {
            curr = curr.next;
        }
        curr.e = e;
        return true;
    }

    public boolean contains(E e) {
        Node curr = dummyHead.next;
        while (curr != null) {
            if (e.equals(curr.e)) {
                return true;
            }
            curr = curr.next;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("{ size: " + size() + ", item: ");
        Node curr = dummyHead.next;
        while (curr != null) {
            result.append(curr.e + "->");
            curr = curr.next;
        }
        result.append("NULL }");
        return result.toString();
    }
}
