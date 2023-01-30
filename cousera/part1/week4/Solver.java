import java.util.ArrayList;
import java.util.Iterator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private int moveCount;
    private ArrayList<Board> goals;
    private ArrayList<String> visited;

    private class AstarObject implements Comparable<AstarObject> {
        Board board;
        public AstarObject(Board board) {
            this.board = board;
        }

        @Override
        public int compareTo(AstarObject that) {
            return Integer.compare(board.manhattan(), that.board.manhattan());
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        goals = new ArrayList<>();
        visited = new ArrayList<>();
        MinPQ<AstarObject> simulations = new MinPQ<>();
        Iterator<Board> boardIterator = initial.neighbors().iterator();
        visited.add(initial.toString());
        /*
        int[][] solution = new int[initial.board.length][initial.board.length];
        for (int i = 0; i < solution.length; i++) {
            for (int i2 = 0; i2 < solution.length; i2++) {
                if (i == solution.length-1 && i2 == solution.length-1) {
                    solution[i][i2] = 0;
                } else {
                    solution[i][i2] = i * solution.length + i2 + 1;
                }
            }
        }
        Board solutionBoard = new Board(solution);*/

        while (boardIterator.hasNext()) {
            Board neighbor = boardIterator.next();
            if (visited.contains(neighbor.toString())) continue;
            AstarObject astarObject = new AstarObject(neighbor);
            simulations.insert(astarObject);
            visited.add(neighbor.toString());
        }

        int min = simulations.min().board.manhattan();

        while (simulations.size() > 0) {
            moveCount++;
            MinPQ<AstarObject> newSimulations = new MinPQ<>();

            while (simulations.size() > 0) {
                AstarObject simulator = simulations.delMin();

                if (simulator.board.isGoal()) {
                    goals.add(simulator.board);
                    continue;
                }
                if (min == simulator.board.manhattan()) {
                    boardIterator = simulator.board.neighbors().iterator();

                    while (boardIterator.hasNext()) {
                        Board neighbor = boardIterator.next();
                        if (visited.contains(neighbor.toString())) continue;
                        AstarObject astarObject = new AstarObject(neighbor);
                        newSimulations.insert(astarObject);
                        visited.add(neighbor.toString());
                    }
                } else {
                    break;
                }
            }

            simulations = newSimulations;
            if (!simulations.isEmpty()) {
                min = simulations.min().board.manhattan();
            } else {
                min = -1;
            }

        }

    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return goals.size() > 0;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moveCount;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return goals;
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
