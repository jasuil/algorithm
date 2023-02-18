import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.TST;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashSet;
import java.util.LinkedList;

public class BoggleSolver
{
    private final TST<Integer> tst;
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        tst = new TST<>();
        int k = 0;
        for (int i = 0; i < dictionary.length; i++) {
            if (dictionary[i].length() > 2) tst.put(dictionary[i], i);
        }
    }

    private class Tour {
        boolean[][] toured;
        int curRow;
        int curCol;
        LinkedList<int[]> coordinates;

        public Tour (int row, int col, int rowSize, int colSize) {
            curRow = row;
            curCol = col;
            coordinates = new LinkedList<>();
            this.toured = new boolean[rowSize][colSize];
            toured[row][col] = true;
        }
    }

    private void addStack(Tour tour, BoggleBoard board, HashSet<String> result, Stack<Tour> stack, int row, int col) {
        Tour newTour = new Tour(row, col, board.rows(), board.cols());
        for (int i = 0; i < board.rows(); i++) {
            for (int i2 = 0; i2 < board.cols(); i2++) {
                if (i == row && i2 == col) continue;
                newTour.toured[i][i2] = tour.toured[i][i2];
            }
        }
        StringBuilder StringBuilder = new StringBuilder();
        for (int[] coordinate : tour.coordinates) {
            newTour.coordinates.add(new int[]{coordinate[0], coordinate[1]});
            StringBuilder.append(board.getLetter(coordinate[0], coordinate[1]));
        }
        newTour.coordinates.add(new int[]{row, col});
        StringBuilder.append(board.getLetter(row, col));
        String curWord = StringBuilder.toString().replaceAll("Q", "QU");
        addValidWord(curWord, result);

        if (tst.keysWithPrefix(curWord).iterator().hasNext()) stack.push(newTour);
    }

    private void addValidWord(String str, HashSet<String> result) {
        if (str.length() < 3) return;
        if (tst.get(str) != null) {
            result.add(str);
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        HashSet<String> result = new HashSet<>();
        Stack<Tour> stack = new Stack<>();

        for (int i = 0; i < board.rows(); i++) {
            for (int i2 = 0; i2 < board.cols(); i2++) {
                Tour tour = new Tour(i, i2, board.rows(), board.cols());
                tour.coordinates.add(new int[]{i, i2});
                stack.push(tour);
            }
        }

        while (!stack.isEmpty()) {
            Tour tour = stack.pop();

            // up
            if (tour.curRow-1 > -1) {
                if (tour.curCol-1 > -1 && !tour.toured[tour.curRow-1][tour.curCol-1]) {
                    addStack(tour, board, result, stack, tour.curRow-1, tour.curCol-1);
                }
                if (tour.curCol+1 < board.cols() && !tour.toured[tour.curRow-1][tour.curCol+1]) {
                    addStack(tour, board, result, stack, tour.curRow-1, tour.curCol+1);
                }
                if (!tour.toured[tour.curRow-1][tour.curCol]) addStack(tour, board, result, stack, tour.curRow-1, tour.curCol);
            }

            // left, right
            if (tour.curCol-1 > -1 && !tour.toured[tour.curRow][tour.curCol-1]) {
                addStack(tour, board, result, stack, tour.curRow, tour.curCol-1);
            }
            if (tour.curCol+1 < board.cols() && !tour.toured[tour.curRow][tour.curCol+1]) {
                addStack(tour, board, result, stack, tour.curRow, tour.curCol+1);
            }

            // down
            if (tour.curRow+1 < board.rows()) {
                if (tour.curCol-1 > -1 && !tour.toured[tour.curRow+1][tour.curCol-1]) {
                    addStack(tour, board, result, stack, tour.curRow+1, tour.curCol-1);
                }
                if (tour.curCol+1 < board.cols() && !tour.toured[tour.curRow+1][tour.curCol+1]) {
                    addStack(tour, board, result, stack, tour.curRow+1, tour.curCol+1);
                }
                if (!tour.toured[tour.curRow+1][tour.curCol]) addStack(tour, board, result, stack, tour.curRow+1, tour.curCol);
            }
        }

        return result;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (tst.get(word) == null) return 0;
        int length = word.length();
        if (length > 2 && length < 5) return 1;
        else if (length == 5) return 2;
        else if (length == 6) return 3;
        else if (length == 7) return 5;
        else if (length > 7)  return 11;
        else return 0;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;

        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);

    }
}
