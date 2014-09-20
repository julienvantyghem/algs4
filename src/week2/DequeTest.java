import static org.junit.Assert.assertEquals;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

public class DequeTest {

	private QueueInterface<Integer> dq;

	private QueueInterface<Integer> getDequeInstance() {
		return new LinkedDeque<>();
	}

	@Before
	public void setUp() {
		dq = getDequeInstance();
	}

	@Test(expected = NullPointerException.class)
	public void addFirstNullTest() {
		dq.addFirst(null);
	}

	@Test(expected = NullPointerException.class)
	public void addLastNullTest() {
		dq.addLast(null);
	}

	@Test(expected = NoSuchElementException.class)
	public void removeFirstFromEmptyTest() {
		dq.removeFirst();
	}

	@Test(expected = NoSuchElementException.class)
	public void removeLastFromEmptyTest() {
		dq.removeLast();
	}

	@Test(expected = UnsupportedOperationException.class)
	public void removeFromIteratorTest() {
		dq.iterator().remove();
	}

	@Test(expected = NoSuchElementException.class)
	public void nextEmptyIteratorTest() {
		dq.iterator().next();
	}

	@Test
	public void addAndRemoveFirstTest() {
		dq.addFirst(1);
		dq.addFirst(2);
		assertEquals(2, (int) dq.removeFirst());
	}

	@Test
	public void addAndRemoveLastTest() {
		dq.addLast(1);
		dq.addLast(2);
		assertEquals(2, (int) dq.removeLast());
	}

	@Test
	public void addFirstRemoveLastTest() {
		dq.addFirst(1);
		dq.addFirst(2);
		assertEquals(1, (int) dq.removeLast());
	}

	@Test
	public void addLastRemoveFirstTest() {
		dq.addLast(1);
		dq.addLast(2);
		assertEquals(1, (int) dq.removeFirst());
	}

	@Test
	public void testSize() {
		dq.addFirst(1);
		assertEquals(1, dq.size());
		dq.addLast(1);
		assertEquals(2, dq.size());
		dq.removeFirst();
		assertEquals(1, dq.size());
		dq.removeLast();
		assertEquals(0, dq.size());
	}

	@Test
	public void testIsEmpty() {
		assert (dq.isEmpty());
		dq.addFirst(1);
		assert (!dq.isEmpty());
		dq.removeFirst();
		assert (dq.isEmpty());
		dq.addLast(1);
		assert (!dq.isEmpty());
		dq.removeLast();
		assert (dq.isEmpty());
	}

	@Test
	public void testIteratorAddFirst() {
		dq.addFirst(1);
		dq.addFirst(2);
		dq.addFirst(3);
		Iterator<Integer> it = dq.iterator();
		assertEquals(3, (int) it.next());
		assertEquals(2, (int) it.next());
		assertEquals(1, (int) it.next());
	}
	
	@Test
	public void testIteratorAddLast() {
		dq.addLast(1);
		dq.addLast(2);
		dq.addLast(3);
		Iterator<Integer> it = dq.iterator();
		assertEquals(1, (int) it.next());
		assertEquals(2, (int) it.next());
		assertEquals(3, (int) it.next());
	}

	@Test
	public void constantTimeTest() {
		LinkedDeque<Integer> bigDq = new LinkedDeque<>();
		int bigSize = 1000000;
		for (int i = bigSize; i >= 1; i--) {
			bigDq.addFirst(i);
		}
		Stopwatch bigSW = new Stopwatch();
		bigDq.addFirst(0);
		bigDq.removeFirst();
		bigDq.addLast(0);
		bigDq.removeLast();
		bigDq.size();
		bigDq.isEmpty();
		double bigTime = bigSW.elapsedTime();

		LinkedDeque<Integer> smallDq = new LinkedDeque<>();
		for (int i = 10; i >= 1; i--) {
			smallDq.addFirst(i);
		}
		Stopwatch smallSW = new Stopwatch();
		smallDq.addFirst(0);
		smallDq.removeFirst();
		smallDq.addLast(0);
		smallDq.removeLast();
		smallDq.size();
		smallDq.isEmpty();
		double smallTime = smallSW.elapsedTime();

		assert (bigTime == smallTime);
	}

}
