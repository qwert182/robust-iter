package tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Tree< K extends Comparable, T extends IHasKey<K> > {
    private int size = 0;
    private Node<K, T> root = null;
    private final List<RobustIterator<K, T> > iterators = new ArrayList<>();

    private void correctRobustIteratorsBeforeRemove(final K key, final Node<K,T> minimum) {
        for (RobustIterator<K,T> iterator : iterators) {
            if (iterator.getCurrent().getKey() == key) {
                if (iterator.getNode() == minimum)
                    iterator.need_reset_on_next = true;
                iterator.prev();
            }
        }
    }

    public void add(final T data) {
        ++size;
        if (root == null)
            root = new Node<>(data);
        else
            Node.add(root, (IHasKey<Comparable>) data);
    }

    public void remove(final K key) {
        Node new_root = null;
        if (size != 0) {
            if (size != 1) {
                correctRobustIteratorsBeforeRemove(key, Node.minimum(root));
                new_root = Node.remove(root, key);
                if (new_root == null)
                    throw new IllegalArgumentException("key not found");
            } else {
                if (!root.data.getKey().equals(key))
                    throw new IllegalArgumentException("key not found");
                correctRobustIteratorsBeforeRemove(key, Node.minimum(root));
            }
            root = new_root;
            --size;
        } else {
            throw new IllegalArgumentException("key not found");
        }
    }

    public T next(final Comparable key) {
        Node next = Node.next(root, key);
        if (next != null)
            return (T) next.data;
        else
            return null;
    }

    public T prev(final Comparable key) {
        Node prev = Node.prev(root, key);
        if (prev != null)
            return (T) prev.data;
        else
            return null;
    }

    public T find(final K key) {
        Node found = Node.find(root, key);
        if (found != null)
            return (T) found.data;
        else
            return null;
    }

    public int size() {
        return size;
    }

    public SimpleIterator<K, T> createSimpleIterator() {
        return new SimpleIterator<>(this);
    }

    public RobustIterator<K, T> createRobustIterator() {
        RobustIterator<K, T> iterator = new RobustIterator<>(this);
        iterators.add(iterator);
        return iterator;
    }

    public Node getRoot() {
        return root;
    }
}
