import java.util.ArrayList;

public class ShippingService {
    private final ArrayList<Shippable> shippableProducts;
    private double totalWeight;
    private double shippingFees;

    public ShippingService() {
        this.shippableProducts = new ArrayList<>();
        this.totalWeight = 0.0;
        this.shippingFees = 0.0;
    }

    public void addShippableProduct(Shippable product) {
        shippableProducts.add(product);
        totalWeight += product.getWeight();
        shippingFees += calculateShippingFees(product);
    }

    private double calculateShippingFees(Shippable product) {
        // Example logic: $5 per 1 kg
        return product.getWeight() * 5;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public double getShippingFees() {
        return shippingFees;
    }
}
