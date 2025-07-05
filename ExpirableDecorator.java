import java.time.LocalDate;

import Exceptions.ProductExpiredException;
import Exceptions.ProductOutOfStockException;

public class ExpirableDecorator extends ProductDecorator implements Expirable {
    private LocalDate expiryDate;

    public ExpirableDecorator(ProductComponent product, LocalDate expiryDate) {
        super(product);
        this.expiryDate = expiryDate;
    }

    @Override
    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    @Override
    public String toString() {
        String expiredStatus = isExpired() ? " (EXPIRED)" : "";
        return super.toString() + " [Expires: " + expiryDate + expiredStatus + "]";
    }

    @Override
    public void reduceStock(int requiredQuantity) throws ProductExpiredException, ProductOutOfStockException {
        if (isExpired()) {
            throw new ProductExpiredException("Cannot add expired product to cart: ", getName(), getExpiryDate());
        }
        super.reduceStock(requiredQuantity);
    }
}