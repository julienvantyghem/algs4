import java.util.Iterator;
import java.util.NoSuchElementException;

@SuppressWarnings("unchecked")
public class RandomizedQueue<Item> implements Iterable<Item> {
	private Item[] values;
	private int next;

	public RandomizedQueue() {
		next = 0;
		values = (Item[]) new Object[1];
	}

	public boolean isEmpty() {
		return next == 0;
	}

	public int size() {
		return next;
	}

	public void enqueue(Item item) {
		if (item == null) {
			throw new NullPointerException();
		}
		if (values.length < next + 1) {
			extendValues();
		}
		values[next] = item;
		next++;
	}

	private void extendValues() {
		int newLength = 2 * values.length + 1;
		Item[] newValues = (Item[]) new Object[newLength];
		for (int i = 0; i < size(); i++) {
			newValues[i] = values[i];
		}
		values = newValues;
	}

	public Item dequeue() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		int r = StdRandom.uniform(next);
		Item item = values[r];
		values[r] = values[next - 1];
		values[next - 1] = null;
		next--;
		if(size() < values.length / 4){
			shrinkValues();
		}
		return item;
	}

	private void shrinkValues(){
		int newLength = values.length / 2;
		Item[] newValues = (Item[]) new Object[newLength];
		for(int i=0; i<size(); i++){
			newValues[i] = values[i];
		}
		values = newValues;
	}

	public Item sample() {
		if(isEmpty()){
			throw new NoSuchElementException();
		}
		return values[StdRandom.uniform(next)];
	}

	public Iterator<Item> iterator() {
		return new Iterator<Item>() {
			Item[] shuffledValues = makeShuffledCopy(values);
			int current = 0;
			@Override
			public boolean hasNext() {
				return current < shuffledValues.length && shuffledValues[current] != null;
			}

			@Override
			public Item next() {
				if(!hasNext()){
					throw new NoSuchElementException();
				}
				Item item = shuffledValues[current];
				current++;
				return item;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
	
    private Item[] makeShuffledCopy(Item[] a) {
        int N = size();
        Item[] shuffledCopy  = (Item[]) new Object[N];
        for(int i=0; i<N; i++){
        	shuffledCopy[i] = a[i];
        }
        for (int i = 0; i < N; i++) {
            int r = i + StdRandom.uniform(N-i);     // between i and N-1
            Item temp = shuffledCopy[i];
            shuffledCopy[i] = shuffledCopy[r];
            shuffledCopy[r] = temp;
        }
        return shuffledCopy;
    }

	public static void main(String[] args) {
	}
}
