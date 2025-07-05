package Exceptions;

public class BalanceNotSufficientException extends Exception {
    public BalanceNotSufficientException(double balance, double required) {
        super("❌ Checkout failed: Insufficient balance. " +
                "Required: $" + String.format("%.2f", required) +
                ", Available: $" + String.format("%.2f", balance) +
                ", Shortage: $" + String.format("%.2f", required - balance));
    }
}
