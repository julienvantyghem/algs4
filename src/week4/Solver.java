public class Solver {
    private static class Node implements Comparable<Node> {
        Board board;
        Node parent;
        int moves;

        private Node(Board board, Node parent, int moves) {
            this.board = board;
            this.parent = parent;
            this.moves = moves;
        }

        private int getPriority() {
            return moves + board.manhattan();
        }

        public int compareTo(Node that) {
            if (that == null || that.board == null) return 1;
            return ((Integer) this.getPriority()).compareTo(that.getPriority());
        }
    }

    private Node solution;
    private Board initial;

    public Solver(Board initial) {
        this.initial = initial;
        MinPQ<Node> pq = new MinPQ<>();
        pq.insert(new Node(initial, null, 0));
        pq.insert(new Node(initial.twin(), null, 0));
        solution = pq.delMin();
        while (!solution.board.isGoal()) {
            for (Board board : solution.board.neighbors()) {
                //Do not reinsert previously inserted board (no one likes to repeat oneself)
                if (solution.parent != null && board.equals(solution.parent.board)) continue;
                Node n = new Node(board, solution, solution.moves + 1);
                pq.insert(n);
            }
            solution = pq.delMin();
        }
    }

    public boolean isSolvable() {
        return getSolution().iterator().next().equals(initial);
    }

    public int moves() {
        return isSolvable() ? solution.moves : -1;
    }

    public Iterable<Board> solution() {
        return isSolvable() ? getSolution() : null;
    }

    private Iterable<Board> getSolution() {
        Stack<Board> s = new Stack<>();
        Node current = solution;
        while (current != null) {
            s.push(current.board);
            current = current.parent;
        }
        return s;
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
