package edu.umb.cs210.p4;

import dsa.LinkedQueue;
// import dsa.Queue;
import stdlib.In;
import stdlib.StdOut;


// Models a board in the 8-puzzle game or its generalization.
public class Board {
    private final int[][] tiles;    // tiles in the board
    private int N;                  // board size
    private int hamming;            // hamming distance to goal
    private int manhattan;          // manhattan distance to goal
    
    // Construct a board from an N-by-N array of tiles, where 
    // tiles[i][j] = tile at row i and column j, and 0 represents the blank 
    // square.
    public Board(int[][] tiles) {
        this.tiles = tiles;
        // initialized to number of rows in tiles
        this.N = tiles[0].length;

        // stores the correct position
        int cPosition;
        // stores hamming distance
        int hammingCount = 0;
        // stores individual manhattan distance
        int manhattanD = 0;
        // correct row position
        int pRow = 0;
        // correct column position
        int pCol = 0;
        // manhattan distance total
        int manhattanDT = 0;
        // stores number value of the tile
        int value = 0;

        // gets manhattan and hamming distance
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                // stores what the current correct position should be
                cPosition = i * N + (j + 1);
                // stores the current number value
                value = tiles[i][j];
                // does not count 0
                if (value == 0)
                {
                    continue;
                }
                // checks if tiles are not in correct position
                if (value != cPosition)
                {
                    hammingCount++;
                    pRow = (value - 1) / N;
                    pCol = (value - 1) % N;
                    // manhattan formula
                    manhattanD = Math.abs(i - pRow) + Math.abs(j - pCol);
                    manhattanDT = manhattanDT + manhattanD;
                }
            }
        }
        // stores calculations in variables
        hamming = hammingCount;
        manhattan = manhattanDT;
    }
    // Tile at row i and column j.
    public int tileAt(int i, int j) {
        return tiles[i][j];
    }
    
    // Size of this board.
    public int size() {
        return N;
    }

    // Number of tiles out of place.
    public int hamming() {
        return hamming;
    }

    // Sum of Manhattan distances between tiles and goal.
    public int manhattan() {
        return manhattan;
    }

    // Is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                // hamming used to check if there are any tiles out of place
                if (hamming == 0) {
                    // if none out of place = goal board
                    return true;
                }
            }
        }
        return false;
    }

    // Is this board solvable?
    public boolean isSolvable() {
        // checks if the board is an odd size
        if (N % 2 != 0)
        {
            // checks if inversions is an even number
            if (this.inversions() % 2 == 0)
            {
                return true;
            }
        }
        // checks if board is an even size
        if (N % 2 == 0)
        {
            // stores the sum
            int sum = 0;
            // stores the blank row number
            int blankRow = (this.blankPos() - 1) / N;
            // gets the total sum
            sum = this.inversions() + blankRow;

            // checks if the sum is even
            if (sum % 2 == 0)
            {
                return false;
            }
            // checks if sum is odd
            if (sum % 2 != 0)
            {
                return true;
            }
        }
        return false;

    }

    // Does this board equal that?
    public boolean equals(Object that) {
        // checks if this and that are board are the same
        if (this == that)
        {
            return true;
        }
        // checks if that board is null
        if (that == null)
        {
            return false;
        }
        // checks if this and that are not the same class
        if (this.getClass() != that.getClass())
        {
            return false;
        }
        // casts that to board
        Board thatBoard = (Board) that;
        // checks if they are different board sizes
        if (this.tiles[0].length != thatBoard.tiles[0].length ||
                this.tiles.length != thatBoard.tiles.length)
        {
            return false;
        }

        // checks if tiles are of this and that are not the same
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                // stores tile values
                int thisTile = this.tiles[i][j];
                int thatTile = thatBoard.tiles[i][j];

                // checks if values are not equal
                if (thisTile != thatTile)
                {
                    return false;
                }
            }
        }
        return true;
    }

    // All neighboring boards.
    public Iterable<Board> neighbors() {
        // create queue of Board objects
        LinkedQueue<Board> q = new LinkedQueue<Board>();

        // find blank position in rank
        int blankP = this.blankPos();
        // stores blank tile row
        int blankRow = (blankP - 1) / N;
        // stores blank tile column
        int blankCol = (blankP - 1) % N;

        // North neighbor
        if (blankRow - 1 >= 0)
        {
            // clone of this board
            int [][] clone = this.cloneTiles();
            // temp to store zero
            int temp = clone[blankRow][blankCol];
            // zero = neighbor
            clone[blankRow][blankCol] = clone[blankRow - 1][blankCol];
            // neighbor = zero
            clone[blankRow - 1][blankCol] = temp;
            // make board object from clone
            Board north = new Board(clone);
            // enqueue into q
            q.enqueue(north);
        }

        // South Neighbor
        if (blankRow + 1 < N)
        {
            // clone of this board
            int [][] clone = this.cloneTiles();
            // temp to store zero
            int temp = clone[blankRow][blankCol];
            // zero = neighbor
            clone[blankRow][blankCol] = clone[blankRow + 1][blankCol];
            // neighbor = zero
            clone[blankRow + 1][blankCol] = temp;
            // make board object from clone
            Board south = new Board(clone);
            // enqueue into q
            q.enqueue(south);
        }

        // East Neighbor
        if (blankCol + 1 < N)
        {
            // clone of this board
            int [][] clone = this.cloneTiles();
            // temp to store zero
            int temp = clone[blankRow][blankCol];
            // zero = neighbor
            clone[blankRow][blankCol] = clone[blankRow][blankCol + 1];
            // neighbor = zero
            clone[blankRow][blankCol + 1] = temp;
            // make board object from clone
            Board east = new Board(clone);
            // enqueue into q
            q.enqueue(east);
        }

        // West Neighbor
        if (blankCol - 1 >= 0)
        {
            // clone of this board
            int [][] clone = this.cloneTiles();
            // temp to store zero
            int temp = clone[blankRow][blankCol];
            // zero = neighbor
            clone[blankRow][blankCol] = clone[blankRow][blankCol - 1];
            // neighbor = zero
            clone[blankRow][blankCol - 1] = temp;
            // make board object from clone
            Board west = new Board(clone);
            // enqueue into q
            q.enqueue(west);
        }
        return q;
    }

    // String representation of this board.
    public String toString() {
        StringBuilder s = new StringBuilder(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d", tiles[i][j]));
                if (j < N - 1) {
                    s.append(" ");
                }
            }
            if (i < N - 1) {
                s.append("\n");
            }
        }
        return s.toString();
    }

    // Helper method that returns the position (in row-major order) of the 
    // blank (zero) tile.
    private int blankPos() {
        // stores blank tile position
        int position = 0;
        // find the tile that is blank
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                // if the tile is 0, it is blank
                if (tiles[i][j] == 0)
                {
                    // returns the position using formula
                    position = i * N + (j + 1);
                    return position;
                }
            }
        }
        // used to satisfy return
        return position;
    }

    // Helper method that returns the number of inversions.
    private int inversions() {
        // stores the total number of inversions
        int inversions = 0;

        // creates a 1d array to store all 2d array values/tiles
        int[] temp1d = new int[N * N];
        // index counter for temp1d
        int r = 0;

        // store 2d values/tiles into 1d
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                temp1d[r] = tiles[i][j];
                r++;
            }
        }
        // go through 1d array and find inversions
        for (int i = 0; i < N * N; i++)
        {
            for (int j = i + 1; j < N * N; j++)
            {
                // used to not count blank tile
                if (temp1d[i] == 0 || temp1d[j] == 0)
                {
                    continue;
                }
                // checks if the number ahead is smaller than current
                if (temp1d[j] < temp1d[i])
                {
                    // increases inversion
                    inversions++;
                }
            }
        }
        return inversions;
    }

    // Helper method that clones the tiles[][] array in this board and 
    // returns it.
    private int[][] cloneTiles() {
        // creates an empty 2d array to be used as clone
        int[][] tilesClone = new int[N][N];

        // copies tiles array into clone
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                tilesClone[i][j] = tiles[i][j];
            }
        }
        // returns the clone
        return tilesClone;
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] tiles = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        Board board = new Board(tiles);
        StdOut.println(board.hamming());
        StdOut.println(board.manhattan());
        StdOut.println(board.isGoal());
        StdOut.println(board.isSolvable());
        for (Board neighbor : board.neighbors()) {
            StdOut.println(neighbor);
        }
    }
}
