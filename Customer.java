import Exceptions.BalanceNotSufficientException;
import Exceptions.EmptyCartException;
import Exceptions.ProductExpiredException;

public class Customer {
    public Cart myCart;
    private double balance;

    public Customer(Inventory inventory, double balance) {
        this.myCart = new Cart(inventory);
        this.balance = balance;
        System.out.println("👤 Customer created with balance: $" + String.format("%.2f", balance));
    }

    public void checkout() throws BalanceNotSufficientException, ProductExpiredException, EmptyCartException {
        // check if cart is empty
        if(myCart.getCartSize() == 0)
            throw new EmptyCartException();

        // check on expired products
        myCart.checkForExpiredProducts();

        double subTotal = myCart.getSubTotal();
        double shippingFees = myCart.getShippingFees();
        double paidAmount = subTotal + shippingFees;

        // check if balance is sufficient
        if(paidAmount > balance)
            throw new BalanceNotSufficientException(balance, paidAmount);

        // send shippable products to shipping service
        myCart.sendShippableProductsToShippingService();

        // print checkout receipt
        myCart.printCheckoutReceipt();

        // deduct amount from balance
        balance -= paidAmount;

        System.out.println("\n✅ Checkout completed successfully!");
        System.out.println("💰 Remaining balance: $" + String.format("%.2f", balance));
    }

    public Cart getMyCart() {
        return myCart;
    }

    public double getBalance() {
        return balance;
    }
}
