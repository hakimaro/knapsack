package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter max weight: ");
        int maxWeight = in.nextInt();
        System.out.print("Enter count of things: ");
        int countOfThings = in.nextInt();
        System.out.print("Enter count of threads: ");
        int countOfThreads = in.nextInt();
        Knapsack k1 = new Knapsack(maxWeight, countOfThings);
        System.out.println(k1);

        long start = System.currentTimeMillis();
        k1.solveKnapsack(1);
        long end = System.currentTimeMillis();
        System.out.println("Completed without threads: " + (end-start) + "\n");

        start = System.currentTimeMillis();
        k1.solveKnapsack(countOfThreads);
        end = System.currentTimeMillis();
        System.out.println("Completed with " + countOfThreads + "threads: " + (end-start) + "\n");
    }
}
