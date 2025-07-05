package Exceptions;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ProductExpiredException extends Exception {
    public ProductExpiredException(String message, String productName, LocalDateTime expiryDate) {
        super(message + "'" + productName + "' (Expired on: " + expiryDate + ")");
    }
}
