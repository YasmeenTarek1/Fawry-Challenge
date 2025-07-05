import Exceptions.ProductExpiredException;
import Exceptions.ProductNotFoundException;
import Exceptions.ProductOutOfStockException;

import java.util.HashMap;

public class Inventory {
    private final HashMap<String, Product> products;

    public Inventory() {
        products = new HashMap<>();
    }

    public void addProduct(Product product) {
        products.put(product.getName().toLowerCase(), product);
        System.out.println("✓ Added to inventory: " + product.getName());
    }

    public Product getProduct(String productName) {
        return products.get(productName.toLowerCase());
    }

    public void reduceStock(String productName, int quantity) throws ProductOutOfStockException, ProductNotFoundException, ProductExpiredException {
        Product product = getProduct(productName);
        if(product == null)
            throw new ProductNotFoundException(productName);

        product.reduceStock(quantity);
    }

    public void increaseStock(String productName, int quantity){
        getProduct(productName).increaseStock(quantity);
        System.out.println("✓ Stock restored: " + quantity + "x " + productName);
    }

    public double getProductShippingFees(String productName){
        return getProduct(productName).getShippingFees();
    }

    public double getProductSubTotal(String productName){
        return getProduct(productName).getPrice();
    }

    public double getProductPrice(String productName){
        return getProduct(productName).getPrice();
    }

    public boolean isExpiredProduct(String productName) {
        return getProduct(productName).getExpirableBehaviour().isExpirable() && getProduct(productName).getExpirableBehaviour().isExpired();
    }

    public void printInventory() {
        System.out.println("📦 Current Inventory Status:");
        System.out.println("═══════════════════════════════════════════════════════════════════════");

        if(products.isEmpty()) {
            System.out.println("  No products in inventory");
            return;
        }

        for (Product p : products.values()) {
            System.out.println("  " + p);
        }

        System.out.println("═══════════════════════════════════════════════════════════════════════");
    }
}
