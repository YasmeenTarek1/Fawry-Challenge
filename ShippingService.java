import java.util.ArrayList;

public class ShippingService {
    private final ArrayList<Product> shippableProducts;
    private double totalWeight;

    public ShippingService() {
        this.shippableProducts = new ArrayList<>();
        this.totalWeight = 0.0;
    }

    public void addShippableProduct(Product product) {
        shippableProducts.add(product);
        totalWeight += product.getWeight();
    }

    public double getTotalWeight() {
        return totalWeight;
    }
}
