import Exceptions.ProductExpiredException;
import Exceptions.ProductNotFoundException;
import Exceptions.ProductOutOfStockException;
import Exceptions.ZeroQuantityException;

import java.util.ArrayList;

public class Cart {
    private ArrayList<CartItem> items;
    private Inventory inventory;
    private ArrayList<Product> shippableProducts;

    public Cart(Inventory inventory) {
        this.items = new ArrayList<>();
        this.inventory = inventory;
        this.shippableProducts = new ArrayList<>();
    }

    public void addItem(String productName, int requiredQuantity) throws ProductOutOfStockException, ProductNotFoundException, ProductExpiredException, ZeroQuantityException {
        if (requiredQuantity <= 0) {
            throw new ZeroQuantityException(productName, requiredQuantity);
        }

        inventory.reduceStock(productName, requiredQuantity);

        // Check if item already exists in cart
        for (CartItem item : items) {
            if (item.getProductName().equalsIgnoreCase(productName)) {
                item.setQuantity(item.getQuantity() + requiredQuantity);
                System.out.println("✓ Updated cart: " + requiredQuantity + "x " + productName + " (Total: " + item.getQuantity() + ")");
                return;
            }
        }

        // Add new item to cart
        items.add(new CartItem(productName, inventory.getProductPrice(productName), requiredQuantity));
        System.out.println("✓ Added to cart: " + requiredQuantity + "x " + productName);

        // Add to shippable products if applicable
        if(inventory.getProduct(productName).getShippableBehaviour().isShippable()) {
            shippableProducts.add(inventory.getProduct(productName));
        }
    }

    public void removeItem(String productName) {
        for (CartItem item : items) {
            if (item.getProductName().equalsIgnoreCase(productName)) {
                inventory.increaseStock(item.getProductName(), item.getQuantity());
                items.remove(item);
                System.out.println("✓ Removed from cart: " + item.getQuantity() + "x " + productName);
                return;
            }
        }
        System.out.println("⚠ Product '" + productName + "' not found in cart");
    }

    public double getSubTotal() {
        double total = 0;
        for (CartItem item : items) {
            total += inventory.getProductSubTotal(item.getProductName()) * item.getQuantity();
        }
        return total;
    }

    public double getShippingFees() {
        double total = 0;
        for (CartItem item : items) {
            total += inventory.getProductShippingFees(item.getProductName()) * item.getQuantity();
        }
        return total;
    }

    public void checkForExpiredProducts() throws ProductExpiredException {
        for (CartItem item : items) {
            if (inventory.isExpiredProduct(item.getProductName())) {
                throw new ProductExpiredException("❌ Checkout failed: Cannot purchase expired product ", item.getProductName(), inventory.getProduct(item.getProductName()).getExpiryDate());
            }
        }
    }

    public void sendShippableProductsToShippingService() {
        if (shippableProducts == null || shippableProducts.isEmpty()) {
            return; // No shipment notice needed
        }

        System.out.println("** Shipment notice **");

        ShippingService shippingService = new ShippingService();
        double totalWeight = 0;

        for (Product product : shippableProducts) {
            shippingService.addShippableProduct(product);
            // Find quantity in cart
            int quantity = 0;
            for (CartItem item : items) {
                if (item.getProductName().equalsIgnoreCase(product.getName())) {
                    quantity = item.getQuantity();
                    break;
                }
            }

            double productWeight = product.getWeight() * quantity;
            totalWeight += productWeight;

            System.out.println(quantity + "x " + product.getName() + " " + (int)(productWeight * 1000) + "g");
        }

        System.out.println("Total package weight " + String.format("%.1f", totalWeight) + "kg");
        System.out.println("\n");
    }

    public void printCheckoutReceipt() {
        System.out.println("** Checkout receipt **");

        for (CartItem item : items) {
            System.out.println(item.getQuantity() + "x " + item.getProductName() + " " + item.getSubTotal());
        }

        System.out.println("----------------------");
        System.out.println("Subtotal " + getSubTotal());
        System.out.println("Shipping " + getShippingFees());
        System.out.println("Amount " + (getSubTotal() + getShippingFees()));
    }

    public int getCartSize() {
        return items.size();
    }

    public ArrayList<CartItem> getItems() {
        return items;
    }

    @Override
    public String toString() {
        if (items.isEmpty()) {
            return "🛒 Cart is empty";
        }

        StringBuilder sb = new StringBuilder("🛒 Cart Contents:\n");
        sb.append("═══════════════════════════════════════\n");

        for (CartItem item : items) {
            sb.append("  ").append(item).append("\n");
        }

        sb.append("═══════════════════════════════════════\n");
        sb.append("Subtotal: $").append(String.format("%.2f", getSubTotal())).append("\n");
        sb.append("Shipping: $").append(String.format("%.2f", getShippingFees())).append("\n");
        sb.append("Total: $").append(String.format("%.2f", getSubTotal() + getShippingFees()));

        return sb.toString();
    }
}
