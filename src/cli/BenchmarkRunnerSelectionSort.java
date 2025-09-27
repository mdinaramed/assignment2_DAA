package cli;

import algorithms.SelectionSort;
import metrics.PerformanceTracker;

import java.util.Arrays;
import java.util.Random;

public class BenchmarkRunnerSelectionSort {

    public static void main(String[] args) {
        int n = args.length > 0 ? Integer.parseInt(args[0]) : 10000;
        String dist = args.length > 1 ? args[1] : "random";
        boolean earlyExit = args.length > 2 && Boolean.parseBoolean(args[2]);
        boolean bidirectional = args.length > 3 && Boolean.parseBoolean(args[3]);
        int trials = args.length > 4 ? Integer.parseInt(args[4]) : 3;

        long totalTime = 0;
        PerformanceTracker lastT = null;
        int[] lastSorted = null;

        for (int t = 0; t < trials; t++) {
            int[] data = generate(n, dist);
            PerformanceTracker tracker = new PerformanceTracker();
            SelectionSort sorter = new SelectionSort();

            int[] sorted = sorter.sort(data, tracker, earlyExit, bidirectional);

            totalTime += tracker.getElapsedNs();
            lastT = tracker;
            lastSorted = sorted;

            int[] check = Arrays.copyOf(data, data.length);
            Arrays.sort(check);
            if (!Arrays.equals(sorted, check)) {
                throw new AssertionError("Sort mismatch");
            }
        }

        long avgTime = totalTime / trials;

        System.out.printf(
                "algo=SelectionSort, n=%d, dist=%s, earlyExit=%b, bidirectional=%b, trials=%d | avg(ns)=%d%n",
                n, dist, earlyExit, bidirectional, trials, avgTime
        );

        if (lastSorted != null) {
            int sampleSize = Math.min(10, lastSorted.length);
            System.out.println("sample(sorted): " + Arrays.toString(Arrays.copyOf(lastSorted, sampleSize)));
        }

        System.out.println(PerformanceTracker.csvHeader());
        System.out.println(lastT.toCsvRow("selection", n, dist));
    }

    private static int[] generate(int n, String dist) {
        Random r = new Random(42);
        int[] a = new int[n];
        switch (dist) {
            case "sorted":
                for (int i = 0; i < n; i++) a[i] = i;
                break;
            case "reversed":
                for (int i = 0; i < n; i++) a[i] = n - i;
                break;
            case "nearly":
                for (int i = 0; i < n; i++) a[i] = i;
                for (int k = 0; k < Math.max(1, n / 100); k++) {
                    int i = r.nextInt(n), j = r.nextInt(n);
                    int tmp = a[i]; a[i] = a[j]; a[j] = tmp;
                }
                break;
            default:
                for (int i = 0; i < n; i++) a[i] = r.nextInt(n);
        }
        return a;
    }
}
