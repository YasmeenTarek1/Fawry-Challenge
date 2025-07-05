public class noShippingBehaviour implements Shippable {
    private final double weight;

    public noShippingBehaviour() {
        this.weight = 0;
    }

    @Override
    public boolean isShippable() {
        return false;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public double getShippingFee() {
        return 0;
    }

}
