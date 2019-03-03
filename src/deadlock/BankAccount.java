package deadlock;

public class BankAccount {
    private static int iterableId = 0;

    private int bablo;
    private int id;

    public BankAccount(int bablo) {
        this.bablo = bablo;
        id = iterableId++;
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

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "bablo=" + bablo +
                ", id=" + id +
                '}';
    }
}
