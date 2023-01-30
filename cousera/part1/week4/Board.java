import java.util.ArrayList;

public class Board {

    private int[][] board;
    private int[] zeroIndex = {0, 0};
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles == null) throw new IllegalArgumentException();

        this.board = new int[tiles.length][tiles.length];
        for (int i = 0; i < tiles.length; i++) {
            for (int i2 = 0; i2 < tiles.length; i2++) {
                this.board[i][i2] = tiles[i][i2];
                if (tiles[i][i2] == 0) {
                    zeroIndex[0] = i;
                    zeroIndex[1] = i2;
                }
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < board.length; i++) {
            for (int i2 = 0; i2 < board.length; i2++) {
                sb.append(board[i][i2]);
                if (i2 < board.length - 1) sb.append(" ");
            }
            if (i < board.length - 1) sb.append("\n");
        }
        return sb.toString();
    }

    // board dimension n
    public int dimension() {
        return board.length;
    }

    // number of tiles out of place
    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < board.length; i++) {
            for (int i2 = 0; i2 < board.length; i2++) {
                if (i == board.length-1 && i2 == board.length-1) continue;
                else if (board[i][i2] != i * board.length + i2 + 1) {
                    hamming += 1;
                }
            }
        }
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < board.length; i++) {
            for (int i2 = 0; i2 < board.length; i2++) {
                if (i == board.length-1 && i2 == board.length-1) {
                    if (board[i][i2] == 0) continue;
                    manhattan += board[i][i2] - (i * board.length + i2 + 1) > 0 ? board[i][i2] - (i * board.length + i2 + 1) : (i * board.length + i2 + 1) - board[i][i2];
                } else if (board[i][i2] != i * board.length + i2 + 1) {
                    int exchangeNumber = board[i][i2];
                    if (board[i][i2] == 0) continue;
                    manhattan += exchangeNumber - (i * board.length + i2 + 1) > 0 ? exchangeNumber - (i * board.length + i2 + 1) : (i * board.length + i2 + 1) - exchangeNumber;
                }
            }
        }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null || !(y.getClass().getClass().equals(board.getClass()))) throw new IllegalArgumentException();
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> iterable = new ArrayList<>();
        if (zeroIndex[1] > 0) {
            int[][] cpBoard = new int[board.length][board.length];
            for (int i = 0; i < board.length; i++) {
                for (int i2 = 0; i2 < board.length; i2++) {
                    if (i == zeroIndex[0]){
                        if (i2 == zeroIndex[1] - 1) cpBoard[i][i2] = 0;
                        else if (i2 == zeroIndex[1]) cpBoard[i][i2] = board[i][i2-1];
                        else cpBoard[i][i2] = board[i][i2];
                    }
                    else cpBoard[i][i2] = board[i][i2];
                }
            }
            Board newBoard = new Board(cpBoard);
            iterable.add(newBoard);
        }
        if (zeroIndex[1] < board.length - 1) {
            int[][] cpBoard = new int[board.length][board.length];
            for (int i = 0; i < board.length; i++) {
                for (int i2 = 0; i2 < board.length; i2++) {
                    if (i == zeroIndex[0]){
                        if (i2 == zeroIndex[1] + 1) cpBoard[i][i2] = 0;
                        else if (i2 == zeroIndex[1]) cpBoard[i][i2] = board[i][i2+1];
                        else cpBoard[i][i2] = board[i][i2];
                    }
                    else cpBoard[i][i2] = board[i][i2];
                }
            }
            Board newBoard = new Board(cpBoard);
            iterable.add(newBoard);
        }
        if (zeroIndex[0] > 0) {
            int[][] cpBoard = new int[board.length][board.length];
            for (int i = 0; i < board.length; i++) {
                for (int i2 = 0; i2 < board.length; i2++) {
                    if (i2 == zeroIndex[1]){
                        if (i == zeroIndex[0] - 1) cpBoard[i][i2] = 0;
                        else if (i == zeroIndex[0]) cpBoard[i][i2] = board[i-1][i2];
                        else cpBoard[i][i2] = board[i][i2];
                    }
                    else cpBoard[i][i2] = board[i][i2];
                }
            }
            Board newBoard = new Board(cpBoard);
            iterable.add(newBoard);
        }
        if (zeroIndex[0] < board.length - 1) {
            int[][] cpBoard = new int[board.length][board.length];
            for (int i = 0; i < board.length; i++) {
                for (int i2 = 0; i2 < board.length; i2++) {
                    if (i2 == zeroIndex[1]){
                        if (i == zeroIndex[0] + 1) cpBoard[i][i2] = 0;
                        else if (i == zeroIndex[0]) cpBoard[i][i2] = board[i+1][i2];
                        else cpBoard[i][i2] = board[i][i2];
                    }
                    else cpBoard[i][i2] = board[i][i2];
                }
            }
            Board newBoard = new Board(cpBoard);
            iterable.add(newBoard);
        }

        return iterable;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        return new Board(board);
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        Board b = new Board(new int[][]{{1,2},{0,3}});
        int g= b.manhattan();
        assert g==0;
    }

}
