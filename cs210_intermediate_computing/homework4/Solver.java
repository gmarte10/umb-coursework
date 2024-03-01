package edu.umb.cs210.p4;

import dsa.LinkedStack;
import dsa.MinPQ;
import stdlib.In;
import stdlib.StdOut;

import java.util.Comparator;

// A solver based on the A* algorithm for the 8-puzzle and its generalizations.
public class Solver {
    // Sequence of boards in a shortest solution
    final LinkedStack<Board> solution;
    // Minimum number of moves to solve initial board
    int moves;

    // Helper search node class.
    private class SearchNode {
        // Board represented by this node
        Board board;
        // Number of moves it took to get to this node from the initial node
        int moves;
        // Previous search node
        SearchNode previous;

        // Construct a new SearchNode
        SearchNode(Board board, int moves, SearchNode previous) {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
        }
    }
     
    // Find a solution to the initial board (using the A* algorithm).
    public Solver(Board initial) {
        // checks corner case if initial = null
        if (initial == null)
        {
            throw new NullPointerException("null board");
        }
        // checks corner case if initial is not solvable
        if (!initial.isSolvable())
        {
            throw new IllegalArgumentException("Unsolvable board");
        }

        // Create a ManhattanOrder comparator for MinPQ to use
        ManhattanOrder manhattan = new ManhattanOrder();
        // Create a MinPQ object using manhattan ordering
        MinPQ<SearchNode> pq = new MinPQ<SearchNode>(manhattan);
        // Initialize solution
        solution = new LinkedStack<Board>();
        // Create initial search node
        SearchNode initialNode = new SearchNode(initial, 0, null);
        // Insert initial search node into pq
        pq.insert(initialNode);

        while (!pq.isEmpty())
        {
            // Remove minimum from pq and store return in node
            SearchNode node = pq.delMin();
            // check if node is the goal board
            if (node.board.isGoal())
            {
                // obtain moves from node
                moves = node.moves;

                // Obtains solution from node by pushing previous until
                // initial board is at the top of LinkedStack, which is
                // the sequence of steps
                // to reach goal board.
                while (node.previous != null)
                {
                    // pushes current node board
                    solution.push(node.board);
                    // makes previous board current board to push
                    node = node.previous;
                }
                // break
                break;
            }
            // iterate over neighbor boards
            for (Board board: node.board.neighbors())
            {
                // prevents checking neighbor is different from previous.board
                // which does not exist in first iteration. The first neighbor
                // is obviously different from null, so you add a new
                // search node
                if (node.previous == null)
                {
                    // create a new search node
                    SearchNode forInsert = new SearchNode(board, node.moves + 1,
                            node);
                    // insert created search node
                    pq.insert(forInsert);
                }
                // checks if neighbor is different from previous
                else if (!board.equals(node.previous.board))
                {
                    // create new search node
                    SearchNode forInsert = new SearchNode(board,
                            node.moves + 1, node);
                    // insert created search node
                    pq.insert(forInsert);
                }
            }

        }
    }

    // The minimum number of moves to solve the initial board.
    public int moves() {
        return moves;
    }

    // Sequence of boards in a shortest solution.
    public Iterable<Board> solution() {
        return solution;
    }

    // Helper hamming priority function comparator.
    private static class HammingOrder implements Comparator<SearchNode> {
        public int compare(SearchNode a, SearchNode b)
        {
            // returns comparison
            return (a.board.hamming() + a.moves) - (b.board.hamming()
                    + b.moves);
        }
    }
       
    // Helper manhattan priority function comparator.
    private static class ManhattanOrder implements Comparator<SearchNode> {
        public int compare(SearchNode a, SearchNode b)
        {
            // returns comparison
            return (a.board.manhattan() + a.moves) - (b.board.manhattan()
                    + b.moves);
        }
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
        Board initial = new Board(tiles);
        if (initial.isSolvable()) {
            Solver solver = new Solver(initial);
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }
        else {
            StdOut.println("Unsolvable puzzle");
        }
    }
}
