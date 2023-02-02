import java.util.Iterator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private int moveCount;
    private Stack<Board> goals;

    private static class AstarObject implements Comparable<AstarObject> {
        Board board;
        AstarObject parent;
        int count;
        public AstarObject(Board board) {
            this.board = board;
            count = 0;
        }

        @Override
        public int compareTo(AstarObject that) {
            return Integer.compare(board.manhattan()+count, that.board.manhattan()+that.count);
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();

        goals = new Stack<>();
        MinPQ<AstarObject> simulations = new MinPQ<>();
        Iterator<Board> boardIterator = initial.neighbors().iterator();

        if (initial.isGoal()) {
            goals.push(initial);
            return;
        }

        AstarObject parent = new AstarObject(initial);
        while (boardIterator.hasNext()) {
            Board neighbor = boardIterator.next();
            AstarObject astarObject = new AstarObject(neighbor);
            astarObject.count = 1;
            astarObject.parent = parent;
            simulations.insert(astarObject);
        }

        moveCount = 0;
        while (simulations.size() > 0) {
            AstarObject simulator = simulations.delMin();

            if (simulator.board.isGoal()) {
                moveCount = simulator.count;
                while (simulator != null) {
                    goals.push(simulator.board);
                    simulator = simulator.parent;
                }
                break;
            }

            for (Board neighbor : simulator.board.neighbors()) {
                boolean isSafe = true;
                AstarObject temp = simulator;
                while (temp != null) {
                    if (neighbor.equals(temp.board)) {
                        isSafe = false;
                        break;
                    }
                    temp = temp.parent;
                }
                if (isSafe) {
                    AstarObject astarObject = new AstarObject(neighbor);
                    astarObject.count = simulator.count + 1;
                    astarObject.parent = simulator;
                    simulations.insert(astarObject);
                }
            }

        }

        if (goals.size() == 0) moveCount = -1;

    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return goals.size() > 0;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return goals.size() == 0 ? -1 : moveCount;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return goals.size() == 0 ? null : goals;
    }

    // test client (see below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
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
