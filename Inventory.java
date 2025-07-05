import Exceptions.ProductExpiredException;
import Exceptions.ProductNotFoundException;
import Exceptions.ProductOutOfStockException;

import java.util.HashMap;

public class Inventory {
    private final HashMap<String, ProductComponent> products;

    public Inventory() {
        products = new HashMap<>();
    }

    public void addProduct(ProductComponent product) {
        products.put(product.getName().toLowerCase(), product);
    }

    public ProductComponent getProduct(String productName) {
        return products.get(productName.toLowerCase());
    }

    public void reduceStock(String productName, int quantity) throws ProductOutOfStockException, ProductNotFoundException, ProductExpiredException {
        ProductComponent product = getProduct(productName);
        if(product == null)
            throw new ProductNotFoundException(productName);

        product.reduceStock(quantity);
    }

    public void increaseStock(String productName, int quantity){
        ProductComponent product = getProduct(productName);
        product.increaseStock(quantity);
    }

    public double getProductShippingFees(String productName){
        ProductComponent product = getProduct(productName);
        if(product instanceof Shippable)
            return ((Shippable) product).getShippingFee();
        return 0;
    }

    public double getProductPrice(String productName){
        return getProduct(productName).getPrice();
    }

    public boolean isExpiredProduct(String productName) {
        ProductComponent product = getProduct(productName);
        if(product instanceof Expirable)
            return ((Expirable) product).isExpired();
        return false;
    }

    public void printInventory() {
        for (ProductComponent p : products.values()) {
            System.out.println(p);
        }
    }
}
