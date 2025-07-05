import Exceptions.ProductOutOfStockException;

public class Product implements ProductComponent {
    private String name;
    private double price;
    private int stockQuantity;

    public Product(String name, double price, int quantity){
        this.name = name;
        this.price = price;
        this.stockQuantity = quantity;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public int getStockQuantity() {
        return stockQuantity;
    }

    @Override
    public void reduceStock(int requiredQuantity) throws ProductOutOfStockException {
        if (stockQuantity >= requiredQuantity)
            stockQuantity -= requiredQuantity;
        else
            throw new ProductOutOfStockException(this.name, this.stockQuantity, requiredQuantity);
    }

    @Override
    public void increaseStock(int quantity) {
        stockQuantity += quantity;
    }

    @Override
    public String toString() {
        return name + " (" + stockQuantity + " in stock @ $" + price + ")";
    }
}
