/* *****************************************************************************
 *  Name:              Bjoern Winkler
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    // array that stores number of open sites until percolation is achieved for each
    // trial run
    private double[] results;

    private int trials;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        // throw an exception if n <= 0
        if (n <= 0) throw new IllegalArgumentException("n must be > 0: " + n);
        // throw an exception if trials <= 0
        if (trials <= 0) throw new IllegalArgumentException("trials must be > 0: " + trials);

        this.trials = trials;

        // initialize results to 0
        results = new double[trials];
        for (int t = 0; t < trials; t++) {
            results[t] = 0;
        }

        // run trials
        for (int t = 0; t < trials; t++) {
            results[t] = runExperiment(n);
        }

    }

    // run a percolation experiment
    private double runExperiment(int n) {
        Percolation p = new Percolation(n);

        // run experiment until there is percolation
        while (!p.percolates()) {
            // draw two random integers from 1 to n
            int r = StdRandom.uniform(n) + 1;
            int c = StdRandom.uniform(n) + 1;
            // StdOut.printf("Trying (%d, %d).%n", r, c);

            // open the site if not already open
            if (!p.isOpen(r, c)) p.open(r, c);
        }
        return (double)p.numberOfOpenSites() / (n*n);
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(results);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(results);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double res = this.mean() - (1.96 * this.stddev()) / Math.sqrt(trials);
        return res;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.mean() + (1.96 * this.stddev()) / Math.sqrt(trials);
    }

    public static void main(String[] args) {
        int n;
        int trials;

        if (args.length == 2) {
            n = Integer.parseInt(args[0]);
            trials = Integer.parseInt(args[1]);

            PercolationStats ps = new PercolationStats(n, trials);

            // print the mean
            StdOut.printf("mean \t\t\t\t\t= %f%n", ps.mean());
            StdOut.printf("stddev \t\t\t\t\t= %f%n", ps.stddev());
            StdOut.printf("95%% confidence interval = [%f, %f]%n", ps.confidenceLo(), ps.confidenceHi());
        }
    }
}
