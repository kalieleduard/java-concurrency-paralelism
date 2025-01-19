package br.com.example.concurrencyparalelism;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ScheduledTask {
    public static void main(String[] args) {
        // If we do not want to finish the application, we don't close the executorService, otherwise,
        // we would close with try-with-resources
        final var executorService = Executors.newScheduledThreadPool(1);
        final Runnable task = () -> System.out.println("Hello from thread " + Thread.currentThread().getName());
        executorService.scheduleAtFixedRate(task, 0, 2, TimeUnit.SECONDS);
    }
}
