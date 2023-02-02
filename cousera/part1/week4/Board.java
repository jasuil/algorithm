import java.util.ArrayList;
import java.util.Arrays;

public class Board {

    private int[][] board;
    private int[] zeroIndex;
    private int manhattan;
    private int hamming;
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        zeroIndex = new int[]{0, 0};
        if (tiles == null) throw new IllegalArgumentException();

        this.board = new int[tiles.length][tiles.length];
        for (int i = 0; i < tiles.length; i++) {
            for (int i2 = 0; i2 < tiles.length; i2++) {
                this.board[i][i2] = tiles[i][i2];
                if (tiles[i][i2] == 0) {
                    zeroIndex[0] = i;
                    zeroIndex[1] = i2;
                }

                if (i == board.length-1 && i2 == board.length-1) {
                    if (board[i][i2] == 0) continue;
                    int diffToRow = i - (board[i][i2]-1) / board.length < 0 ? (board[i][i2]-1) / board.length - i : i - (board[i][i2]-1) / board.length;
                    int diffToColumn = i2 - (board[i][i2]-1) % board.length < 0 ?(board[i][i2]-1) % board.length  - i2 : i2 - (board[i][i2]-1) % board.length;
                    manhattan += diffToRow + diffToColumn;
                    hamming += 1;
                } else if (board[i][i2] != i * board.length + i2 + 1) {
                    if (board[i][i2] == 0) continue;
                    int diffToRow = i - (board[i][i2]-1) / board.length < 0 ? (board[i][i2]-1) / board.length - i : i - (board[i][i2]-1) / board.length;
                    int diffToColumn = i2 - (board[i][i2]-1) % board.length < 0 ?(board[i][i2]-1) % board.length  - i2 : i2 - (board[i][i2]-1) % board.length;
                    manhattan += diffToRow + diffToColumn;
                    hamming += 1;
                }
            }
        }

