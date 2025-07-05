import Exceptions.ProductExpiredException;
import Exceptions.ProductOutOfStockException;

public abstract class ProductDecorator implements ProductComponent {
    protected ProductComponent product;

    public ProductDecorator(ProductComponent product) {
        this.product = product;
    }

    @Override
    public String getName() {
        return product.getName();
    }

    @Override
    public double getPrice() {
        return product.getPrice();
    }

    @Override
    public int getStockQuantity() {
        return product.getStockQuantity();
    }

    @Override
    public void reduceStock(int requiredQuantity) throws ProductOutOfStockException, ProductExpiredException {
        product.reduceStock(requiredQuantity);
    }

    @Override
    public void increaseStock(int quantity) {
        product.increaseStock(quantity);
    }

    @Override
    public String toString() {
        return product.toString();
    }
}