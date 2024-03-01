package edu.umb.cs210.p1;

import stdlib.StdOut;
import stdlib.StdRandom;
import stdlib.StdStats;

// Estimates percolation threshold for an N-by-N percolation system.
public class PercolationStats {
    // number of independent experiments
    int T;
    // percolation thresholds
    double[] p;

    // Performs T independent experiments (Monte Carlo simulations) on an
    // N-by-N grid.
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0)
        {
            throw new IllegalArgumentException("N or T is out of Range");
        }
        this.T = T;
        p = new double[T];
        for (int i = 0; i < T; i++)
        {
            Percolation grid = new Percolation(N);
            while (!grid.percolates())
            {
                grid.open(StdRandom.uniform(0, N), StdRandom.uniform(0, N));
            }
            p[i] = (((double) grid.numberOfOpenSites()) /
                    ((double) (Math.pow(N, 2))));
        }
    }

    // Returns sample mean of percolation threshold.
    public double mean() {
        return (double) StdStats.mean(p);
    }

    // Returns sample standard deviation of percolation threshold.
    public double stddev() {
        return (double) StdStats.stddev(p);
    }

    // Returns low endpoint of the 95% confidence interval.
    public double confidenceLow() {
        return ((double) (mean()) - (((double) 1.96 * (double) stddev()))
                / ((double) (Math.sqrt((double) (T)))));
    }

    // Returns high endpoint of the 95% confidence interval.
    public double confidenceHigh() {
        return (((double) (mean())) + (((double) 1.96 *
                (double) stddev()) /
                ((double) (Math.sqrt((double) (T))))));
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(N, T);
        StdOut.printf("mean           = %f\n", stats.mean());
        StdOut.printf("stddev         = %f\n", stats.stddev());
        StdOut.printf("confidenceLow  = %f\n", stats.confidenceLow());
        StdOut.printf("confidenceHigh = %f\n", stats.confidenceHigh());
    }
}

