import java.time.LocalDate;
import java.time.LocalDateTime;

import Exceptions.ProductExpiredException;
import Exceptions.ProductOutOfStockException;

public class ExpirableDecorator extends ProductDecorator implements Expirable {
    private LocalDateTime expiryDate;

    public ExpirableDecorator(ProductComponent product, LocalDateTime expiryDate) {
        super(product);
        this.expiryDate = expiryDate;
    }

    @Override
    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    @Override
    public String toString() {
        String expiredStatus = isExpired() ? " ❌ EXPIRED" : " ✅ FRESH";
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