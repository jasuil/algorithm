import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[] model;
    private int size;
    private WeightedQuickUnionUF percolateFinder;
    private WeightedQuickUnionUF fullFinder;
    private int openCount;
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        this.model = new boolean[n*n];
        this.percolateFinder = new WeightedQuickUnionUF(n * n + 2); // n*n size + top, bottom root
        this.fullFinder = new WeightedQuickUnionUF(n * n + 2);
        this.size = n;
    }

    // test client (optional)
    public static void main(String[] args) {
        int n = Integer.parseInt(StdIn.readString());
        Percolation percolation = new Percolation(n);
        while (!StdIn.isEmpty()) {
            int row = Integer.parseInt(StdIn.readString());
            int col = Integer.parseInt(StdIn.readString());
            percolation.open(row, col);
            if (percolation.numberOfOpenSites() >=  18) {
                System.out.println(percolation.percolates());
            }
        }
        // input6.txt
        /*
         Percolation percolation = new Percolation(6);
         percolation.open(1, 6);
         percolation.open(2, 6);
         percolation.open(3, 6);
         percolation.open(4, 6);
         percolation.open(5, 6);
         percolation.open(5, 5);
         percolation.open(4, 4);
         percolation.open(3, 4);
         percolation.open(2, 4);
         percolation.open(2, 3);
         percolation.open(2, 2);
         percolation.open(2, 1);
         percolation.percolates();
         percolation.open(3, 1);
         percolation.open(4, 1);
         percolation.open(5, 1);
         percolation.open(5, 2);
         percolation.open(6, 2);
         percolation.open(5, 4);
*/
    }

    private void validate(int row, int col) {
        if (row < 0 || row >= this.size) {
            throw new IllegalArgumentException();
        }
        if (col < 0 || col >= this.size) {
            throw new IllegalArgumentException();
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        --row;
        --col;

        validate(row, col);

        int leftRow = row - 1;
        int rightRow = row + 1;
        int leftCol = col - 1;
        int rightCol = col + 1;

        if (this.model[this.size * row + col]) return;

        openCount++;
        this.model[this.size * row + col] = true;

        if (leftRow < 0) {
            this.percolateFinder.union(this.model.length, this.size * row + col);
            this.fullFinder.union(this.model.length, this.size * row + col);
        }

        if (leftRow > -1 && this.model[this.size * leftRow + col]) {
            this.percolateFinder.union(this.size * leftRow + col, this.size * row + col);
            this.fullFinder.union(this.size * leftRow + col, this.size * row + col);
        }
        if (rightRow < this.size && this.model[this.size * rightRow + col]
                ) {
            this.percolateFinder.union(this.size * row + col, this.size * rightRow + col);
            this.fullFinder.union(this.size * row + col, this.size * rightRow + col);
        }
        if (leftCol > -1 && this.model[this.size * row + leftCol]) {
            this.percolateFinder.union(this.size * row + leftCol, this.size * row + col);
            this.fullFinder.union(this.size * row + leftCol, this.size * row + col);
        }
        if (rightCol < this.size && this.model[this.size * row + rightCol]) {
            this.percolateFinder.union(this.size * row + col, this.size * row + rightCol);
            this.fullFinder.union(this.size * row + col, this.size * row + rightCol);
        }

        if (rightRow >= this.size) {
             this.percolateFinder.union(this.size * row + col, this.model.length + 1);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        --row;
        --col;

        validate(row, col);

        return this.model[this.size * row + col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        --row;
        --col;

        validate(row, col);

        if (!this.model[this.size * row + col]) return false;
        return this.fullFinder.find(this.size * row + col) == this.fullFinder.find(this.model.length);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.openCount;
    }

    // does the system percolate?
    public boolean percolates() {
         return this.percolateFinder.find(this.model.length) == this.percolateFinder.find(this.model.length + 1);
    }
}