       // calcHamming();
       // calcManhattan();
    }

    // string representation of this board
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf(board.length)).append("\n");
        for (int i = 0; i < board.length; i++) {
            for (int i2 = 0; i2 < board.length; i2++) {
                if (i2 == 0) {
                    if (board[i][i2] < 10) sb.append(" ");
                    sb.append(board[i][i2]);
                } else {
                    sb.append(" ");
                    if (board[i][i2] < 10) sb.append(" ");
                    sb.append(board[i][i2]);
                }
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
    private void calcHamming() {

        for (int i = 0; i < board.length; i++) {
            for (int i2 = 0; i2 < board.length; i2++) {
                if (i == board.length-1 && i2 == board.length-1) {
                    if (board[i][i2] == 0) continue;
                    hamming += 1;
                }
                else if (board[i][i2] != i * board.length + i2 + 1) {
                    if (board[i][i2] == 0) continue;
                    hamming += 1;
                }
            }
        }
    }
    public int hamming() {
        hamming = 0;
        calcHamming();
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    private void calcManhattan() {
        for (int i = 0; i < board.length; i++) {
            for (int i2 = 0; i2 < board.length; i2++) {
                if (i == board.length-1 && i2 == board.length-1) {
                    if (board[i][i2] == 0) continue;
                    int diffToRow = i - (board[i][i2]-1) / board.length < 0 ? (board[i][i2]-1) / board.length - i : i - (board[i][i2]-1) / board.length;
                    int diffToColumn = i2 - (board[i][i2]-1) % board.length < 0 ?(board[i][i2]-1) % board.length  - i2 : i2 - (board[i][i2]-1) % board.length;
                    manhattan += diffToRow + diffToColumn;
                    hamming += 1;
                } else if (board[i][i2] != i * board.length + i2 + 1) {
                    if (board[i][i2] == 0) continue;
                    int diffToRow = i - (board[i][i2]-1) / board.length < 0 ? (board[i][i2]-1) / board.length - i : i - (board[i][i2]-1) / board.length;
                    int diffToColumn = i2 - (board[i][i2]-1) % board.length < 0 ?(board[i][i2]-1) % board.length  - i2 : i2 - (board[i][i2]-1) % board.length;
                    manhattan += diffToRow + diffToColumn;
                    hamming += 1;
                }
            }
        }
    }
    public int manhattan() {
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return manhattan == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        return Arrays.deepEquals(this.board, that.board);
        //if (!that.board.getClass().equals(int[][].class)) return false;

    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> iterable = new ArrayList<>();
        if (zeroIndex[1] > 0) {
            Board newBoard = new Board(board);
            int temp = newBoard.board[zeroIndex[0]][zeroIndex[1]];
            // 이동할 곳
           // int diffToRow = zeroIndex[0] - (newBoard.board[zeroIndex[0]][zeroIndex[1]-1]-1) / newBoard.board.length < 0 ? (newBoard.board[zeroIndex[0]][zeroIndex[1]-1]-1) / newBoard.board.length - zeroIndex[0] : zeroIndex[0] - (newBoard.board[zeroIndex[0]][zeroIndex[1]-1]-1) / newBoard.board.length;
            int diffToColumn = zeroIndex[1]-1 - (newBoard.board[zeroIndex[0]][zeroIndex[1]-1]-1) % newBoard.board.length < 0 ?(newBoard.board[zeroIndex[0]][zeroIndex[1]-1]-1) % newBoard.board.length  - (zeroIndex[1]-1) : zeroIndex[1]-1 - (newBoard.board[zeroIndex[0]][zeroIndex[1]-1]-1) % newBoard.board.length;

            newBoard.board[zeroIndex[0]][zeroIndex[1]] = newBoard.board[zeroIndex[0]][zeroIndex[1]-1];
            newBoard.board[zeroIndex[0]][zeroIndex[1]-1] = temp;
            // 0이었던 곳
          //  int afterDiffToRow = zeroIndex[0] - (newBoard.board[zeroIndex[0]][zeroIndex[1]]-1) / newBoard.board.length < 0 ? (newBoard.board[zeroIndex[0]][zeroIndex[1]]-1) / newBoard.board.length - zeroIndex[0] : zeroIndex[0] - (newBoard.board[zeroIndex[0]][zeroIndex[1]]-1) / newBoard.board.length;
            int afterDiffToColumn = zeroIndex[1] - (newBoard.board[zeroIndex[0]][zeroIndex[1]]-1) % newBoard.board.length < 0 ? (newBoard.board[zeroIndex[0]][zeroIndex[1]]-1) % newBoard.board.length  - zeroIndex[1] : zeroIndex[1] - (newBoard.board[zeroIndex[0]][zeroIndex[1]]-1) % newBoard.board.length;

            newBoard.manhattan += afterDiffToColumn - diffToColumn;
            newBoard.zeroIndex = new int[]{zeroIndex[0],zeroIndex[1]-1};
            iterable.add(newBoard);
        }
        if (zeroIndex[1] < board.length - 1) {
            Board newBoard = new Board(board);
            int temp = newBoard.board[zeroIndex[0]][zeroIndex[1]];
           // int diffToRow = zeroIndex[0] - (newBoard.board[zeroIndex[0]][zeroIndex[1]+1]-1) / newBoard.board.length < 0 ? (newBoard.board[zeroIndex[0]][zeroIndex[1]+1]-1) / newBoard.board.length - zeroIndex[0] : zeroIndex[0] - (newBoard.board[zeroIndex[0]][zeroIndex[1]+1]-1) / newBoard.board.length;
            int diffToColumn = zeroIndex[1]+1 - (newBoard.board[zeroIndex[0]][zeroIndex[1]+1]-1) % newBoard.board.length < 0 ? (newBoard.board[zeroIndex[0]][zeroIndex[1]+1]-1) % newBoard.board.length  - (zeroIndex[1]+1) : zeroIndex[1]+1 - (newBoard.board[zeroIndex[0]][zeroIndex[1]+1]-1) % newBoard.board.length;

            newBoard.board[zeroIndex[0]][zeroIndex[1]] = newBoard.board[zeroIndex[0]][zeroIndex[1]+1];
            newBoard.board[zeroIndex[0]][zeroIndex[1]+1] = temp;

           // int afterDiffToRow = zeroIndex[0] - (newBoard.board[zeroIndex[0]][zeroIndex[1]]-1) / newBoard.board.length < 0 ? (newBoard.board[zeroIndex[0]][zeroIndex[1]]-1) / newBoard.board.length - zeroIndex[0] : zeroIndex[0] - (newBoard.board[zeroIndex[0]][zeroIndex[1]]-1) / newBoard.board.length;
            int afterDiffToColumn = zeroIndex[1] - (newBoard.board[zeroIndex[0]][zeroIndex[1]]-1) % newBoard.board.length < 0 ?(newBoard.board[zeroIndex[0]][zeroIndex[1]]-1) % newBoard.board.length  - zeroIndex[1] : zeroIndex[1] - (newBoard.board[zeroIndex[0]][zeroIndex[1]]-1) % newBoard.board.length;

            newBoard.manhattan += afterDiffToColumn - diffToColumn;
            newBoard.zeroIndex = new int[]{zeroIndex[0],zeroIndex[1]+1};
            iterable.add(newBoard);
        }
        if (zeroIndex[0] > 0) {
            Board newBoard = new Board(board);
            int temp = newBoard.board[zeroIndex[0]][zeroIndex[1]];
            int diffToRow = zeroIndex[0]-1 - (newBoard.board[zeroIndex[0]-1][zeroIndex[1]]-1) / newBoard.board.length < 0 ? (newBoard.board[zeroIndex[0]-1][zeroIndex[1]]-1) / newBoard.board.length - (zeroIndex[0]-1) : zeroIndex[0]-1 - (newBoard.board[zeroIndex[0]-1][zeroIndex[1]]-1) / newBoard.board.length;
           // int diffToColumn = zeroIndex[1] - (newBoard.board[zeroIndex[0]-1][zeroIndex[1]]-1) % newBoard.board.length < 0 ? (newBoard.board[zeroIndex[0]-1][zeroIndex[1]]-1) % newBoard.board.length  - zeroIndex[1] : zeroIndex[1] - (newBoard.board[zeroIndex[0]-1][zeroIndex[1]]-1) % newBoard.board.length;

            newBoard.board[zeroIndex[0]][zeroIndex[1]] = newBoard.board[zeroIndex[0]-1][zeroIndex[1]];
            newBoard.board[zeroIndex[0]-1][zeroIndex[1]] = temp;

            int afterDiffToRow = zeroIndex[0] - (newBoard.board[zeroIndex[0]][zeroIndex[1]]-1) / newBoard.board.length < 0 ? (newBoard.board[zeroIndex[0]][zeroIndex[1]]-1) / newBoard.board.length - zeroIndex[0] : zeroIndex[0] - (newBoard.board[zeroIndex[0]][zeroIndex[1]]-1) / newBoard.board.length;
           // int afterDiffToColumn = zeroIndex[1] - (newBoard.board[zeroIndex[0]][zeroIndex[1]]-1) % newBoard.board.length < 0 ?(newBoard.board[zeroIndex[0]][zeroIndex[1]]-1) % newBoard.board.length  - zeroIndex[1] : zeroIndex[1] - (newBoard.board[zeroIndex[0]][zeroIndex[1]]-1) % newBoard.board.length;

            newBoard.manhattan += afterDiffToRow - diffToRow;
            newBoard.zeroIndex = new int[]{zeroIndex[0]-1,zeroIndex[1]};
            iterable.add(newBoard);
        }
        if (zeroIndex[0] < board.length - 1) {
            Board newBoard = new Board(board);
            int temp = newBoard.board[zeroIndex[0]][zeroIndex[1]];
            int diffToRow = zeroIndex[0]+1 - (newBoard.board[zeroIndex[0]+1][zeroIndex[1]]-1) / newBoard.board.length < 0 ? (newBoard.board[zeroIndex[0]+1][zeroIndex[1]]-1) / newBoard.board.length - (zeroIndex[0]+1) : zeroIndex[0]+1 - (newBoard.board[zeroIndex[0]+1][zeroIndex[1]]-1) / newBoard.board.length;
           // int diffToColumn = zeroIndex[1] - (newBoard.board[zeroIndex[0]+1][zeroIndex[1]]-1) % newBoard.board.length < 0 ? (newBoard.board[zeroIndex[0]+1][zeroIndex[1]]-1) % newBoard.board.length  - zeroIndex[1] : zeroIndex[1] - (newBoard.board[zeroIndex[0]+1][zeroIndex[1]]-1) % newBoard.board.length;

            newBoard.board[zeroIndex[0]][zeroIndex[1]] = newBoard.board[zeroIndex[0]+1][zeroIndex[1]];
            newBoard.board[zeroIndex[0]+1][zeroIndex[1]] = temp;

            int afterDiffToRow = zeroIndex[0] - (newBoard.board[zeroIndex[0]][zeroIndex[1]]-1) / newBoard.board.length < 0 ? (newBoard.board[zeroIndex[0]][zeroIndex[1]]-1) / newBoard.board.length - zeroIndex[0] : zeroIndex[0] - (newBoard.board[zeroIndex[0]][zeroIndex[1]]-1) / newBoard.board.length;
           // int afterDiffToColumn = zeroIndex[1] - (newBoard.board[zeroIndex[0]][zeroIndex[1]]-1) % newBoard.board.length < 0 ?(newBoard.board[zeroIndex[0]][zeroIndex[1]]-1) % newBoard.board.length  - zeroIndex[1] : zeroIndex[1] - (newBoard.board[zeroIndex[0]][zeroIndex[1]]-1) % newBoard.board.length;

            newBoard.manhattan += afterDiffToRow - diffToRow;
            newBoard.zeroIndex = new int[]{zeroIndex[0]+1,zeroIndex[1]};
            iterable.add(newBoard);
        }

        return iterable;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        Board cpBoard = new Board(board);

        int[][] notZero = new int[][]{{-1,-1},{-1,-1}};
        for (int i = 0; i < board.length; i++) {
            for (int i2 = 0; i2 < board.length; i2++) {
                if (board[i][i2] > 0) {
                    if (notZero[0][0] == -1) {
                        notZero[0][0] = i;
                        notZero[0][1] = i2;
                    } else {
                        notZero[1][0] = i;
                        notZero[1][1] = i2;
                        break;
                    }
                }
            }
        }
        int temp = cpBoard.board[notZero[0][0]][notZero[0][1]];
        cpBoard.board[notZero[0][0]][notZero[0][1]] = cpBoard.board[notZero[1][0]][notZero[1][1]];
        cpBoard.board[notZero[1][0]][notZero[1][1]] = temp;

        return cpBoard;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        Board b = new Board(new int[][]{ { 1, 0, 3}, { 4, 2, 5}, { 7, 8, 6}});
        Iterable<Board> b1 = b.neighbors();

        b.equals(new Board(new int[][]{ { 21, 32}, { 0, 3}}));
    }

}
