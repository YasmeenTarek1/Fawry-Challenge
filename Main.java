import Exceptions.ProductExpiredException;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("🛍️  Welcome to Enhanced Shopping System");
            System.out.println("═══════════════════════════════════════════════════════════");

            // Create inventory
            Inventory inventory = new Inventory();

            // 1. Basic Products
            Product basicProduct1 = new Product("Notebook", 5.99, 100, new noShippingBehaviour(), new noExpiryBehaviour());
            Product basicProduct2 = new Product("Pen", 1.50, 200, new noShippingBehaviour(), new noExpiryBehaviour());

            // 2. Shippable Products
            Product shippableProduct1 = new Product("Laptop", 999.99, 15, new ShippingBehaviour(2.5), new noExpiryBehaviour()); // 2.5kg
            Product shippableProduct2 = new Product("Gaming Chair", 299.99, 8, new ShippingBehaviour(15.0), new noExpiryBehaviour()); // 15kg

            // 3. Expirable Products
            Product expirableProduct1 = new Product("Organic Milk", 4.50, 50,new noShippingBehaviour(), new ExpiryBehaviour(LocalDateTime.now().plusDays(7))); // expires in 7 days
            Product expirableProduct2 = new Product("Bread", 2.25, 30,new noShippingBehaviour(), new ExpiryBehaviour(LocalDateTime.now().minusDays(1))); // expired yesterday

            // 4. Shippable AND Expirable Products
            Product shippableExpirableProduct1 = new Product("Fresh Salmon", 25.99, 12, new ShippingBehaviour(1.2), new ExpiryBehaviour(LocalDateTime.now().plusDays(3)));

            Product shippableExpirableProduct2 = new Product("Cheese Wheel", 89.99, 5, new ShippingBehaviour(3.5), new ExpiryBehaviour(LocalDateTime.now().plusDays(30)));

            // 5. Low stock product for testing out-of-stock exception
            Product lowStockProduct = new Product("Limited Edition Item", 199.99, 2, new noShippingBehaviour(), new noExpiryBehaviour());

            // Add all products to inventory
            System.out.println("\n🏪 Setting up inventory...");
            inventory.addProduct(basicProduct1);
            inventory.addProduct(basicProduct2);
            inventory.addProduct(shippableProduct1);
            inventory.addProduct(shippableProduct2);
            inventory.addProduct(expirableProduct1);
            inventory.addProduct(expirableProduct2);
            inventory.addProduct(shippableExpirableProduct1);
            inventory.addProduct(shippableExpirableProduct2);
            inventory.addProduct(lowStockProduct);

            System.out.println("\n");
            inventory.printInventory();

            // Create customer with sufficient balance
            Customer customer = new Customer(inventory, 2000.0);

            System.out.println("\n🛒 Shopping Session Started - Testing All Corner Cases");
            System.out.println("═══════════════════════════════════════════════════════════");

            // CORNER CASE 1: Test ZeroQuantityException
            System.out.println("\n🧪 TEST 1: Zero Quantity Exception");
            try {
                customer.myCart.addItem("Notebook", 0);
            } catch (Exception e) {
                System.out.println("✓ Caught expected exception: " + e.getMessage());
            }

            // CORNER CASE 2: Test negative quantity (also ZeroQuantityException)
            System.out.println("\n🧪 TEST 2: Negative Quantity Exception");
            try {
                customer.myCart.addItem("Pen", -5);
            } catch (Exception e) {
                System.out.println("✓ Caught expected exception: " + e.getMessage());
            }

            // CORNER CASE 3: Test ProductNotFoundException
            System.out.println("\n🧪 TEST 3: Product Not Found Exception");
            try {
                customer.myCart.addItem("NonExistentProduct", 1);
            } catch (Exception e) {
                System.out.println("✓ Caught expected exception: " + e.getMessage());
            }

            // CORNER CASE 4: Test ProductOutOfStockException
            System.out.println("\n🧪 TEST 4: Product Out Of Stock Exception");
            try {
                customer.myCart.addItem("Limited Edition Item", 5); // Only 2 in stock
            } catch (Exception e) {
                System.out.println("✓ Caught expected exception: " + e.getMessage());
            }

            // CORNER CASE 5: Test ProductExpiredException - Adding expired product
            System.out.println("\n🧪 TEST 5: Product Expired Exception (Adding to cart)");
            try {
                customer.myCart.addItem("Bread", 1); // Expired yesterday
            } catch (Exception e) {
                System.out.println("✓ Caught expected exception: " + e.getMessage());
            }

            // CORNER CASE 6: Test EmptyCartException
            System.out.println("\n🧪 TEST 6: Empty Cart Exception");
            Customer customer2 = new Customer(inventory, 100.0);
            try {
                customer2.checkout(); // Cart is empty
            } catch (Exception e) {
                System.out.println("✓ Caught expected exception: " + e.getMessage());
            }

            // CORNER CASE 7: Test BalanceNotSufficientException
            System.out.println("\n🧪 TEST 7: Balance Not Sufficient Exception");
            Customer poorCustomer = new Customer(inventory, 10.0); // Very low balance
            try {
                poorCustomer.myCart.addItem("Laptop", 1); // Expensive item
                poorCustomer.checkout();
            } catch (Exception e) {
                System.out.println("✓ Caught expected exception: " + e.getMessage());
            }

            // CORNER CASE 8: Test ProductExpiredException during checkout
            System.out.println("\n🧪 TEST 8: Product Expired Exception (During checkout)");

            Product expiringSoon = new Product("Expires Soon", 5.99, 10, new noShippingBehaviour(), new ExpiryBehaviour(LocalDateTime.now().plusSeconds(5)));

            Inventory testInventory = new Inventory();
            testInventory.addProduct(expiringSoon);
            Customer testCustomer = new Customer(testInventory, 100.0);
            testCustomer.myCart.addItem("Expires Soon", 1);

            // Simulate waiting for it to expire
            try {
                Thread.sleep(5_000); // Wait for 5 seconds
                testCustomer.checkout();
            } catch (ProductExpiredException e) {
                System.out.println("Caught expected exception: " + e.getMessage());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // CORNER CASE 9: Test cart item removal
            System.out.println("\n🧪 TEST 9: Cart Item Removal (Valid and Invalid)");
            Customer removalTestCustomer = new Customer(inventory, 500.0);

            removalTestCustomer.myCart.addItem("Notebook", 2);
            System.out.println("Before removal: " + removalTestCustomer.myCart.getCartSize() + " items");

            // Valid removal
            removalTestCustomer.myCart.removeItem("Notebook");
            System.out.println("After valid removal: " + removalTestCustomer.myCart.getCartSize() + " items");

            // Invalid removal (item not in cart)
            removalTestCustomer.myCart.removeItem("NonExistentInCart");

            // CORNER CASE 10: Test case-insensitive product names
            System.out.println("\n🧪 TEST 10: Case Insensitive Product Names");
            Customer caseTestCustomer = new Customer(inventory, 100.0);
            try {
                caseTestCustomer.myCart.addItem("NOTEBOOK", 1); // uppercase
                caseTestCustomer.myCart.addItem("pen", 1); // lowercase
                caseTestCustomer.myCart.addItem("LaPtOp", 1); // mixed case
                System.out.println("✓ Case insensitive product names work correctly");
            } catch (Exception e) {
                System.out.println("✓ Caught exception: " + e.getMessage());
            }

            // CORNER CASE 11: Test adding same item to cart
            System.out.println("\n🧪 TEST 11: Adding Same Item Multiple Times");
            Customer duplicateTestCustomer = new Customer(inventory, 100.0);
            try {
                duplicateTestCustomer.myCart.addItem("Notebook", 2);
                duplicateTestCustomer.myCart.addItem("Notebook", 3); // Adding same item again
                System.out.println("✓ Successfully added same item multiple times");
            } catch (Exception e) {
                System.out.println("✓ Caught exception: " + e.getMessage());
            }

            // SUCCESS CASE
            System.out.println("\n✅ SUCCESS CASE :Adding valid products to cart...");
            customer.myCart.addItem("Notebook", 3);
            customer.myCart.addItem("Pen", 5);
            customer.myCart.addItem("Laptop", 1);
            customer.myCart.addItem("Gaming Chair", 1);
            customer.myCart.addItem("Organic Milk", 2);
            customer.myCart.addItem("Fresh Salmon", 1);
            customer.myCart.addItem("Cheese Wheel", 1);

            System.out.println("\n");
            System.out.println(customer.myCart);

            // Test successful checkout
            System.out.println("\n💳 Proceeding to checkout...");
            System.out.println("═══════════════════════════════════════════════════════════");
            customer.checkout();

            System.out.println("\n📊 Post-checkout inventory status:");
            inventory.printInventory();
        } catch (Exception e) {
            System.err.println("❌ System Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}