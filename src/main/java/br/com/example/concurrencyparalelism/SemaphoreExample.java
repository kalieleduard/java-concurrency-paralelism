package br.com.example.concurrencyparalelism;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class SemaphoreExample {
    public static void main(String[] args) {
        final Semaphore semaphore = new Semaphore(5);
        final ExecutorService executorService = Executors.newFixedThreadPool(10);

        final Runnable task = () -> {
            boolean permit = false;
            try {
                permit = semaphore.tryAcquire(1, TimeUnit.SECONDS);
                if (permit) {
                    System.out.println("Semaphore acquired");
                    Thread.sleep(5_000);
                } else {
                    System.out.println("Could not acquire semaphore");
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                if (permit) semaphore.release();
            }

        };

        IntStream.range(0, 10).forEach(i -> executorService.submit(task));
    }
}
