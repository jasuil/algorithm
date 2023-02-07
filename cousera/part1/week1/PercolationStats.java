import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96d;
    private double[] percentages;
    private double mean;
    private double standardDeviation;
    private double confidenceLo;
    private double confidenceHi;
    private int trials;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();

        this.trials = trials;
        this.percentages = new double[trials];
        for (int i = 0; i < trials; i++) {

            Percolation percolation = new Percolation(n);
            int[] opens = new int[n * n];
            for (int j = 0; j < opens.length; j++) {
                opens[j] = j;
            }
            StdRandom.shuffle(opens);

            for (int j = 0, range = n * (n + 1); j < n * n; j++) {
                int row = opens[j] / n + 1;
                int col = opens[j] % n + 1;
                percolation.open(row, col);
                if (percolation.percolates()) {
                    this.percentages[i] = (double) percolation.numberOfOpenSites() / (n * n);
                    break;
                }
            }

        }

        this.mean = StdStats.mean(this.percentages);
        this.standardDeviation = StdStats.stddev(this.percentages);
        this.confidenceLo = this.mean - (CONFIDENCE_95 * this.standardDeviation) / Math.sqrt(this.trials);
        this.confidenceHi = this.mean + (CONFIDENCE_95 * this.standardDeviation) / Math.sqrt(this.trials);
    }

    // sample mean of percolation threshold
    public double mean() {
        return this.mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return this.standardDeviation;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
         return this.confidenceLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.confidenceHi;
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats percolationStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println("mean                    = " + String.valueOf(percolationStats.mean()));
        System.out.println("stddev                  = " + String.valueOf((percolationStats.stddev())));
        System.out.println("95% confidence interval = [" + String.valueOf(percolationStats.confidenceLo()) + ", " + String.valueOf(percolationStats.confidenceHi()) + "]");
    }
}
