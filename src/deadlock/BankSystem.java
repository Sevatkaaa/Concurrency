package deadlock;

import exceptions.NotEnoughBabloException;

public class BankSystem {
    public static void main(String[] args) throws InterruptedException {
        BankAccount a = new BankAccount(500);
        BankAccount b = new BankAccount(2000);

        new Thread(() -> transfer(a, b, 100)).start();
        new Thread(() -> transfer(b, a, 200)).start();

        Thread.sleep(1000);

        System.out.println(a.getBablo());
        System.out.println(b.getBablo());

    }

    private static void transfer(BankAccount a, BankAccount b, int money) {
        boolean isALower = a.getId() < b.getId();
        BankAccount low = isALower ? a : b;
        BankAccount high = isALower ? b : a;
        synchronized (low) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (high) {
                if (money > a.getBablo()) {
                    throw new NotEnoughBabloException(a);
                }
                System.out.println(String.format("%d money on 1 account from %d", a.getBablo(), Thread.currentThread().getId()));
                System.out.println(String.format("%d money on 2 account from %d", a.getBablo(), Thread.currentThread().getId()));

                a.minus(money);
                b.plus(money);

                System.out.println(String.format("%d money on 1 account from %d", a.getBablo(), Thread.currentThread().getId()));
                System.out.println(String.format("%d money on 2 account from %d", a.getBablo(), Thread.currentThread().getId()));
            }
        }
    }
}
