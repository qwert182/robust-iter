package tree;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestSimpleIterator {
    protected Tree<Integer, Int> tree;

    protected SimpleIterator<Integer, Int> newIteratorFrom(final Tree<Integer, Int> tree) {
        return tree.createSimpleIterator();
    }

    @Before
    public void before() {
        tree = new Tree<>();
    }

    @Test
    public void canIterateEmptyTree() {
        SimpleIterator iterator = newIteratorFrom(tree);
        iterator.reset();
        assertFalse(iterator.isNotDone());
    }


    @Test
    public void canIterateOnOne() {
        tree.add(new Int(1));
        SimpleIterator iterator = newIteratorFrom(tree);
        iterator.reset();
        assertTrue(iterator.isNotDone());
        assertSame(tree.find(1), iterator.getCurrent());
        iterator.next();
        assertFalse(iterator.isNotDone());
    }

    @Test
    public void canIterateOnFive() {
        final int[] arr = new int[] {4, 87, 12, 2, 5};
        final int[] arr_sorted = new int[] {2, 4, 5, 12, 87};
        for (int i : arr) tree.add(new Int(i));
        int i = 0;

        SimpleIterator<Integer, Int> iterator = newIteratorFrom(tree);
        iterator.reset();
        while (iterator.isNotDone()) {
            Int cur = iterator.getCurrent();
            assertEquals(arr_sorted[i], cur.value);
            iterator.next();
            ++i;
        }
    }
}
