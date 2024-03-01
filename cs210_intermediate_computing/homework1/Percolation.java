package edu.umb.cs210.p1;

import dsa.WeightedQuickUnionUF;
import stdlib.In;
import stdlib.StdOut;

// Models an N-by-N percolation system.
public class Percolation {
    // int N
    int N;
    // grid
    boolean[][] open;
    // # of open sites
    int openSites;
    // uf for scanning grid
    WeightedQuickUnionUF uf;
    // second uf for backwash
    WeightedQuickUnionUF uf2;

    // Creates an N-by-N grid, with all sites blocked.
    public Percolation(int N) {
        if (N <= 0)
        {
            throw new IllegalArgumentException("N is out of Range");
        }
        this.N = N;
        open = new boolean[N][N];
        openSites = 0;
        uf = new WeightedQuickUnionUF(N * N + 2);
        uf2 = new WeightedQuickUnionUF(N * N + 2);

        for (int i = 0; i <= N - 1; i++) // connect top to source
        {
            uf.union(0, encode(0, i));
            uf2.union(0, encode(0, i));
        }

        for (int i = 0; i <= N - 1; i++) // connect bottom to sink
        {
            uf.union(N * N + 1, encode(N - 1, i));
        }
    }

    // Opens site (i, j) if it is not open already.
    public void open(int i, int j) {
        if (i < 0 && i >= N && j < 0 && j >= N)
        {
            throw new IllegalArgumentException("i or j is out of range");
        }

        if (!open[i][j])
        {
            open[i][j] = true;
            openSites += 1;
        }

        if (i > 0 && open[i - 1][j]) // North
        {
            uf.union(encode(i, j), encode(i - 1, j));
            uf2.union(encode(i, j), encode(i - 1, j));
        }

        if (i < N - 1 && open[i + 1][j]) // South
        {
            uf.union(encode(i, j), encode(i + 1, j));
            uf2.union(encode(i, j), encode(i + 1, j));
        }

        if (j < N - 1 && open[i][j + 1]) // East
        {
            uf.union(encode(i, j), encode(i, j + 1));
            uf2.union(encode(i, j), encode(i, j + 1));
        }

        if (j > 0 && open[i][j - 1]) // West
        {
            uf.union(encode(i, j), encode(i, j - 1));
            uf2.union(encode(i, j), encode(i, j - 1));
        }
    }

    // Checks if site (i, j) is open.
    public boolean isOpen(int i, int j) {
        if ((i < 0 && i > N) && (j < 0 && j > N))
        {
            throw new IllegalArgumentException("i or j is out of range.");
        }

        if (open[i][j])
        {
            return true;
        }
        return false;
    }

    // Checks if site (i, j) is full.
    public boolean isFull(int i, int j) {
        for (int k = 0; k < N; k++)
            if (isOpen(i, j) && uf2.connected(encode(0, k), encode(i, j)))
            {
                return true;
            }
        return false;
    }

    // Returns the number of open sites.
    public int numberOfOpenSites() {
        return openSites;
    }

    // Checks if the system percolates.
    public boolean percolates() {
        if (uf.connected(0, N * N + 1))
        {
            return true;
        }
        return false;
    }

    // Returns an integer ID (1...N) for site (i, j).
    protected int encode(int i, int j) {
        return i * N + j + 1;
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Percolation perc = new Percolation(N);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
        }
        StdOut.println(perc.numberOfOpenSites() + " open sites");
        if (perc.percolates()) {
            StdOut.println("percolates");
        }
        else {
            StdOut.println("does not percolate");
        }

        // Check if site (i, j) optionally specified on the command line
        // is full.
        if (args.length == 3) {
            int i = Integer.parseInt(args[1]);
            int j = Integer.parseInt(args[2]);
            StdOut.println(perc.isFull(i, j));
        }
    }
}
