package testing;
import algorithms.SelectionSort;

import metrics.PerformanceTracker;
import org.junit.jupiter.api.Test;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SelectionSortTest {

    @Test
    void empty() {
        assertArrayEquals(new int[]{}, sort(new int[]{}));
    }

    @Test
    void single() {
        assertArrayEquals(new int[]{5}, sort(new int[]{5}));
    }

    @Test
    void duplicates() {
        assertArrayEquals(new int[]{1,1,2,2,3,3}, sort(new int[]{3,2,1,2,3,1}));
    }

    @Test
    void alreadySortedEarlyExit() {
        SelectionSort s = new SelectionSort();
        PerformanceTracker t = new PerformanceTracker();
        int[] res = s.sort(new int[]{1,2,3,4,5}, t, true, false);
        assertArrayEquals(new int[]{1,2,3,4,5}, res);
        assertTrue(t.getComparisons() < 20);
    }

    private int[] sort(int[] in) {
        SelectionSort s = new SelectionSort();
        PerformanceTracker t = new PerformanceTracker();
        return s.sort(in, t, true, false);
    }
}
