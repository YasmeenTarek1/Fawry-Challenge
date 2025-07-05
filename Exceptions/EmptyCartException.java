package Exceptions;

public class EmptyCartException extends Exception {
    public EmptyCartException() {
        super("❌ Checkout failed: Your cart is empty. Please add items before checkout.");
    }
}