package com.company;

import java.util.Arrays;
import java.util.Scanner;

public class Knapsack {
    private final Integer maxWeight; // max weight
    private int[][] things; // weight, price
    private int countOfThings; // Count of things
    private int[] bestChain; // Best chain
    private int maxPrice = 0; // Max price
    private int iteration = 1; // For threads

    public Knapsack(int maxWeight, int countOfThings) {
        this.maxWeight = maxWeight;
        this.countOfThings = countOfThings;
        things = new int[countOfThings][2];
        bestChain = new int[countOfThings];
        Scanner in = new Scanner(System.in);
        for(int i = 0; i < countOfThings; i++) {
            System.out.println("Enter weight of thing " + i);
            things[i][0] = in.nextInt();
            System.out.println("Enter price of thing " + i);
            things[i][1] = in.nextInt();
        }
        System.out.println("You have entered all weights and prices");
    }

    public void solveKnapsack(int cThread) throws InterruptedException {
        Thread[] thread = new Thread[cThread];
        for(int i = 0; i < thread.length; i++) {
            thread[i] = new KnapsackThread();
        }

        for (Thread value : thread) {
            value.start();
        }

        for (Thread value : thread) {
            value.join();
        }
        System.out.println("Best price: " + maxPrice);
        System.out.println("Chain: " + Arrays.toString(bestChain));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < countOfThings; i++) {
            sb.append("Thing ").append(i).append(": weight = ").append(things[i][0]).append("; price = ").append(things[i][1]).append("\n");
        }
        return sb.toString();
    }

    private class KnapsackThread extends Thread {

        @Override
        public void run() {
            while (iteration < Math.pow(2, countOfThings)) {
                int it = 0;
                synchronized (maxWeight) {
                    it = iteration;
                    iteration++;
                }
                int[] chain = new int[countOfThings];
                int price = 0;
                int weight = 0;
                for(int i = 0; i < countOfThings; i++) {
                    int tmp = it & (1 << i);
                    tmp >>= i;
                    chain[i] = tmp;
                    if (chain[i] == 1) {
                        price += things[i][1];
                        weight += things[i][0];
                    }
                }
                if (weight <= maxWeight && price > maxPrice) {
                    maxPrice = price;
                    bestChain = chain;
                }
            }
        }
    }
}
