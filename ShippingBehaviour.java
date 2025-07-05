public class ShippingBehaviour implements Shippable {
    private double weight;

    public ShippingBehaviour(double weight) {
        this.weight = weight;
    }

    @Override
    public boolean isShippable() {
        return true;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public double getShippingFee() {
        return weight * 1.5;
    }
}
