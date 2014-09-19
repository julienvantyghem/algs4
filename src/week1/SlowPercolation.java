public class SlowPercolation extends AbstractPercolation {

    public SlowPercolation(int N) {
        super(N);
    }

    @Override
    protected WeightedQuickUnionUF defineWeightedQuickUnionFind() {
        return new WeightedQuickUnionUF(N * N);
    }

    @Override
    public boolean isFull(int i, int j) {
        checkIndices(i, j);
        if (!isOpen(i, j))
            return false;
        int currentCell = getFlatIndex(i, j);
        for (int k = 1; k <= N; k++) {
            int topCell = getFlatIndex(1, k);
            if (uf.connected(topCell, currentCell))
                return true;
        }
        return false;
    }

    @Override
    public boolean percolates() {
        for (int k = 1; k <= N; k++) {
            if (isFull(N, k)) {
                return true;
            }
        }
        return false;
    }
    
    public static void main(String[] args) {
        PercolationInterface perc = new SlowPercolation(5);
        perc.open(1, 1);
        perc.open(2, 1);
        System.out.println(perc.isFull(2, 1));
        System.out.println("Yay");
    }

}
