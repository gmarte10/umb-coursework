package edu.umb.cs210.p4;

import stdlib.StdOut;

public class Ramanujan1 {
    public static void main(String[] args) {
        // gets the input
        int N = Integer.parseInt(args[0]);
        // 0 < a <= cube root(N)
        for (int a = 1; a * a * a <= N; a++)
        {
            // a < b <= cube root(N - a^3)
            for (int b = a + 1; b * b * b <= N - a * a * a; b++)
            {
                // a < c < cube root(N)
                for (int c = a + 1; c * c * c <= N; c++)
                {
                    // c < d <= cube root(N - c^3)
                    for (int d = c + 1; d * d * d <= N - c * c * c; d++)
                    {
                        // checks if a^3 + b^3 = c^3 + d^3
                        if (a * a * a + b * b * b == c * c * c + d * d * d)
                        {
                            // integer <= N, expressed as sum of 2 cubes
                            // in 2 ways
                            int integerLessN = a * a * a + b * b * b;
                            StdOut.printf("%d = %d^3 + %d^3 = %d^3 + " +
                                    "%d^3\n", integerLessN, a, b, c, d);
                        }
                    }
                }
            }

        }

    }
}
