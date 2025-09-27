package testing;

import algorithms.InsertionSort;
import metrics.PerformanceTracker;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class InsertionSortTest {

    @Test
     void emptyArray() {
        int[] out = new InsertionSort().sort(new int[]{}, new PerformanceTracker());
        assertArrayEquals(new int[]{}, out, "Empty array should remain empty");
    }

    @Test
    void singleElement() {
        int[] out = new InsertionSort().sort(new int[]{42}, new PerformanceTracker());
        assertArrayEquals(new int[]{42}, out, "Single element should remain unchanged");
    }

    @Test
    void alreadySorted() {
        int[] out = new InsertionSort().sort(new int[]{1,2,3,4,5}, new PerformanceTracker());
        assertArrayEquals(new int[]{1,2,3,4,5}, out, "Already sorted array should not change");
    }

    @Test
    void reversedArray() {
        int[] out = new InsertionSort().sort(new int[]{5,4,3,2,1}, new PerformanceTracker());
        assertArrayEquals(new int[]{1,2,3,4,5}, out, "Reversed array should be sorted ascending");
    }

    @Test
    void withDuplicates() {
        int[] out = new InsertionSort().sort(new int[]{3,1,2,3,1}, new PerformanceTracker());
        assertArrayEquals(new int[]{1,1,2,3,3}, out, "Array with duplicates should be sorted correctly");
    }

    @Test
    void randomArray() {
        int[] out = new InsertionSort().sort(new int[]{10,-5,7,3,0}, new PerformanceTracker());
        assertArrayEquals(new int[]{-5,0,3,7,10}, out, "Random array should be sorted ascending");
    }
}