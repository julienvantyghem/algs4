import java.util.ArrayList;
import java.util.Collection;

public class Board {

    private final int N;
    private final int[][] blocks;

    public Board(int[][] blocks) {
        N = blocks.length;
        this.blocks = getBlocksCopy(blocks);
    }

    public int dimension() {
        return N;
    }

    public int hamming() {
        int score = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (blocks[i][j] != 0 && blocks[i][j] != getGoalElement(i, j)) score++;
            }
        }
        return score;
    }

    public int manhattan() {
        int score = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int e = blocks[i][j];
                if (e != 0) {
                    score += Math.abs(i - getGoalRow(e)) + Math.abs(j - getGoalColumn(e));
                }
            }
        }
        return score;
    }

    private int getGoalElement(int i, int j) {
        if (i == N - 1 && j == N - 1) return 0;
        return 1 + N * i + j;
    }

    private int getGoalRow(int e) {
        if (e == 0) return N - 1;
        return (e - 1) / N;
    }

    private int getGoalColumn(int e) {
        if (e == 0) return N - 1;
        return (e - 1) % N;
    }
    // sum of Manhattan distances between blocks and goal

    public boolean isGoal() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (blocks[i][j] != getGoalElement(i, j)) return false;
            }
        }
        return true;
    }

    public Board twin() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N - 1; j++) {
                if (blocks[i][j] != 0 && blocks[i][j + 1] != 0) {
                    return newBoardWithSwappedElements(i, j, i, j + 1);
                }
            }
        }
        return null;
    }

    private Board newBoardWithSwappedElements(int i1, int j1, int i2, int j2) {
        int[][] newBlocks = getBlocksCopy();
        newBlocks[i1][j1] = blocks[i2][j2];
        newBlocks[i2][j2] = blocks[i1][j1];
        return new Board(newBlocks);
    }

    private int[][] getBlocksCopy() {
        return getBlocksCopy(this.blocks);
    }

    private int[][] getBlocksCopy(int[][] blocks) {
        int N = blocks.length;
        int[][] newBlocks = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                newBlocks[i][j] = blocks[i][j];
            }
        }
        return newBlocks;
    }

    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (!(y instanceof Board)) return false;
        Board that = (Board) y;
        if (that.N != this.N) return false;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (that.blocks[i][j] != this.blocks[i][j]) return false;
            }
        }
        return true;
    }

    public Iterable<Board> neighbors() {
        Collection<Board> neighbors = new ArrayList<>();
        int i0 = getGoalRow(findZero());
        int j0 = getGoalColumn(findZero());
        if (i0 > 0) {
            neighbors.add(newBoardWithSwappedElements(i0, j0, i0 - 1, j0));
        }
        if (i0 < N - 1) {
            neighbors.add(newBoardWithSwappedElements(i0, j0, i0 + 1, j0));
        }
        if (j0 > 0) {
            neighbors.add(newBoardWithSwappedElements(i0, j0, i0, j0 - 1));
        }
        if (j0 < N - 1) {
            neighbors.add(newBoardWithSwappedElements(i0, j0, i0, j0 + 1));
        }
        return neighbors;
    }

    private int findZero() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (blocks[i][j] == 0) return getGoalElement(i, j);
            }
        }
        return -1;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sb.append(String.format("%2d ", blocks[i][j]));
            }
            sb.append("\n");
        }
        return sb.toString();
    }


    public static void main(String[] args) {
    }
}
