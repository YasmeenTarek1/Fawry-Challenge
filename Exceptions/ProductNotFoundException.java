package Exceptions;

public class ProductNotFoundException extends Exception {
    public ProductNotFoundException(String productName) {
        super("Product '" + productName + "' not found in inventory.");
    }
}