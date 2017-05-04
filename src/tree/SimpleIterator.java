package tree;

public class SimpleIterator< K extends Comparable, T extends IHasKey<K> > {
    private final Tree<K, T> tree;
    private Node<K, T> node;

    SimpleIterator(final Tree<K, T> tree) {
        this.tree = tree;
        node = null;
    }

    public void reset() {
        node = Node.minimum(tree.getRoot());
    }

    public boolean isNotDone() {
        return node != null;
    }

    public T getCurrent() {
        return node.data;
    }

    public void next() {
        node = Node.next(tree.getRoot(), node.data.getKey());
    }

    void prev() {
        node = Node.prev(tree.getRoot(), node.data.getKey());
    }

    Node<K, T> getNode() {
        return node;
    }

    @Override
    public String toString() {
        if (node != null)
            return node.toString();
        else
            return "<null>";
    }
}
