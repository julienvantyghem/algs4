import java.util.Arrays;

public class MaxPQ<Key extends Comparable<Key>> {

    private Key[] a;
    //N = index of last element (1 => # elements)
    private int N;

    public MaxPQ(){
        a = (Key[]) new Comparable[10];
        N = 0;
    }

    public MaxPQ(Key[] a){
        N = a.length;
        this.a = (Key[]) new Comparable[N + 1];
        for(int i=1; i<=N; i++){
            this.a[i] = a[i-1];
        }
        heapify();
    }

    private void heapify(){
        for(int i = N / 2; i >= 1; i--){
            sink(i);
        }
    }

    public void insert(Key v){
        if(N + 1 == a.length){
            extendArray();
        }
        a[++N] = v;
        swim(N);
    }

    private void extendArray(){
        Key[] extendedArray = (Key[]) new Comparable[a.length * 2];
        for(int i = 1; i <= N; i++){
            extendedArray[i] = a[i];
        }
        a = extendedArray;
    }

    private void swim(int i){
        while(i >= 2 && more(i, i / 2)){
            exch(i, i/2);
            i = i / 2;
        }
    }

    private boolean less(int i, int j){
        return a[i].compareTo(a[j]) < 0;
    }

    private boolean more(int i, int j){
        return a[i].compareTo(a[j]) > 0;
    }

    private void exch(int i, int j){
        Key temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    private void sink(int i){
        if(i * 2 <= N){
            int max = more(i, i * 2) ? i : i * 2;
            if(i * 2 + 1 <= N){
                max = more(max, i * 2 + 1) ? max : i * 2 + 1;
            }
            if(max != i){
                exch(i, max);
                sink(max);
            }
        }
    }

    public Key delMax(){
        Key max = a[1];
        exch(1, N--);
        sink(1);
        return max;
    }

    public boolean isEmpty(){
        return N == 0;
    }

    public Key max(){
        return a[1];
    }

    public int size(){
        return N;
    }

    void showArray(){
        System.out.println(Arrays.toString(Arrays.copyOfRange(a, 1, N + 1)));
    }

    public static void main(String[] args) {
        Integer[] a = {94,90,91,76,82,57,40,37,60,67};
        MaxPQ<Integer> pq = new MaxPQ<>(a);
        pq.insert(93);
        pq.insert(17);
        pq.insert(79);
        pq.showArray();
        pq.heapify();
        pq.showArray();

        a = new Integer[] {99,57,88,39,46,27,50,20,31,34};
        pq = new MaxPQ<>(a);
        pq.delMax();
        pq.delMax();
        pq.delMax();
        pq.showArray();
        pq.heapify();
        pq.showArray();
    }

}
