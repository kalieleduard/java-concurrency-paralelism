package br.com.example.concurrencyparalelism;

public class Deadlock {

    final Object lockA = new Object();
    final Object lockB = new Object();

    public void methodA() {
        synchronized (lockA) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("OK -> MethodA");

            synchronized (lockB) {
                System.out.println("here will have a deadlock");
            }
        }
    }

    public void methodB() {
        synchronized (lockB) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("OK -> MethodB");

            synchronized (lockA) {
                System.out.println("here will have a deadlock");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final Deadlock deadlock = new Deadlock();
        Thread thread1 = new Thread(deadlock::methodA);
        Thread thread2 = new Thread(deadlock::methodB);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }
}
