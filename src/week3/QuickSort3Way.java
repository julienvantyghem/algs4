

public class QuickSort3Way<Item extends Comparable<Item>> implements Sort<Item> {
	
	private void sort(Item[] a, int lo, int hi){
		if(hi <= lo) return;
		int lt = lo;
		int i = lo + 1;
		int gt = hi;
		Item v = a[lt];
		while(i <= gt){
			int cmp = a[i].compareTo(v);
			if(cmp < 0 ){
				SortUtils.exch(a, lt ++, i ++);
			}else if(cmp > 0){
				SortUtils.exch(a, i, gt--);
			}else{
				i++;
			}
		}
		sort(a, lo, lt - 1);
		sort(a, i, hi);
	}

	@Override
	public void sortArray(Item[] a) {
		sort(a, 0, a.length-1);
	}
	
}
