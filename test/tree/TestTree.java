package tree;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class TestTree {
    private Tree<String, Player> tree;
    private static final Player alex = new Player("Alex", 12);
    private static final Player boris = new Player("Boris", 42);
    private static final Player ivan = new Player("Ivan", 10);
    private static final Player petr = new Player("Petr", 22);

    @Before
    public void before() {
        tree = new Tree<>();
    }

    @Test
    public void createdTreeIsEmpty() {
        assertEquals(0, tree.size());
        assertNull(tree.find(ivan.getName()));
    }

    @Test
    public void addPlayer() {
        tree.add(ivan);
        assertEquals(1, tree.size());
        assertSame(ivan, tree.find(ivan.getName()));
    }

    @Test
    public void addTwoPlayers() {
        tree.add(ivan);
        tree.add(alex);
        assertEquals(2, tree.size());
        assertSame(alex, tree.find(alex.getName()));
        assertSame(ivan, tree.find(ivan.getName()));
    }

    @Test
    public void addThreePlayers() {
        tree.add(ivan);
        tree.add(alex);
        tree.add(boris);
        assertEquals(3, tree.size());
        assertSame(boris, tree.find(boris.getName()));
        assertSame(alex, tree.find(alex.getName()));
        assertSame(ivan, tree.find(ivan.getName()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void cantAddSamePlayers() {
        tree.add(ivan);
        tree.add(ivan);
    }

    @Test
    public void cantFindNonExisting() {
        tree.add(ivan);
        assertNull(tree.find(alex.getName()));
    }

    @Test
    public void nextThreePlayers() {
        tree.add(ivan);
        tree.add(alex);
        tree.add(boris);
        assertEquals(3, tree.size());
        assertSame(null, tree.next(ivan.getName()));
        assertSame(boris, tree.next(alex.getName()));
        assertSame(ivan, tree.next(boris.getName()));
    }

    @Test
    public void addOneRemoveOne() {
        tree.add(ivan);
        tree.remove(ivan.getName());
        assertEquals(0, tree.size());
        assertNull(tree.find(ivan.getName()));
    }

    @Test
    public void addTwoRemoveOne() {
        tree.add(ivan);
        tree.add(alex);
        tree.remove(alex.getName());
        assertEquals(1, tree.size());
        assertSame(ivan, tree.find(ivan.getName()));
        assertNull(tree.find(alex.getName()));
    }

    @Test
    public void addTwoRemoveOneWhoIsRoot() {
        tree.add(ivan);
        tree.add(alex);
        tree.remove(ivan.getName());
        assertEquals(1, tree.size());
        assertSame(alex, tree.find(alex.getName()));
        assertNull(tree.find(ivan.getName()));
    }

    @Test
    public void addThreeRemoveOneWhoIsBetween() {
        tree.add(alex);
        tree.add(boris);
        tree.add(ivan);
        tree.remove(boris.getName());
        assertEquals(2, tree.size());
        assertSame(alex, tree.find(alex.getName()));
        assertNull(tree.find(boris.getName()));
        assertSame(ivan, tree.find(ivan.getName()));
    }

    @Test
    public void addFourRemoveOneWhoHasTwoChildren() {
        tree.add(alex);
        tree.add(ivan);
        tree.add(boris);
        tree.add(petr);
        tree.remove(ivan.getName());
        assertEquals(3, tree.size());
        assertSame(alex, tree.find(alex.getName()));
        assertSame(boris, tree.find(boris.getName()));
        assertSame(petr, tree.find(petr.getName()));
        assertNull(tree.find(ivan.getName()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void CantRemoveNotExisting0() {
        tree.remove(ivan.getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void CantRemoveNotExisting1() {
        tree.add(alex);
        tree.remove(ivan.getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void CantRemoveNotExisting2() {
        tree.add(alex);
        tree.add(boris);
        tree.remove(ivan.getName());
    }
}
