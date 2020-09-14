/* *****************************************************************************
 *  Name:              Bjoern Winkler
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/**
 * Unit test for simple App.
 */
@SuppressFBWarnings("NAB_NEEDLESS_BOOLEAN_CONSTANT_CONVERSION")
public class TestPercolation {
    /**
     * Rigorous Test.
     */

    // Test if constructor with n = 0 throws exception
    @Test
    public void testException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Percolation(0));
        assertEquals("n must be > 0: 0", exception.getMessage());
    }

    // Test if isOpen with row / column out of bounds throws exception
    @Test
    public void testIsOpen() {
        int n = 10;
        Percolation p = new Percolation(n);

        // row is 0
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> p.isOpen(0, 1));
        assertEquals("Not within grid dimensions: 0", exception1.getMessage());

        // row is > 10
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> p.isOpen(n+1, 1));
        assertEquals("Not within grid dimensions: " + (n + 1), exception2.getMessage());

        // col is -1
        Exception exception3 = assertThrows(IllegalArgumentException.class, () -> p.isOpen(1, -1));
        assertEquals("Not within grid dimensions: -1", exception3.getMessage());

        // col is > 10
        Exception exception4 = assertThrows(IllegalArgumentException.class, () -> p.isOpen(1, n+5));
        assertEquals("Not within grid dimensions: " + (n + 5), exception4.getMessage());
    }

    // Test open
    @edu.umd.cs.findbugs.annotations.SuppressFBWarnings("NAB_NEEDLESS_BOOLEAN_CONSTANT_CONVERSION")
    @Test
    public void testOpen() {
        int n = 10;
        Percolation p = new Percolation(n);

        int r = 5;
        int c = 5;
        // Test that (5, 5) is not open
        assertEquals(false, p.isOpen(r, c));
        p.open(r, c);
        // Test that (5, 5) is now open
        assertEquals(true, p.isOpen(r, c));

    }

    // Test that we can connect elements and that isFull works
    @Test
    public void testIsFull() {
        int n = 10;
        Percolation p = new Percolation(n);

        int r = 1;
        int c = 1;
        // Test that (1, 1) is not open
        assertEquals(false, p.isOpen(r, c));
        p.open(r, c);
        // Test that (1, 1) is now open
        assertEquals(true, p.isOpen(r, c));
        // Test that (1, 1) is now full (connected to top)
        assertEquals(true, p.isFull(1, 1));

        r = 3;
        c = 3;
        // test that (3, 3) is not full
        assertEquals(false, p.isOpen(r, c));
        assertEquals(false, p.isFull(r, c));
        p.open(r, c);
        assertEquals(true, p.isOpen(r, c));
        assertEquals(false, p.isFull(r, c));
    }

    // Test that numberOfOpenSites works correctly
    @Test
    public void testNumberOfOpenSites() {
        int n = 10;
        Percolation p = new Percolation(n);

        int r = 1;
        int c = 1;
        // Test that number of open sites is 0
        assertEquals(0, p.numberOfOpenSites());
        p.open(r, c);
        // Test that open sites increased to 1
        assertEquals(1, p.numberOfOpenSites());
        // open the same site again, count should not go up
        p.open(r, c);
        // should still be 1...
        assertEquals(1, p.numberOfOpenSites());
        // open another site and check that it's now 2
        p.open(3, 4);
        assertEquals(2, p.numberOfOpenSites());
    }

    // Test that percolate() works correctly
    @Test
    public void testPercolate() {
        int n = 5;
        Percolation p = new Percolation(n);

        // fill in the first column
        int c = 1;
        for (int r = 1; r <= n; r++) p.open(r, c);
        // Test that number of open sites is 5
        assertEquals(5, p.numberOfOpenSites());
        // System should now percolate
        assertEquals(true, p.percolates());
    }
}