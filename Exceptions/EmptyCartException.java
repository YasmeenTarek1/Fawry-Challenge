package Exceptions;

public class EmptyCartException extends Exception {
    public EmptyCartException() {
        super("Cannot proceed to checkout. The cart is empty.");
    }
}