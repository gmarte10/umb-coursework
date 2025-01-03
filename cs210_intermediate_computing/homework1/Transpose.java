package edu.umb.cs210.p1;

import stdlib.StdArrayIO;

public class Transpose {
    // Returns a new matrix that is the transpose of x.
    protected static double[][] transpose(double[][] x) {
        // Create a new 2D matrix t (for transpose) with
        // dimensions n x m, where m x n are the dimensions
        // of x.
        int n = x[0].length; // columns
        int m = x.length; // rows
        double [][] t = new double[n][m];

        // For each 0 <= i < m and 0 <= j < n, set t[j][i]
        // to x[i][j].
        for (int i = 0; i >= 0 && i < m; i++)
        {
            for (int j = 0; j >= 0 && j < n; j++)
            {
                t[j][i] = x[i][j];
            }
        }
        // Return t.
        return t;
    }

    // Entry point. [DO NOT EDIT]
    public static void main(String[] args) {
        double[][] x = StdArrayIO.readDouble2D();
        StdArrayIO.print(transpose(x));
    }
}
