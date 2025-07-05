import Exceptions.*;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Product milk = new Product("Milk", 2.0, 1);
        Product apple = new Product("Apple", 1.5, 10);

        Inventory inventory = new Inventory();
        inventory.addProduct(new ExpirableDecorator(milk, LocalDate.of(2025, 10, 1)));
        inventory.addProduct(new ShippableDecorator(apple, 0.5));

        Customer alice = new Customer(inventory, 150);

        try {
            alice.getMyCart().addItem("Apple", 5);   // okay
            alice.getMyCart().addItem("Milk", 2);    // not enough stock
        }catch (ProductNotFoundException | ProductOutOfStockException | ProductExpiredException ex){
            System.out.println(ex.getMessage());
        }

        try{
            alice.checkout();
        }catch(EmptyCartException | BalanceNotSufficientException | ProductExpiredException ex){
            System.out.println(ex.getMessage());
        }

        inventory.printInventory(); // Remaining stock
    }
}
