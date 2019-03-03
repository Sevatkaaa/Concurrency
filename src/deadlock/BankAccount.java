package deadlock;

public class BankAccount {
    private int bablo;

    public BankAccount(int bablo) {
        this.bablo = bablo;
    }

    public void minus(int bakshish) {
        bablo -= bakshish;
    }

    public void plus(int bakshish) {
        bablo += bakshish;
    }

    public int getBablo() {
        return bablo;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "bablo=" + bablo +
                '}';
    }
}
