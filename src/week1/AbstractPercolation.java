
public abstract class AbstractPercolation implements PercolationInterface {
    
    protected final boolean[][] sites;
    protected final int N;
    protected final WeightedQuickUnionUF uf;
    
    public AbstractPercolation(int N) {
        if (N < 1)
            throw new IllegalArgumentException("N must be at least equal to 1");
        this.N = N;
        sites = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sites[i][j] = false;
            }
        }
        uf = defineWeightedQuickUnionFind();
    }
    
    abstract protected WeightedQuickUnionUF defineWeightedQuickUnionFind();
    
    public void open(int i, int j) {
        checkIndices(i, j);
        if (isOpen(i, j))
            return;
        openSite(i, j);
        // Connect cell above
        if (i > 1) {
            connectCellsIfPossible(i, j, i - 1, j);
        }
        // Connect cell below
        if (i < N) {
            connectCellsIfPossible(i, j, i + 1, j);
        }
        // Connect cell left
        if (j > 1) {
            connectCellsIfPossible(i, j, i, j - 1);
        }
        // Connect cell right
        if (j < N) {
            connectCellsIfPossible(i, j, i, j + 1);
        }
    }
    
    protected void connectCellsIfPossible(int i1, int j1, int i2, int j2){
        if (isOpen(i2, j2)) {
            int currentCell = getFlatIndex(i1, j1);
            int appendedCell = getFlatIndex(i2, j2);
            if (!uf.connected(currentCell, appendedCell)) {
                uf.union(currentCell, appendedCell);
            }
        }
    }
    
    private void openSite(int i, int j){
        sites[i - 1][j - 1] = true;
    }
    

    
    protected void checkIndices(int i, int j) {
        if (i < 1 || j < 1 || i > N || j > N) {
            throw new IndexOutOfBoundsException(
                    "i and j must be between 1 and " + N);
        }
    }
    
    public boolean isOpen(int i, int j) {
        checkIndices(i, j);
        return sites[i - 1][j - 1];
    }
    
    protected int getFlatIndex(int i, int j) {
        return (i - 1) * N + (j - 1);
    }
    
    abstract public boolean isFull(int i, int j);
    
    abstract public boolean percolates();
    

}
