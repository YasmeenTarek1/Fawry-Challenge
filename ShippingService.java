import java.util.ArrayList;

public class ShippingService {
    private final ArrayList<Shippable> shippableProducts;
    private double totalWeight;

    public ShippingService() {
        this.shippableProducts = new ArrayList<>();
        this.totalWeight = 0.0;
    }

    public void addShippableProduct(Shippable product) {
        shippableProducts.add(product);
        totalWeight += product.getWeight();
    }

    public double getTotalWeight() {
        return totalWeight;
    }
}
