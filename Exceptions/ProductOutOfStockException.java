package Exceptions;

public class ProductOutOfStockException extends Exception {
    public ProductOutOfStockException(String productName, int available, int requested) {
        super(("❌ Insufficient stock for '" + productName + "'. " +
                "Requested: " + requested + ", Available: " + available +
                ", Shortage: " + (requested - available)));
    }
}