package com.seerbit;

import java.util.ArrayList;
import java.util.List;


public class AlgorithmSolutions {

    // Function to merge overlapping intervals in a sorted array
    public List<Interval> mergeIntervals(List<Interval> intervals) {
        List<Interval> result = new ArrayList<>();

        if (intervals.isEmpty()) {
            return result;
        }

        Interval currentInterval = intervals.get(0);

        for (int i = 1; i < intervals.size(); i++) {
            Interval nextInterval = intervals.get(i);

            if (currentInterval.end >= nextInterval.start) {
                // Merge the intervals
                currentInterval.end = Math.max(currentInterval.end, nextInterval.end);
            } else {
                // Add the current interval to the result and update the current interval
                result.add(currentInterval);
                currentInterval = nextInterval;
            }
        }

        // Add the last interval to the result
        result.add(currentInterval);

        return result;
    }

    // Function to find the subarray with maximum XOR
    public int maxSubarrayXOR(int[] arr, int N) {
        int maxXOR = 0;
        int prefixXOR = 0;
        TrieNode root = new TrieNode();

        for (int i = 0; i < N; i++) {
            prefixXOR ^= arr[i];
            insert(root, prefixXOR);
            int currentXOR = query(root, prefixXOR);
            maxXOR = Math.max(maxXOR, currentXOR);
        }

        return maxXOR;
    }

    // TrieNode class for XOR trie
    static class TrieNode {
        TrieNode[] children = new TrieNode[2];
    }

    // Function to insert a number into the XOR trie
    private void insert(TrieNode root, int num) {
        TrieNode node = root;

        for (int i = 31; i >= 0; i--) {
            int bit = (num >> i) & 1;
            if (node.children[bit] == null) {
                node.children[bit] = new TrieNode();
            }
            node = node.children[bit];
        }
    }

    // Function to query the maximum XOR value from the XOR trie
    private int query(TrieNode root, int num) {
        TrieNode node = root;
        int result = 0;

        for (int i = 31; i >= 0; i--) {
            int bit = (num >> i) & 1;
            int oppositeBit = 1 - bit;

            if (node.children[oppositeBit] != null) {
                result |= (1 << i);
                node = node.children[oppositeBit];
            } else {
                node = node.children[bit];
            }
        }

        return result;
    }

    // Interval class to represent intervals
    static class Interval {
        int start;
        int end;

        Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    // Console test
    public static void main(String[] args) {
        // Example usage for merging intervals
        List<Interval> intervals = new ArrayList<>();
        intervals.add(new Interval(1, 3));
        intervals.add(new Interval(2, 6));
        intervals.add(new Interval(8, 10));
        intervals.add(new Interval(15, 18));

        AlgorithmSolutions solution = new AlgorithmSolutions();
        List<Interval> mergedIntervals = solution.mergeIntervals(intervals);
        System.out.println("Merged Intervals:");
        for (Interval interval : mergedIntervals) {
            System.out.println("[" + interval.start + ", " + interval.end + "]");
        }

        // Example usage for finding the subarray with maximum XOR
        int[] arr = {1, 2, 3, 4};
        int N = 4;
        int maxSubarrayXOR = solution.maxSubarrayXOR(arr, N);
        System.out.println("Maximum XOR in a subarray: " + maxSubarrayXOR);
    }
}

