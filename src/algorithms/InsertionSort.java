package algorithms;

import metrics.PerformanceTracker;
import java.util.Arrays;

public class InsertionSort {
    public int[] sort(int[] input, PerformanceTracker t) {
        if (input == null) throw new IllegalArgumentException("array is null");

        int[] a = Arrays.copyOf(input, input.length);
        t.incAllocations(1);
        t.startTimer();

        if (a.length < 2) {
            t.stopTimer();
            return a;
        }

        for (int i = 1; i < a.length; i++) {
            int key = a[i];
            t.incArrayAccesses(1);

            int pos = binarySearchPos(a, 0, i, key, t);

            if (pos != i) {
                int j = i;
                while (j > pos) {
                    t.incArrayAccesses(2);
                    a[j] = a[j - 1];
                    t.incSwaps();
                    j--;
                }
                a[pos] = key;
                t.incArrayAccesses(1);
            }
        }

        t.stopTimer();
        return a;
    }

    private int binarySearchPos(int[] a, int lo, int hi, int key, PerformanceTracker t) {
        int left = lo, right = hi;
        while (left < right) {
            int mid = (left + right) >>> 1;
            t.incComparisons();
            t.incArrayAccesses(1);
            if (a[mid] <= key) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }
}