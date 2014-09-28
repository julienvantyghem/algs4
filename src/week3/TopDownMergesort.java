import java.util.Arrays;

public class TopDownMergesort<Item extends Comparable<Item>> implements Sort<Item>{

	private int mergeCallCount;

	private void merge(Item[] a, Item[] aux, int lo, int mid,
			int hi) {
		for (int i = lo; i <= hi; i++) {
			aux[i] = a[i];
		}
		int i = lo;
		int j = mid + 1;
		for (int k = lo; k <= hi; k++) {
			if (i > mid)
				a[k] = aux[j++];
			else if (j > hi)
				a[k] = aux[i++];
			else if (aux[j].compareTo(aux[i]) < 0)
				a[k] = aux[j++];
			else
				a[k] = aux[i++];
		}
		System.out.println("Array after "+ ++mergeCallCount + " merges : " + Arrays.toString(a));
	}

	private void sort(Item[] a, Item[] aux, int lo, int hi) {
		if (hi <= lo)
			return;
		int mid = lo + (hi - lo) / 2;
		sort(a, aux, lo, mid);
		sort(a, aux, mid + 1, hi);
		merge(a, aux, lo, mid, hi);
	}

	@Override
	public void sortArray(Item[] a) {
		mergeCallCount = 0;
		Item[] aux = (Item[]) new Comparable[a.length];
		sort(a, aux, 0, a.length - 1);
	}

	public static void main(String[] args) {
		Integer[] array = { 35, 22, 42, 70, 66, 26, 43, 23, 56, 62, 80, 60 };
		new TopDownMergesort().sortArray(array);
	}

}
