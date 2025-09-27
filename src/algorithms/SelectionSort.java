package algorithms;

import metrics.PerformanceTracker;
import java.util.Arrays;


public class SelectionSort {

    public int[] sort(int[] input, PerformanceTracker t, boolean earlyExit, boolean bidirectional) {
        if (input == null)
            throw new IllegalArgumentException("array is null");

        int[] a = Arrays.copyOf(input, input.length);
        t.incAllocations(1);

        t.startTimer();
        if (a.length < 2) {
            t.stopTimer();
            return a; }

        if (bidirectional) {
            biDirectionalSelection(a, t, earlyExit);
        } else {
            classicSelection(a, t, earlyExit);
        }
        t.stopTimer();
        return a;
    }

    private void classicSelection(int[] a, PerformanceTracker t, boolean earlyExit) {
        int n = a.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            boolean anyInversion = false;

            for (int j = i + 1; j < n; j++) {
                t.incComparisons();
                t.incArrayAccesses(2);
                if (a[j] < a[minIdx]) {
                    minIdx = j;
                }
                if (earlyExit) {
                    t.incComparisons();
                    t.incArrayAccesses(2);
                    if (a[j] < a[j - 1]) anyInversion = true;
                }
            }

            if (minIdx != i) {
                swap(a, i, minIdx, t);
            } else if (earlyExit && !anyInversion) {
                break;
            }
        }
    }

    private void biDirectionalSelection(int[] a, PerformanceTracker t, boolean earlyExit) {
        int left = 0, right = a.length - 1;
        while (left < right) {
            int minIdx = left, maxIdx = right;
            boolean anyInversion = false;

            for (int j = left; j <= right; j++) {
                t.incComparisons();
                t.incArrayAccesses(2);
                if (a[j] < a[minIdx]) minIdx = j;


                t.incComparisons();
                t.incArrayAccesses(2);
                if (a[j] > a[maxIdx]) maxIdx = j;

                if (earlyExit && j > left) {
                    t.incComparisons();
                    t.incArrayAccesses(2);
                    if (a[j] < a[j - 1]) anyInversion = true;
                }
            }

            if (minIdx != left) swap(a, left, minIdx, t);

            if (maxIdx == left) maxIdx = minIdx;

            if (maxIdx != right) swap(a, right, maxIdx, t);

            if (earlyExit && !anyInversion) break;

            left++; right--;
        }
    }

    private void swap(int[] a, int i, int j, PerformanceTracker t) {
        int tmp = a[i];
        t.incArrayAccesses(1);
        a[i] = a[j];
        t.incArrayAccesses(2);
        a[j] = tmp;
        t.incArrayAccesses(1);
        t.incSwaps();
    }
}
