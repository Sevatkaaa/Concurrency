package deadlock;

import exceptions.NotEnoughBabloException;

import java.util.concurrent.TimeUnit;

public class BankSystem {
    public static void main(String[] args) throws InterruptedException {
        BankAccount a = new BankAccount(500);
        BankAccount b = new BankAccount(2000);

        new Thread(() -> transfer(a, b, 100)).start();
        new Thread(() -> transfer(b, a, 200)).start();

        Thread.sleep(1000);

        System.out.println();
        System.out.println(a.getBablo() + " on account " + a.toString());
        System.out.println(b.getBablo() + " on account " + b.toString());

    }

    private static void transfer(BankAccount a, BankAccount b, int money) {
        if (a.tryLock(100, TimeUnit.MILLISECONDS)) {
            try {
                if (b.tryLock(100, TimeUnit.MILLISECONDS)) {
                    try {
                        doTransfer(a, b, money);
                    } finally {
                        b.unlock();
                    }
                } else {
                    System.out.println(String.format("Wasn't able to lock %s", b));
                }
            } finally {
                a.unlock();
            }
        } else {
            System.out.println(String.format("Wasn't able to lock %s", b));
        }
    }

    private static void doTransfer(BankAccount a, BankAccount b, int money) {
        if (money > a.getBablo()) {
            throw new NotEnoughBabloException(a);
        }
        System.out.println(String.format("%d money on 1 account %s from %d", a.getBablo(), a, Thread.currentThread().getId()));
        System.out.println(String.format("%d money on 2 account %s from %d", a.getBablo(), b, Thread.currentThread().getId()));

        a.minus(money);
        b.plus(money);

        System.out.println(String.format("%d money on 1 account %s from %d", a.getBablo(), a, Thread.currentThread().getId()));
        System.out.println(String.format("%d money on 2 account %s from %d", a.getBablo(), b, Thread.currentThread().getId()));
    }
}
