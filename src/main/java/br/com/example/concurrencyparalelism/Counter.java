package br.com.example.concurrencyparalelism;

import java.util.concurrent.atomic.AtomicInteger;

public class Counter {

    private final AtomicInteger count = new AtomicInteger(0);

    // If we take off synchronized keyword or do not use atomic variables, the variable would be used in all
    // threads at the same time, corrupting the final value with race conditions

    public void increment() {
        count.incrementAndGet();
    }

    public int getValue() {
        return count.intValue();
    }

    static class Main {
        public static void main(String[] args) throws InterruptedException {
            final Counter counter = new Counter();

            final Thread thread1 = new Thread(() -> increment(counter));
            final Thread thread2 = new Thread(() -> increment(counter));
            final Thread thread3 = new Thread(() -> increment(counter));

            thread1.start();
            thread2.start();
            thread3.start();

            thread1.join();
            thread2.join();
            thread3.join();

            System.out.println(counter.getValue());
        }

        public static void increment(final Counter value) {
            for (int i = 0; i < 100000; i++) {
                value.increment();
            }
        }
    }
}