public class PercolationStats {
    private final int N;
    private final int T;
    private final double[] thresholds;

    public PercolationStats(int N, int T) {
        Stopwatch sw = new Stopwatch();
        this.N = N;
        this.T = T;
        thresholds = new double[T];
        for (int i = 0; i < T; i++) {
            thresholds[i] = runPercolationTest();
        }
        StdOut.println("Execution time = "+sw.elapsedTime()+" ms");
    }

    private double runPercolationTest() {
        
        Percolation p = new Percolation(N);
        int c = 0;
        while (!p.percolates()) {
            int i = StdRandom.uniform(N) + 1;
            int j = StdRandom.uniform(N) + 1;
            if (!p.isOpen(i, j)) {
                p.open(i, j);
                c++;
            }
        }
        return ((double) c)  / (N * N);

    }

    public double mean() {
        return StdStats.mean(thresholds);
    }

    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(T);
    }

    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(T);
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        if (N < 1 || T < 1) {
            throw new IllegalArgumentException(
                    "Please provide strictly positive values for N and T");
        }
        PercolationStats ps = new PercolationStats(N, T);
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = " + ps.confidenceLo() + " ,"
                + ps.confidenceHi());
    }
}
