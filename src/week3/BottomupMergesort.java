import java.util.Arrays;

public class BottomupMergesort implements Sort {

	private int mergeCallCount;

	private void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
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
		System.out.println("Array after " + ++mergeCallCount + " merges (merge size = "+(hi-lo+1)+") : "
				+ Arrays.toString(a));
	}

	@Override
	public void sortArray(Comparable[] a) {
		mergeCallCount = 0;
		int N = a.length;
		Comparable[] aux = new Comparable[N];
		for(int sz = 1; sz < N; sz = sz + sz){
			for(int lo = 0; lo < N - sz; lo= lo + sz + sz){
				merge(a, aux, lo, lo + sz - 1, Math.min(lo + sz + sz - 1, N-1));
			}
		}
	}
	
	public static void main(String[] args) {
		Integer[] array = {95,42,27,99,58,70,16,46,78,62};
		new BottomupMergesort().sortArray(array);
	}

}
