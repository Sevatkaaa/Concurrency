package deadlock;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BankSystem {
    public static void main(String[] args) throws InterruptedException {
        BankAccount a = new BankAccount(500);
        BankAccount b = new BankAccount(2000);

        Random random = new Random();

        ExecutorService executor = Executors.newFixedThreadPool(3);

        for (int i = 0; i < 5; i++) {
            executor.submit(new TransferService(a, b, random.nextInt(100)));
            executor.submit(new TransferService(b, a, random.nextInt(200)));
        }

        Thread.sleep(1000);

        executor.shutdown();

        System.out.println();
        System.out.println(a.getBablo() + " on account " + a.toString());
        System.out.println(b.getBablo() + " on account " + b.toString());

    }


}
