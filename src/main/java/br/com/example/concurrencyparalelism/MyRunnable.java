package br.com.example.concurrencyparalelism;

// Basic implementation of threads

public class MyRunnable implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName());
            try {
                Thread.sleep((200));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final Runnable runnable1 = new MyRunnable();
        final Runnable runnable2 = new MyRunnable();
        final Thread thread1 = new Thread(runnable1);
        final Thread thread2 = new Thread(runnable2);

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
    }
}
