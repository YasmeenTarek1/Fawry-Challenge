import Exceptions.ProductExpiredException;
import Exceptions.ProductOutOfStockException;

import java.time.LocalDateTime;

public class Product {
    private String name;
    private double price;
    private int stockQuantity;
    public Shippable shippableBehaviour;
    public Expirable expirableBehaviour;

    public Product(String name, double price, int quantity, Shippable shippableBehaviour, Expirable expirableBehaviour) {
        this.name = name;
        this.price = price;
        this.stockQuantity = quantity;
        this.shippableBehaviour = shippableBehaviour;
        this.expirableBehaviour = expirableBehaviour;
    }

    public void reduceStock(int requiredQuantity) throws ProductOutOfStockException, ProductExpiredException {
        if (isExpirable() && isExpired())
            throw new ProductExpiredException("Cannot add expired product to cart: ", getName(), getExpiryDate());
        else if (stockQuantity >= requiredQuantity)
            stockQuantity -= requiredQuantity;
        else
            throw new ProductOutOfStockException(this.name, this.stockQuantity, requiredQuantity);
    }

    public void increaseStock(int quantity) {
        stockQuantity += quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double getShippingFees(){
        return shippableBehaviour.getShippingFee();
    }

    public double getWeight(){
        return shippableBehaviour.getWeight();
    }

    public boolean isExpirable() {
        return expirableBehaviour.isExpirable();
    }

    public boolean isExpired() {
        return expirableBehaviour.isExpired();
    }

    public LocalDateTime getExpiryDate() {
        return expirableBehaviour.getExpiryDate();
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public Shippable getShippableBehaviour() {
        return shippableBehaviour;
    }

    public void setShippableBehaviour(Shippable shippableBehaviour) {
        this.shippableBehaviour = shippableBehaviour;
    }

    public Expirable getExpirableBehaviour() {
        return expirableBehaviour;
    }

    public void setExpirableBehaviour(Expirable expirableBehaviour) {
        this.expirableBehaviour = expirableBehaviour;
    }

    @Override
    public String toString() {
        return name + " (" + stockQuantity + " in stock @ $" + String.format("%.2f", price) + ")";
    }
}
