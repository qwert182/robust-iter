package tree;

public class RobustIterator< K extends Comparable, T extends IHasKey<K> > extends SimpleIterator<K, T> {
    RobustIterator(final Tree<K, T> tree) {
        super(tree);
    }
}
