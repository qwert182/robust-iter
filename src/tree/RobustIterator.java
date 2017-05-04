package tree;

public class RobustIterator< K extends Comparable, T extends IHasKey<K> > extends SimpleIterator<K, T> {
    boolean need_reset_on_next = false;
    RobustIterator(final Tree<K, T> tree) {
        super(tree);
    }

    @Override
    public void next() {
        if (need_reset_on_next) {
            reset();
            need_reset_on_next = false;
        } else {
            super.next();
        }
    }
}
