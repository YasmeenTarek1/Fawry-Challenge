import Exceptions.ProductExpiredException;
import Exceptions.ProductOutOfStockException;

public interface ProductComponent {
    String getName();
    double getPrice();
    int getStockQuantity();
    void reduceStock(int requiredQuantity) throws ProductOutOfStockException, ProductExpiredException;
    void increaseStock(int quantity);
    String toString();
}