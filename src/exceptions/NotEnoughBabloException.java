package exceptions;

import deadlock.BankAccount;

public class NotEnoughBabloException extends RuntimeException {
    public NotEnoughBabloException(BankAccount a) {
        super(a.toString());
    }
}
