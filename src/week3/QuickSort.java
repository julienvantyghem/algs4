
public class QuickSort<Item extends Comparable<Item>> implements Sort<Item> {

	private void sort(Item[] a, int lo, int hi) {
		if (hi <= lo)
			return;
		int j = partition(a, lo, hi);
		sort(a, lo, j - 1);
		sort(a, j + 1, hi);
	}

	private int partition(Item[] a, int lo, int hi) {
		int i = lo;
		int j = hi + 1;
		while (true) {
			while (SortUtils.less(a, ++i, lo)) {
				if (i >= hi)
					break;
			}
			while (SortUtils.less(a, lo, --j)) {
				if (j <= lo)
					break;
			}
			if (j <= i)
				break;
			SortUtils.exch(a, i, j);
		}
		SortUtils.exch(a, lo, j);
		return j;
	}

	public void sortArray(Item[] a) {
		sort(a, 0, a.length - 1);
	}

}
