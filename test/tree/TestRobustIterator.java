package tree;

import org.junit.Test;

import static org.junit.Assert.*;


// robust iterator
// реализовать, чтобы
// при удалении не падало
// при вставке не проходило 2 раза по одному и тому же элементу


public class TestRobustIterator extends TestSimpleIterator {
    @Override
    protected RobustIterator<Integer, Int> newIteratorFrom(final Tree<Integer, Int> tree) {
        return tree.createRobustIterator();
    }

    @Test
    public void canAddAfter() {
        tree.add(new Int(1));
        RobustIterator iterator = newIteratorFrom(tree);
        iterator.reset();

        assertTrue(iterator.isNotDone());
        assertSame(tree.find(1), iterator.getCurrent());

        tree.add(new Int(2));
        iterator.next();

        assertTrue(iterator.isNotDone());
        assertSame(tree.find(2), iterator.getCurrent());

        iterator.next();

        assertFalse(iterator.isNotDone());
    }

    @Test
    public void canAddBefore() {
        tree.add(new Int(1));
        RobustIterator iterator = newIteratorFrom(tree);
        iterator.reset();

        assertTrue(iterator.isNotDone());
        assertSame(tree.find(1), iterator.getCurrent());

        tree.add(new Int(0));
        iterator.next();

        assertFalse(iterator.isNotDone());
    }

    @Test
    public void canRemoveBefore() {
        tree.add(new Int(1));
        tree.add(new Int(2));
        RobustIterator iterator = newIteratorFrom(tree);
        iterator.reset();

        assertTrue(iterator.isNotDone());
        assertSame(tree.find(1), iterator.getCurrent());
        iterator.next();

        tree.remove(1);

        assertTrue(iterator.isNotDone());
        assertSame(tree.find(2), iterator.getCurrent());

        iterator.next();

        assertFalse(iterator.isNotDone());
    }

    @Test
    public void canRemoveAfter() {
        tree.add(new Int(2));
        tree.add(new Int(1));
        RobustIterator iterator = newIteratorFrom(tree);
        iterator.reset();

        assertTrue(iterator.isNotDone());
        assertSame(tree.find(1), iterator.getCurrent());

        tree.remove(2);

        assertTrue(iterator.isNotDone());
        assertSame(tree.find(1), iterator.getCurrent());

        iterator.next();

        assertFalse(iterator.isNotDone());
    }

    @Test
    public void canRemoveThisOne() {
        tree.add(new Int(1));
        RobustIterator iterator = newIteratorFrom(tree);
        iterator.reset();

        assertTrue(iterator.isNotDone());
        assertSame(tree.find(1), iterator.getCurrent());

        tree.remove(1);

        iterator.next();
        assertFalse(iterator.isNotDone());
    }

    @Test
    public void canRemoveThisBetween() {
        tree.add(new Int(2));
        tree.add(new Int(1));
        tree.add(new Int(3));
        RobustIterator iterator = newIteratorFrom(tree);
        iterator.reset();
        iterator.next();

        assertTrue(iterator.isNotDone());
        assertSame(tree.find(2), iterator.getCurrent());

        tree.remove(2);

        iterator.next();
        assertTrue(iterator.isNotDone());
        assertSame(tree.find(3), iterator.getCurrent());

        iterator.next();

        assertFalse(iterator.isNotDone());
    }


    @Test
    public void canRemoveAllInCycle() {
        final int[] arr = new int[] {4, 87, 12, 2, 5};
        for (int i : arr) tree.add(new Int(i));
        RobustIterator<Integer, Int> iterator = newIteratorFrom(tree);
        iterator.reset();

        while (iterator.isNotDone()) {
            Int current = iterator.getCurrent();
            tree.remove(current.getKey());
            iterator.next();
        }

        assertEquals(0, tree.size());
    }
}
