package Exceptions;

public class BalanceNotSufficientException extends Exception {
    public BalanceNotSufficientException(double balance, double required) {
        super("Insufficient balance. Available: $" + balance + ", required: $" + required);
    }
}
