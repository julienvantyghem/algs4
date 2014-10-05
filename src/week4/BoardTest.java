import org.junit.Ignore;
import org.junit.Test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class BoardTest {

    Board testedBoard = new Board(new int[][]{{8, 1, 3}, {4, 0, 2}, {7, 6, 5}});

    @Test
    public void testEquals() throws Exception {
        assertEquals(testedBoard, new Board(new int[][]{{8, 1, 3}, {4, 0, 2}, {7, 6, 5}}));
        assertEquals(testedBoard, testedBoard);
        assertNotEquals(testedBoard, new Board(new int[][]{{1, 8, 3}, {4, 0, 2}, {7, 6, 5}}));
        assertNotEquals(testedBoard, new Board(new int[][]{{8,1},{2,3}}));
        assertNotEquals(testedBoard, null);
    }

    @Test
    public void testDimension() throws Exception {
        assertEquals(3, testedBoard.dimension());
    }

    @Test
    public void testHamming() throws Exception {
        assertEquals(5, testedBoard.hamming());
    }

    @Test
    public void testManhattan() throws Exception {
        assertEquals(10, testedBoard.manhattan());
    }

    @Test
    public void testIsGoal() throws Exception {
        assertFalse(testedBoard.isGoal());
        assertTrue(new Board(new int[][]{{1,2,3},{4,5,6},{7,8,0}}).isGoal());
    }

    @Test
    public void testToString() throws Exception {
        assertEquals("3\n 8  1  3 \n 4  0  2 \n 7  6  5 \n", testedBoard.toString());
    }

    @Test
    public void testTwin() throws Exception {
        assertEquals(new Board(new int[][]{{1, 8, 3}, {4, 0, 2}, {7, 6, 5}}), testedBoard.twin());
        assertEquals(new Board(new int[][]{{0, 8, 3}, {4, 1, 2}, {7, 6, 5}}), new Board(new int[][]{{0, 3, 8}, {4, 1, 2}, {7, 6, 5}}).twin());
    }

    @Test
    public void testNeighbors() throws Exception {
        Iterator<Board> it = testedBoard.neighbors().iterator();
        assertTrue(it.hasNext());
        assertEquals(new Board(new int[][]{{8, 0, 3}, {4, 1, 2}, {7, 6, 5}}), it.next());
        assertTrue(it.hasNext());
        assertEquals(new Board(new int[][]{{8, 1, 3}, {4, 6, 2}, {7, 0, 5}}), it.next());
        assertTrue(it.hasNext());
        assertEquals(new Board(new int[][]{{8, 1, 3}, {0, 4, 2}, {7, 6, 5}}), it.next());
        assertTrue(it.hasNext());
        assertEquals(new Board(new int[][]{{8, 1, 3}, {4, 2, 0}, {7, 6, 5}}), it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testNeighbors2() throws Exception {
        Board testedBoard2 = new Board(new int[][]{{8, 0, 3}, {4, 1, 2}, {7, 6, 5}});
        Iterator<Board> it = testedBoard2.neighbors().iterator();
        assertTrue(it.hasNext());
        assertEquals(new Board(new int[][]{{8, 1, 3}, {4, 0, 2}, {7, 6, 5}}), it.next());
        assertTrue(it.hasNext());
        assertEquals(new Board(new int[][]{{0, 8, 3}, {4, 1, 2}, {7, 6, 5}}), it.next());
        assertTrue(it.hasNext());
        assertEquals(new Board(new int[][]{{8, 3, 0}, {4, 1, 2}, {7, 6, 5}}), it.next());
        assertFalse(it.hasNext());
    }

}