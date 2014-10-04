import org.junit.Test;

import static org.junit.Assert.*;

public class MaxPQTest {

    @Test
    public void testInsert() {
        Integer[] a = new Integer[10];
        for(int i = 0; i < 10; i++){
            a[i] = i;
        }
        StdRandom.shuffle(a);
        MaxPQ<Integer> pq = new MaxPQ<>();
        for(int i = 0; i < 10; i++){
            pq.insert(a[i]);
        }
        for(int i = 9; i>=0; i--){
            assertEquals(i, (int) pq.delMax());
        }
    }

    @Test
    public void testInitialize() {
        Integer[] a = new Integer[10];
        for(int i = 0; i < 10; i++){
            a[i] = i;
        }
        StdRandom.shuffle(a);
        MaxPQ<Integer> pq = new MaxPQ<>(a);
        for(int i = 9; i>=0; i--){
            assertEquals(i, (int) pq.delMax());
        }
    }


}