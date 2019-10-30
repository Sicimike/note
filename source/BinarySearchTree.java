import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 二分搜索树
 */
public class BinarySearchTree<E extends Comparable<E>> {

    private class Node {

        private E val;
        private Node left;
        private Node right;

        public Node(E val) {
            this.val = val;
            this.left = null;
            this.right = null;
        }

        public Node() {
            this(null);
        }
    }

    // 根节点
    private Node root;
    // 节点数
    private int size;

    public BinarySearchTree() {
        this.root = null;
        this.size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    /**
     * 新增节点
     *
     * @param val
     */
//    public void add(E val) {
//        if (val == null) {
//            throw new IllegalArgumentException("illegal argument: null ");
//        }
//
//        if (root == null) {
//            size++;
//            root = new Node(val);
//        } else {
//            add(root, val);
//        }
//    }

    /**
     * 新增节点的递归方法
     *
     * @param node
     * @param val
     */
//    private void add(Node node, E val) {
//        if (node == null) {
//            return;
//        }
//        if (val.compareTo(node.val) < 0 && node.left == null) {
//            node.left = new Node(val);
//            size++;
//            return;
//        }
//        if (val.compareTo(node.val) > 0 && node.right == null) {
//            node.right = new Node(val);
//            size++;
//            return;
//        }
//        if (val.compareTo(node.val) < 0) {
//            add(node.left, val);
//        } else if (val.compareTo(node.val) > 0) {
//            add(node.right, val);
//        }
//    }

    /**
     * 新增节点
     * @param val
     */
    public void add(E val) {
        if (val == null) {
            throw new IllegalArgumentException("illegal argument: null ");
        }
        root = add(root, val);
    }

    /**
     * 向以node为根节点的子树中插入元素
     * 并且返回插入成功后的子树的根节点
     * @param node
     * @param val
     * @return
     */
    private Node add(Node node, E val) {
        if (node == null) {
            node = new Node(val);
            return node;
        }
        if (val.compareTo(node.val) < 0) {
            node.left = add(node.left, val);
        } else if (val.compareTo(node.val) > 0){
            node.right = add(node.right, val);
        }
        return node;
    }

    /**
     * 二分搜索树中是否包含某个节点
     *
     * @param val
     * @return
     */
    public boolean contains(E val) {
        if (val == null) {
            throw new IllegalArgumentException("illegal argument: null ");
        }
        return contains(root, val);
    }

    /**
     * 查询以node为根节点的二分搜索树中是否包含某个节点，并返回
     *
     * @param node
     * @param val
     * @return
     */
    private boolean contains(Node node, E val) {
        if (node == null) {
            return false;
        }
        if (val.compareTo(node.val) == 0) {
            return true;
        } else if (val.compareTo(node.val) < 0) {
            return contains(node.left, val);
        } else {
            return contains(node.right, val);
        }
    }

    /**
     * 前序遍历（递归实现）
     */
    public void preorder() {
        preorder(root);
    }

    private void preorder(Node node) {
        if (node == null) {
            return;
        }
        System.out.print(node.val + " ");
        preorder(node.left);
        preorder(node.right);
    }

    /**
     * 前序遍历（非递归实现）
     * 深度优先遍历
     */
    public void dfs() {
        if (root == null) {
            System.out.println("tree is empty");
            return;
        }
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node curr = stack.pop();
            System.out.print(curr.val + " ");
            if (curr.right != null) {
                stack.push(curr.right);
            }
            if (curr.left != null) {
                stack.push(curr.left);
            }
        }
    }

    /**
     * 广度优先遍历
     */
    public void bfs() {
        if (root == null) {
            System.out.println("tree is empty");
            return;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Node curr = queue.poll();
            System.out.print(curr.val + " ");
            if (curr.left != null) {
                queue.offer(curr.left);
            }
            if (curr.right != null) {
                queue.offer(curr.right);
            }
        }
    }

    /**
     * 中序遍历（递归实现）
     */
    public void inorder() {
        inorder(root);
    }

    private void inorder(Node node) {
        if (node == null) {
            return;
        }
        inorder(node.left);
        System.out.print(node.val + " ");
        inorder(node.right);
    }

    /**
     * 后序遍历（递归实现）
     */
    public void postorder() {
        postorder(root);
    }

    private void postorder(Node node) {
        if (node == null) {
            return;
        }
        postorder(node.left);
        postorder(node.right);
        System.out.print(node.val + " ");
    }

    /**
     * 找到二分搜索树中最小的节点
     *
     * @return
     */
    public E min() {
        if (root == null) {
            return null;
        }
        return min(root).val;
    }

    /**
     * 找到以node为根节点的二分搜索树中最小的节点并返回
     *
     * @param node
     * @return
     */
    private Node min(Node node) {
        if (node.left == null) {
            return node;
        }
        return min(node.left);
    }

    /**
     * 删除二分搜索树最小的节点
     *
     * @return
     */
    public E removeMin() {
        E result = min();
        root = removeMin(root);
        return result;
    }

    /**
     * 删除以node为根节点的二分搜索树中的最小节点，并返回删除后的根节点
     *
     * @param node
     * @return
     */
    private Node removeMin(Node node) {
        if (node.left == null) {
            Node right = node.right;
            node.right = null;
            size--;
            return right;
        }
        node.left = removeMin(node.left);
        return node;
    }

    /**
     * 删除二分搜索树中指定的元素e
     *
     * @param val
     * @return
     */
    public E remove(E val) {
        if (root == null) {
            return null;
        }
        return remove(root, val).val;
    }

    /**
     * 删除以node为根节点的二分搜索树中的指定元素e，并返回删除后的跟节点
     *
     * @param node
     * @param val
     * @return
     */
    private Node remove(Node node, E val) {
        if (node == null) {
            return null;
        }
        if (val.compareTo(node.val) < 0) {
            node.left = remove(node.left, val);
            return node;
        } else if (val.compareTo(node.val) > 0) {
            node.right = remove(node.right, val);
            return node;
        } else {
            if (node.right == null) {
                // 只有左子树（包括叶子节点）
                Node left = node.left;
                node.left = null;
                size--;
                return left;
            } else if (node.left == null) {
                // 只有右子树
                Node right = node.right;
                node.right = null;
                size--;
                return right;
            } else {
                // 既有左子树，又有右子树
                Node curr = min(node.right);
                curr.right = removeMin(node.right);
                curr.left = node.left;
                node.left = node.right = null;
                return curr;
            }
        }
    }

}
