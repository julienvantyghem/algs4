public class HopefullyFasterPercolation extends AbstractPercolation {

    final int topRowGroup;
    final int bottomRowGroup;

    public HopefullyFasterPercolation(int N) {
        super(N);
        // Second-to last element regroups top rows, last element regroups all
        // bottom rows
        topRowGroup = N * N;
        bottomRowGroup = N * N + 1;
    }

    @Override
    protected WeightedQuickUnionUF defineWeightedQuickUnionFind() {
        return new WeightedQuickUnionUF(N * N + 2);
    }

    @Override
    public void open(int i, int j) {
        super.open(i, j);
        if (i == 1) {
            uf.union(getFlatIndex(i, j), topRowGroup);
        }
        if(i == N){
            uf.union(getFlatIndex(i, j), bottomRowGroup);
        }
    }

    @Override
    public boolean isFull(int i, int j) {
        return uf.connected(getFlatIndex(i, j), topRowGroup);
    }

    @Override
    public boolean percolates() {
        return uf.connected(topRowGroup, bottomRowGroup);
    }

}
