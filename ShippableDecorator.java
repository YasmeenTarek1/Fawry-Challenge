public class ShippableDecorator extends ProductDecorator implements Shippable {
    private double weight;

    public ShippableDecorator(ProductComponent product, double weight) {
        super(product);
        this.weight = weight;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public double getShippingFee() {
        return weight * 1.5;
    }

    @Override
    public double getPrice() {
        return super.getPrice() + getShippingFee();
    }

    @Override
    public String toString() {
        return super.toString() + " [🚚 Shippable: " + String.format("%.1f", weight) +
                "kg, shipping fee: $" + String.format("%.2f", getShippingFee()) + "]";
    }
}
