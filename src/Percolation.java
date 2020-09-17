/* *****************************************************************************
 *  Name:              Bjoern Winkler
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    // the number of rows and cols
    private int size;
    // top element
    private int top;
    // bottom element
    private int bottom;

    // we store the status of the sites as a set of n*n elements
    private boolean[] status;

    // Number of open sites
    private int openSites;

    // The WeightedQuickUnionUF structure that stores our elements and connections
    private WeightedQuickUnionUF grid;

    // secondary WQUUF structure without bottom element - used to avoid backwash
    private WeightedQuickUnionUF gridXBottom;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {

        // throw an exception if n <= 0
        if (n <= 0) throw new IllegalArgumentException("n must be > 0: " + n);

        // set the size to n to determine the number of rows and columns
        size = n;

        // set the top and bottom elements
        top = n*n;
        bottom = (n*n) + 1;

        // number of open sites is 0 when we start
        openSites = 0;

        /* initialize the grid structure
         * The grid uses 2 more elements:
         * - (n*n)+1: this is the virtual element in the top row
         * - (n*n)+2: this is the virtual element in the bottom row
         *
         */
        grid = new WeightedQuickUnionUF((n * n) + 2);
        gridXBottom = new WeightedQuickUnionUF((n * n) + 1);

        // set all statuses to blocked
        status = new boolean[n * n];
        for (int i = 0; i < n * n; i++) {
            status[i] = false;
        }
        // we need to set the virtual top and bottom elements to open to allow
        // connections to them e.g. if an element in the bottom row is opened
        // set virtual top element (n*n)+1 to open
        // status[top] = true;
        // status[bottom] = true;
    }

    // checks if row or col provided is within range of the grid (0 < i <= n)
    private void checkSize(int rc) {
        if ((rc <= 0) || (rc > size)) {
            throw new IllegalArgumentException("Not within grid dimensions: " + rc);
        }
    }

    // convert the row and column to N linear array coordinate
    private int rcToN(int row, int col) {
        return (row - 1) * size + (col - 1);
    }


    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        // throw IllegalArgumentException if outside of range
        checkSize(row);
        checkSize(col);

        // if element is already open, return and do nothing
        if (isOpen(row, col)) return;

        // change status to open and increase the openSites count by 1
        status[rcToN(row, col)] = true;
        openSites += 1;

        // connect the open element to any open elements
        // if in the first row, connect to the virtual top row
        // if in the bottom row, connect to the virtual bottom row

        // find neighbors
        // check top neighbor
        if ((row - 1) == 0) {
            // first row, so connect to top element
            grid.union(rcToN(row, col), top);
            gridXBottom.union(rcToN(row, col), top);
        }
        // connect top neighbor if neighbor is open
        else if (isOpen(row - 1, col)) {
            grid.union(rcToN(row, col), rcToN(row-1, col));
            gridXBottom.union(rcToN(row, col), rcToN(row-1, col));
        }

        // check bottom neighbor
        if ((row + 1) == (size + 1)) {
            // bottom row, connect to bottom element
            // we do not use gridXBottom here - avoid connecting to virtual
            // bottom element
            grid.union(rcToN(row, col), bottom);
        }
        else if (isOpen(row + 1, col)) {
            grid.union(rcToN(row, col), rcToN(row + 1, col));
            gridXBottom.union(rcToN(row, col), rcToN(row + 1, col));
        }

        // check left neighbor
        if (((col - 1) > 0) && (isOpen(row, col - 1))) {
            grid.union(rcToN(row, col), rcToN(row, col -1));
            gridXBottom.union(rcToN(row, col), rcToN(row, col -1));
        }

        // check right neighbor
        if (((col + 1) <= size) && (isOpen(row, col + 1))) {
            grid.union(rcToN(row, col), rcToN(row, col + 1));
            gridXBottom.union(rcToN(row, col), rcToN(row, col + 1));
        }
    }


    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkSize(row);
        checkSize(col);

        return status[rcToN(row, col)];
    }


    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        checkSize(row);
        checkSize(col);

        // we can check this by confirming if the r/c element is connected to the top element
        // Use gridXBottom here to avoid backwash!
        return gridXBottom.find(top) == gridXBottom.find(rcToN(row, col));
    }
    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        // percolation can be determined if the top and bottom elements are connected
        return grid.find(top) == grid.find(bottom);
    }

}
