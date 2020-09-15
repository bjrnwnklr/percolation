import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

/* *****************************************************************************
 *  Name:              Bjoern Winkler
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */
class PercolationStatsTest {

    // Test if constructor with n = 0 throws exception
    @Test
    public void testException() {
        // test if n <= 0 throws exception
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> new PercolationStats(0, 1));
        assertEquals("n must be > 0: 0", exception1.getMessage());
        // test if trials <= 0 throws exception
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> new PercolationStats(1, 0));
        assertEquals("trials must be > 0: 0", exception2.getMessage());
    }

    @Test
    void mean() {
    }

    @Test
    void stddev() {
    }

    @Test
    void confidenceLo() {
    }

    @Test
    void confidenceHi() {
    }

    @Test
    void main() {
    }
}