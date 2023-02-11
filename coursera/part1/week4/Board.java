import java.util.ArrayList;

public class Board {

    private int[][] board;
    private int manhattan;
    private int hamming;
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles == null) throw new IllegalArgumentException();

        this.board = new int[tiles.length][tiles.length];
        for (int i = 0; i < tiles.length; i++) {
            for (int i2 = 0; i2 < tiles.length; i2++) {
                this.board[i][i2] = tiles[i][i2];

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

    // string representation of this board
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(board.length).append("\n");
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
    public int hamming() {
        hamming = 0;
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
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
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
        if (board.length != that.board.length) return false;
        if (board[0].length != that.board[0].length) return false;
        for(int i = 0; i < board.length; i++) {
            for (int i2 = 0; i2 < board.length; i2++) {
                if (board[i][i2] != that.board[i][i2]) return false;
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> iterable = new ArrayList<>();
        int[] zeroIndex = new int[0];
        for(int i = 0; i < board.length; i++) {
            for (int i2 = 0; i2 < board.length; i2++) {
                if (board[i][i2] == 0) {
                    zeroIndex = new int[] {i, i2 };
                    break;
                }
            }
        }

        if (zeroIndex[1] > 0) {
            Board newBoard = new Board(board);
            int temp = newBoard.board[zeroIndex[0]][zeroIndex[1]];
            // 이동할 곳
            int diffToColumn = zeroIndex[1]-1 - (newBoard.board[zeroIndex[0]][zeroIndex[1]-1]-1) % newBoard.board.length < 0 ?(newBoard.board[zeroIndex[0]][zeroIndex[1]-1]-1) % newBoard.board.length  - (zeroIndex[1]-1) : zeroIndex[1]-1 - (newBoard.board[zeroIndex[0]][zeroIndex[1]-1]-1) % newBoard.board.length;

            newBoard.board[zeroIndex[0]][zeroIndex[1]] = newBoard.board[zeroIndex[0]][zeroIndex[1]-1];
            newBoard.board[zeroIndex[0]][zeroIndex[1]-1] = temp;
            // 0이었던 곳
            int afterDiffToColumn = zeroIndex[1] - (newBoard.board[zeroIndex[0]][zeroIndex[1]]-1) % newBoard.board.length < 0 ? (newBoard.board[zeroIndex[0]][zeroIndex[1]]-1) % newBoard.board.length  - zeroIndex[1] : zeroIndex[1] - (newBoard.board[zeroIndex[0]][zeroIndex[1]]-1) % newBoard.board.length;

            newBoard.manhattan += afterDiffToColumn - diffToColumn;
            iterable.add(newBoard);
        }
        if (zeroIndex[1] < board.length - 1) {
            Board newBoard = new Board(board);
            int temp = newBoard.board[zeroIndex[0]][zeroIndex[1]];
            int diffToColumn = zeroIndex[1]+1 - (newBoard.board[zeroIndex[0]][zeroIndex[1]+1]-1) % newBoard.board.length < 0 ? (newBoard.board[zeroIndex[0]][zeroIndex[1]+1]-1) % newBoard.board.length  - (zeroIndex[1]+1) : zeroIndex[1]+1 - (newBoard.board[zeroIndex[0]][zeroIndex[1]+1]-1) % newBoard.board.length;

            newBoard.board[zeroIndex[0]][zeroIndex[1]] = newBoard.board[zeroIndex[0]][zeroIndex[1]+1];
            newBoard.board[zeroIndex[0]][zeroIndex[1]+1] = temp;

            int afterDiffToColumn = zeroIndex[1] - (newBoard.board[zeroIndex[0]][zeroIndex[1]]-1) % newBoard.board.length < 0 ?(newBoard.board[zeroIndex[0]][zeroIndex[1]]-1) % newBoard.board.length  - zeroIndex[1] : zeroIndex[1] - (newBoard.board[zeroIndex[0]][zeroIndex[1]]-1) % newBoard.board.length;

            newBoard.manhattan += afterDiffToColumn - diffToColumn;
            iterable.add(newBoard);
        }
        if (zeroIndex[0] > 0) {
            Board newBoard = new Board(board);
            int temp = newBoard.board[zeroIndex[0]][zeroIndex[1]];
            int diffToRow = zeroIndex[0]-1 - (newBoard.board[zeroIndex[0]-1][zeroIndex[1]]-1) / newBoard.board.length < 0 ? (newBoard.board[zeroIndex[0]-1][zeroIndex[1]]-1) / newBoard.board.length - (zeroIndex[0]-1) : zeroIndex[0]-1 - (newBoard.board[zeroIndex[0]-1][zeroIndex[1]]-1) / newBoard.board.length;

            newBoard.board[zeroIndex[0]][zeroIndex[1]] = newBoard.board[zeroIndex[0]-1][zeroIndex[1]];
            newBoard.board[zeroIndex[0]-1][zeroIndex[1]] = temp;

            int afterDiffToRow = zeroIndex[0] - (newBoard.board[zeroIndex[0]][zeroIndex[1]]-1) / newBoard.board.length < 0 ? (newBoard.board[zeroIndex[0]][zeroIndex[1]]-1) / newBoard.board.length - zeroIndex[0] : zeroIndex[0] - (newBoard.board[zeroIndex[0]][zeroIndex[1]]-1) / newBoard.board.length;

            newBoard.manhattan += afterDiffToRow - diffToRow;
            iterable.add(newBoard);
        }
        if (zeroIndex[0] < board.length - 1) {
            Board newBoard = new Board(board);
            int temp = newBoard.board[zeroIndex[0]][zeroIndex[1]];
            int diffToRow = zeroIndex[0]+1 - (newBoard.board[zeroIndex[0]+1][zeroIndex[1]]-1) / newBoard.board.length < 0 ? (newBoard.board[zeroIndex[0]+1][zeroIndex[1]]-1) / newBoard.board.length - (zeroIndex[0]+1) : zeroIndex[0]+1 - (newBoard.board[zeroIndex[0]+1][zeroIndex[1]]-1) / newBoard.board.length;

            newBoard.board[zeroIndex[0]][zeroIndex[1]] = newBoard.board[zeroIndex[0]+1][zeroIndex[1]];
            newBoard.board[zeroIndex[0]+1][zeroIndex[1]] = temp;

            int afterDiffToRow = zeroIndex[0] - (newBoard.board[zeroIndex[0]][zeroIndex[1]]-1) / newBoard.board.length < 0 ? (newBoard.board[zeroIndex[0]][zeroIndex[1]]-1) / newBoard.board.length - zeroIndex[0] : zeroIndex[0] - (newBoard.board[zeroIndex[0]][zeroIndex[1]]-1) / newBoard.board.length;

            newBoard.manhattan += afterDiffToRow - diffToRow;
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
        int diffToRow1 = notZero[0][0] - (cpBoard.board[notZero[0][0]][notZero[0][1]]-1) / cpBoard.board.length < 0 ? (cpBoard.board[notZero[0][0]][notZero[0][1]]-1) / cpBoard.board.length - notZero[0][0] : notZero[0][0] - (cpBoard.board[notZero[0][0]][notZero[0][1]]-1) / cpBoard.board.length;
        int diffToColumn1 = notZero[0][1] - (cpBoard.board[notZero[0][0]][notZero[0][1]]-1) % cpBoard.board.length < 0 ?(cpBoard.board[notZero[0][0]][notZero[0][1]]-1) % cpBoard.board.length  - notZero[0][1] : notZero[0][1] - (cpBoard.board[notZero[0][0]][notZero[0][1]]-1) % cpBoard.board.length;
        int diffToRow2 = notZero[1][0] - (cpBoard.board[notZero[1][0]][notZero[1][1]]-1) / cpBoard.board.length < 0 ? (cpBoard.board[notZero[1][0]][notZero[1][1]]-1) / cpBoard.board.length - notZero[1][0] : notZero[1][0] - (cpBoard.board[notZero[1][0]][notZero[1][1]]-1) / cpBoard.board.length;
        int diffToColumn2 = notZero[1][1] - (cpBoard.board[notZero[1][0]][notZero[1][1]]-1) % cpBoard.board.length < 0 ?(cpBoard.board[notZero[1][0]][notZero[1][1]]-1) % cpBoard.board.length  - notZero[1][1] : notZero[1][1] - (cpBoard.board[notZero[1][0]][notZero[1][1]]-1) % cpBoard.board.length;
        cpBoard.manhattan -= diffToRow1 + diffToRow2 + diffToColumn1 + diffToColumn2;

        int temp = cpBoard.board[notZero[0][0]][notZero[0][1]];
        cpBoard.board[notZero[0][0]][notZero[0][1]] = cpBoard.board[notZero[1][0]][notZero[1][1]];
        cpBoard.board[notZero[1][0]][notZero[1][1]] = temp;

        diffToRow1 = notZero[0][0] - (cpBoard.board[notZero[0][0]][notZero[0][1]]-1) / cpBoard.board.length < 0 ? (cpBoard.board[notZero[0][0]][notZero[0][1]]-1) / cpBoard.board.length - notZero[0][0] : notZero[0][0] - (cpBoard.board[notZero[0][0]][notZero[0][1]]-1) / cpBoard.board.length;
        diffToColumn1 = notZero[0][1] - (cpBoard.board[notZero[0][0]][notZero[0][1]]-1) % cpBoard.board.length < 0 ?(cpBoard.board[notZero[0][0]][notZero[0][1]]-1) % cpBoard.board.length  - notZero[0][1] : notZero[0][1] - (cpBoard.board[notZero[0][0]][notZero[0][1]]-1) % cpBoard.board.length;
        diffToRow2 = notZero[1][0] - (cpBoard.board[notZero[1][0]][notZero[1][1]]-1) / cpBoard.board.length < 0 ? (cpBoard.board[notZero[1][0]][notZero[1][1]]-1) / cpBoard.board.length - notZero[1][0] : notZero[1][0] - (cpBoard.board[notZero[1][0]][notZero[1][1]]-1) / cpBoard.board.length;
        diffToColumn2 = notZero[1][1] - (cpBoard.board[notZero[1][0]][notZero[1][1]]-1) % cpBoard.board.length < 0 ?(cpBoard.board[notZero[1][0]][notZero[1][1]]-1) % cpBoard.board.length  - notZero[1][1] : notZero[1][1] - (cpBoard.board[notZero[1][0]][notZero[1][1]]-1) % cpBoard.board.length;
        cpBoard.manhattan += diffToRow1 + diffToRow2 + diffToColumn1 + diffToColumn2;

        return cpBoard;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        Board b = new Board(new int[][]{ { 3,7,8}, { 1,4,6}, { 5,0,2}});
        int t = b.twin().manhattan();

        b.equals(new Board(new int[][]{ { 21, 32}, { 0, 3}}));
    }

}