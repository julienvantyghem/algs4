import java.util.Iterator;
import java.util.NoSuchElementException;

@SuppressWarnings("unchecked")
public class ArrayDequeue<Item> implements QueueInterface<Item> {

	private Item[] values;
	private int previous;
	private int next;

	public ArrayDequeue() {
		values = (Item[]) new Object[0];
	}

	public boolean isEmpty() {
		return values.length == 0;
	}

	public int size() {
		return (next - previous) - 1;
	}

	public void addFirst(Item item) {
		if (item == null) {
			throw new NullPointerException("Cannot add null Item");
		}
		if (isEmpty()) {
			addFirstItem(item);
		} else {
			if (previous < 0) {
				extendValues();
			}
			values[previous] = item;
			previous--;
		}
	}

	@Override
	public void addLast(Item item) {
		if (item == null) {
			throw new NullPointerException("Cannot add null Item");
		}
		if (isEmpty()) {
			addFirstItem(item);
		} else {
			if (next == values.length) {
				extendValues();
			}
			values[next] = item;
			next++;
		}
	}

	private void addFirstItem(Item item) {
		values = (Item[]) new Object[] { item };
		previous = -1;
		next = 1;
	}

	private void extendValues() {
		int newLength = 2 * values.length + 1;
		int oldFirst = previous + 1;
		int newFirst = newLength / 2;
		Item[] newValues = (Item[]) new Object[newLength];
		for (int i = 0; i < size(); i++) {
			newValues[newFirst + i] = values[oldFirst + i];
		}
		values = newValues;
		next = newFirst + size();
		previous = newFirst - 1;
	}

	@Override
	public Item removeFirst() {
		if (isEmpty()) {
			throw new NoSuchElementException("Deque is empty");
		}
		int first = previous + 1;
		Item item = values[first];
		values[first] = null;
		previous = first;
		if (previous + 1 == next) {
			resetValues();
		} else if (size() < values.length / 4) {
			shrinkValues();
		}
		return item;
	}

	@Override
	public Item removeLast() {
		if (isEmpty()) {
			throw new NoSuchElementException("Deque is empty");
		}
		int last = next - 1;
		Item item = values[last];
		values[last] = null;
		previous = last;
		if (previous + 1 == next) {
			resetValues();
		} else if (size() < values.length / 4) {
			shrinkValues();
		}
		return item;
	}

	private void resetValues() {
		values = (Item[]) new Object[0];
	}

	private void shrinkValues(){
		int newLength = values.length / 2;
		int oldFirst = previous + 1;
		int newFirst = newLength / 2;
		Item[] newValues = (Item[]) new Object[newLength];
		for(int i = 0; i < size(); i++){
			newValues[newFirst + i] = values[oldFirst + i];
		}
		values = newValues;
		next = newFirst + size();
		previous = newFirst - 1;
	}

	@Override
	public Iterator<Item> iterator() {
		return new Iterator<Item>() {
			int current = previous + 1;

			@Override
			public boolean hasNext() {
				return current < values.length && current <= next;
			}

			@Override
			public Item next() {
				if(!hasNext()){
					throw new NoSuchElementException();
				}
				Item nextItem = values[current];
				current++;
				return nextItem;
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
