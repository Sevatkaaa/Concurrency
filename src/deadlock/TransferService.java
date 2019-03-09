package deadlock;

import exceptions.NotEnoughBabloException;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class TransferService implements Callable<Boolean> {
    private static Random random = new Random();

    private BankAccount a;
    private BankAccount b;
    private int bablo;

    public TransferService(BankAccount a, BankAccount b, int bablo) {
        this.a = a;
        this.b = b;
        this.bablo = bablo;
    }

    @Override
    public Boolean call() {
        return transfer(a, b, bablo);
    }

    private static Boolean transfer(BankAccount a, BankAccount b, int money) {
        if (a.tryLock(100, TimeUnit.MILLISECONDS)) {
            try {
                Thread.sleep(random.nextInt(50));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                if (b.tryLock(100, TimeUnit.MILLISECONDS)) {
                    try {
                        doTransfer(a, b, money);
                    } finally {
                        b.unlock();
                    }
                } else {
//                    System.out.println(String.format("Wasn't able to lock %s", b));
                    a.fail();
                    return Boolean.FALSE;
                }
            } finally {
                a.unlock();
            }
        } else {
//            System.out.println(String.format("Wasn't able to lock %s", b));
            b.fail();
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    private static void doTransfer(BankAccount a, BankAccount b, int money) {
        if (money > a.getBablo()) {
            throw new NotEnoughBabloException(a);
        }
//        System.out.println(String.format("%d money on 1 account %s from %d", a.getBablo(), a, Thread.currentThread().getId()));
//        System.out.println(String.format("%d money on 2 account %s from %d", a.getBablo(), b, Thread.currentThread().getId()));

        a.minus(money);
        b.plus(money);

//        System.out.println(String.format("%d money on 1 account %s from %d", a.getBablo(), a, Thread.currentThread().getId()));
//        System.out.println(String.format("%d money on 2 account %s from %d", a.getBablo(), b, Thread.currentThread().getId()));
    }
}
