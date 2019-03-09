package deadlock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class BankSystem {
    public static void main(String[] args) throws InterruptedException {
        BankAccount a = new BankAccount(500);
        BankAccount b = new BankAccount(2000);

        Random random = new Random();

        ExecutorService executor = Executors.newFixedThreadPool(3);
        List<Future<Boolean>> results = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            results.add(executor.submit(new TransferService(a, b, random.nextInt(100))));
            results.add(executor.submit(new TransferService(b, a, random.nextInt(200))));
        }

        for (int i = 0; i < results.size(); i++) {
            try {
                System.out.println("Result " + i + " is " + results.get(i).get());
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();

        System.out.println();
        System.out.println(a.getBablo() + " on account " + a.toString());
        System.out.println(b.getBablo() + " on account " + b.toString());

    }


}
