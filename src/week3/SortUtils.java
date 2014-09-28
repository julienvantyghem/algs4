
public class SortUtils {

	static <Item extends Comparable<Item>> boolean less(Item[] a, int i, int j){
		return a[i].compareTo(a[j]) < 0;
	}
	
	static void exch(Object[] a , int i , int j){
		Object temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}
	
}
