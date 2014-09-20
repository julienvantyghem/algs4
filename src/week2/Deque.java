import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

	private Node<Item> first;
	private Node<Item> last;
	private int size = 0;

	private static class Node<Item> {
		private Item item;
		private Node<Item> previous;
		private Node<Item> next;

		Node(Item item, Node<Item> previous, Node<Item> next) {
			this.item = item;
			this.previous = previous;
			this.next = next;
		}
	}

	public Deque() {
		first = null;
		last = null;
	}

	public boolean isEmpty() {
		return first == null;
	}

	public int size() {
		return size;
	}

	public void addFirst(Item item) {
		if (item == null) {
			throw new NullPointerException();
		}
		if (isEmpty()) {
			buildFirstNode(item);
		} else {
			Node<Item> newFirst = new Node<>(item, null, first);
			first.previous = newFirst;
			first = newFirst;
		}
		size++;
	}

	public void addLast(Item item) {
		if (item == null) {
			throw new NullPointerException();
		}
		if (isEmpty()) {
			buildFirstNode(item);
		} else {
			Node<Item> newLast = new Node<>(item, last, null);
			last.next = newLast;
			last = newLast;
		}
		size++;
	}

	private void buildFirstNode(Item item) {
		Node<Item> node = new Node<>(item, null, null);
		first = node;
		last = node;
	}

	public Item removeFirst() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		Item firstItem = first.item;
		first = first.next;
		if (first != null) {
			first.previous = null;
		} else {
			last = null;
		}
		size--;
		return firstItem;
	}

	public Item removeLast() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		Item lastItem = last.item;
		last = last.previous;
		if (last != null) {
			last.next = null;
		} else {
			first = null;
		}
		size--;
		return lastItem;
	}

	@Override
	public Iterator<Item> iterator() {
		return new Iterator<Item>() {
			Node<Item> current = first;

			@Override
			public boolean hasNext() {
				return current != null;
			}

			@Override
			public Item next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				}
				Item item = current.item;
				current = current.next;
				return item;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	public static void main(String[] args) {
	}

}
