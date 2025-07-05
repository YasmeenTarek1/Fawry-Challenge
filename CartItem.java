public class CartItem {

    private String productName;
    private double unitPrice;
    private int quantity;

    public CartItem(String productName, double unitPrice, int quantity) {
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSubTotal() {
        return unitPrice * quantity;
    }

    @Override
    public String toString() {
        return quantity + "x " + productName + " @ $" + String.format("%.2f", unitPrice) + " each = $" + String.format("%.2f", getSubTotal());
    }
}
