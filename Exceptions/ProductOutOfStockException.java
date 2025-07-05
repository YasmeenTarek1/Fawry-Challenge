package Exceptions;

public class ProductOutOfStockException extends Exception {
    public ProductOutOfStockException(String productName, int available, int requested) {
        super("Only " + available + " of '" + productName + "' available, but " + requested + " requested.");
    }
}