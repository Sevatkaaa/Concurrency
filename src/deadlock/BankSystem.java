package deadlock;

import exceptions.NotEnoughBabloException;

public class BankSystem {
    public static void main(String[] args)  {
        BankAccount a = new BankAccount(500);
        BankAccount b = new BankAccount(2000);

        new Thread(() -> transfer(a, b, 100)).start();
        new Thread(() -> transfer(b, a, 200)).start();
    }

    private static void transfer(BankAccount a, BankAccount b, int money) {
        if (money > a.getBablo()) {
            throw new NotEnoughBabloException(a);
        }
        a.minus(money);
        b.plus(money);
    }
}
