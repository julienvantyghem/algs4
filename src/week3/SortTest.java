import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SortTest {
	
	Sort<Integer> sort = new TopDownMergesort<>();

	@Test
	public void testSort() {
		for(int i = 1; i <= 20; i++){
			Integer[] array = generateRandomArray(i);
			sort.sortArray(array);
			checkArrayOrdered(array);	
		}
	}

	private static Integer[] generateRandomArray(int size) {
		Integer[] array = new Integer[size];
		for (int i = 0; i < size; i++) {
			array[i] = i;
		}
		StdRandom.shuffle(array);
		return array;
	}

	private static void checkArrayOrdered(Integer[] array) {
		for (int i = 0; i < array.length; i++) {
			assertEquals(i, (int) array[i]);
		}
	}

}
