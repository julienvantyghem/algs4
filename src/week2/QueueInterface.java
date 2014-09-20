import java.util.Iterator;

public interface QueueInterface<Item> extends Iterable<Item> {
	   public boolean isEmpty();                 // is the deque empty?
	   public int size();                        // return the number of items on the deque
	   public void addFirst(Item item);          // insert the item at the front
	   public void addLast(Item item);           // insert the item at the end
	   public Item removeFirst();                // delete and return the item at the front
	   public Item removeLast();                 // delete and return the item at the end
	   public Iterator<Item> iterator();         // return an iterator over items in order from front to end
}
