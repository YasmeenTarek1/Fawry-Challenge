package Exceptions;

import java.time.LocalDate;

public class ProductExpiredException extends Exception {
    public ProductExpiredException(String message, String productName, LocalDate expiryDate) {
        super(message + productName + " expired on " + expiryDate + ".");
    }
}
