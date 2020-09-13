/* *****************************************************************************
 *  Name:              Bjoern Winkler
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/**
 * Unit test for simple App.
 */
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
    @Test
    public void testOpen() {
        int n = 10;
        Percolation p = new Percolation(n);

        int r = 5;
        int c = 5;
        // Test that (5, 5) is not open
        assertEquals(p.isOpen(r, c), false);
        p.open(r, c);
        // Test that (5, 5) is now open
        assertEquals(p.isOpen(r, c), true);
    }

    // Test that we can connect elements and that isFull works
    @Test
    public void testIsFull() {
        int n = 10;
        Percolation p = new Percolation(n);

        int r = 1;
        int c = 1;
        // Test that (1, 1) is not open
        assertEquals(p.isOpen(r, c), false);
        p.open(r, c);
        // Test that (1, 1) is now open
        assertEquals(p.isOpen(r, c), true);
        // Test that (1, 1) is now full (connected to top)
        assertEquals(p.isFull(1, 1), true);

        r = 3;
        c = 3;
        // test that (3, 3) is not full
        assertEquals(p.isOpen(r, c), false);
        assertEquals(p.isFull(r, c), false);
        p.open(r, c);
        assertEquals(p.isOpen(r, c), true);
        assertEquals(p.isFull(r, c), false);
    }

}