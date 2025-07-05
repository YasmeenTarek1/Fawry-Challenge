import Exceptions.ProductExpiredException;
import Exceptions.ProductNotFoundException;
import Exceptions.ProductOutOfStockException;

import java.util.ArrayList;

public class Cart {
    private ArrayList<CartItem> items;
    private Inventory inventory;
    private ArrayList<Shippable> shippableProducts;

    public Cart(Inventory inventory) {
        this.items = new ArrayList<>();
        this.inventory = inventory;
        this.shippableProducts = new ArrayList<>();
    }

    public void addItem(String productName, int requiredQuantity) throws ProductOutOfStockException, ProductNotFoundException, ProductExpiredException {
        inventory.reduceStock(productName, requiredQuantity);
        for (CartItem item : items) {
            if (item.getProductName().equalsIgnoreCase(productName)) {
                item.setQuantity(item.getQuantity() + requiredQuantity);
                return;
            }
        }
        items.add(new CartItem(productName, inventory.getProductPrice(productName), requiredQuantity));

        if(inventory.getProduct(productName) instanceof Shippable) {
            shippableProducts.add((Shippable) inventory.getProduct(productName));
        }
    }

    public void removeItem(String productName) {
        for (CartItem item : items) {
            if (item.getProductName().equalsIgnoreCase(productName)) {
                inventory.increaseStock(item.getProductName(), item.getQuantity());
                items.remove(item);
                return;
            }
        }
    }

    public double getSubTotal() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getSubTotal();
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
                throw new ProductExpiredException("Cannot Checkout: ", item.getProductName(), ((Expirable) inventory.getProduct(item.getProductName())).getExpiryDate());
            }
        }
    }

    public void sendShippableProductsToShippingService() {
        if (shippableProducts == null || shippableProducts.isEmpty()) {
            System.out.println("No shippable products in the cart.");
            return;
        }

        System.out.println("Sending shippable products to shipping service:");

        ShippingService shippingService = new ShippingService();
        for (Shippable product : shippableProducts) {
            shippingService.addShippableProduct(product);
            System.out.println("- " + product.getName() + " (Weight: " + product.getWeight() + ")");
        }
    }

    public int getCartSize() {
        return items.size();
    }

    public ArrayList<CartItem> getItems() {
        return items;
    }

    @Override
    public String toString() {
        if (items.isEmpty()) return "Cart is empty.";
        StringBuilder sb = new StringBuilder("Cart Items:\n");
        for (CartItem item : items) {
            sb.append("- ").append(item).append("\n");
        }
        sb.append("SubTotal: ").append(getSubTotal());
        return sb.toString();
    }
}
