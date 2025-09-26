package cli;

import algorithms.InsertionSort;
import metrics.PerformanceTracker;

import java.util.Arrays;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;

public class BenchmarkRunner {
    public static void main(String[] args) throws Exception {
        int    n      = args.length > 0 ? parseInt(args[0], 1000) : 1000;
        String input  = args.length > 1 ? args[1].toLowerCase()   : "random";
        int    trials = args.length > 2 ? parseInt(args[2], 3)    : 3;
        long   seed   = args.length > 3 ? parseLong(args[3], 42L) : 42L;
        String csv    = args.length > 4 ? args[4]                 : null;

        PerformanceTracker tracker = new PerformanceTracker();
        InsertionSort insertion = new InsertionSort();

        long totalNs = 0;
        int[] lastSorted = null;

        if (csv != null) ensureCsvHeader(csv);

        for (int t = 0; t < Math.max(1, trials); t++) {
            int[] data = makeInput(n, input, seed + t);
            tracker.reset();
            lastSorted = insertion.sort(data, tracker);
            totalNs += tracker.getElapsedNs();

            if (csv != null) appendCsv(csv, tracker.toCsvRow("insertion", n, input));
        }

        long avgNs = totalNs / Math.max(1, trials);
        System.out.println("algo=insertion, n=" + n + ", input=" + input +
                ", trials=" + trials + ", avg(ns)=" + avgNs);
        System.out.println("sample(sorted): " + Arrays.toString(sample(lastSorted)));
        System.out.println("last metrics:   " + tracker);
    }

    private static int[] makeInput(int n, String kind, long seed) {
        int[] a = new int[n];
        Random r = new Random(seed);
        for (int i = 0; i < n; i++) a[i] = r.nextInt(1_000_000);

        switch (kind) {
            case "sorted"   -> Arrays.sort(a);
            case "reversed" -> { Arrays.sort(a); reverse(a); }
            case "nearly"   -> { Arrays.sort(a); if (n > 1) swap(a, n/3, (2*n)/3); }
            default         -> {}
        }
        return a;
    }
    private static void reverse(int[] a) {
        for (int i = 0, j = a.length - 1; i < j; i++, j--) { int t = a[i]; a[i] = a[j]; a[j] = t; }
    }
    private static void swap(int[] a, int i, int j) { int t = a[i]; a[i] = a[j]; a[j] = t; }
    private static int[] sample(int[] arr) {
        if (arr == null) return new int[0];
        return Arrays.copyOf(arr, Math.min(arr.length, 10));
    }
    private static int parseInt(String s, int def) {
        try { return Integer.parseInt(s); } catch (Exception e) { return def; }
    }
    private static long parseLong(String s, long def) {
        try { return Long.parseLong(s); } catch (Exception e) { return def; }
    }
    private static void ensureCsvHeader(String path) throws IOException {
        java.io.File f = new java.io.File(path);
        if (!f.exists() || f.length() == 0) {
            try (FileWriter w = new FileWriter(f, true)) {
                w.write(PerformanceTracker.csvHeader() + System.lineSeparator());
            }
        }
    }
    private static void appendCsv(String path, String row) throws IOException {
        try (FileWriter w = new FileWriter(path, true)) {
            w.write(row + System.lineSeparator());
        }
    }
}