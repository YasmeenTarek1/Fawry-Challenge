package Exceptions;

public class ZeroQuantityException extends Exception {
    public ZeroQuantityException(String productName, int quantity) {
        super("Cannot add " + productName + " with quantity: " + quantity + ". Quantity must be greater than zero");
    }
}