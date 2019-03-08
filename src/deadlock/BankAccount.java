package deadlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {
    private int bablo;
    private Lock lock;
    private int timesFailed;

    public BankAccount(int bablo) {
        this.bablo = bablo;
        lock = new ReentrantLock();
    }

    public void minus(int bakshish) {
        bablo -= bakshish;
    }

    public void plus(int bakshish) {
        bablo += bakshish;
    }

    public void lock() {
        lock.lock();
    }

    public boolean tryLock() {
        return lock.tryLock();
    }

    public boolean tryLock(long timeout, TimeUnit unit) {
        try {
            return lock.tryLock(timeout, unit);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public void unlock() {
        lock.unlock();
    }

    public void fail() {
        timesFailed++;
    }

    public int getBablo() {
        return bablo;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "bablo=" + bablo +
                " lock is locked:" + ((ReentrantLock)lock).isLocked() +
                " timesFailed=" + timesFailed +
                '}';
    }
}
