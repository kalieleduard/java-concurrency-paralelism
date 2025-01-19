package br.com.example.concurrencyparalelism;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

public class SpeedTest {

    public static class SafeCounter {
        private final AtomicInteger counter = new AtomicInteger(0);

        public void increment() {
            counter.incrementAndGet();
        }

        public AtomicInteger getCounter() {
            return counter;
        }
    }

    public static class NonSafeCounter {
        private Integer counter = 0;

        public void increment() {
            counter++;
        }

        public int getCounter() {
            return counter;
        }
    }

    public static void main(String[] args) {
        final var init = System.currentTimeMillis();

        final var counter = new SafeCounter();
        final var nonSafeCounter = new NonSafeCounter();

        largeProcess(nonSafeCounter);

        System.out.println(nonSafeCounter.getCounter());

        final var totalExecution = System.currentTimeMillis() - init;
        System.out.println("Total execution time in MILLISECONDS: " + totalExecution);
    }

    public static void largeProcess(final SafeCounter safeCounter) {
        for (int i = 0; i < 1000000000; i++) {
            safeCounter.increment();
        }

        for (int i = 0; i < 1000000000; i++) {
            safeCounter.increment();
        }
    }

    public static void largeProcess(final NonSafeCounter nonSafeCounter) {
        final var thread1 = new Thread(() -> {
            for (int i = 0; i < 1000000000; i++) {
                nonSafeCounter.increment();
            }
        });

        final var thread2 = new Thread(() -> {
            for (int i = 0; i < 1000000000; i++) {
                nonSafeCounter.increment();
            }
        });

        thread1.start();
        thread2.start();
        try {
            thread1.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try {
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
