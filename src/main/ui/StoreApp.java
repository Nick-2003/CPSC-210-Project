package ui;

import model.Cart;
import model.Inventory;
import model.Item;

import java.util.Scanner;

// Store application
public class StoreApp {

//    private String name;

    private Inventory inventory;
    private Cart cart;
    private Scanner input;

    // EFFECTS: Runs the  application
    public StoreApp() {
        runApp();
    }

    // MODIFIES: this
    // EFFECTS: Process user input
    private void runApp() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: Processes user command
    private void processCommand(String command) {
        if (command.equals("t")) {
            itemToCart();
        } else if (command.equals("f")) {
            itemFromCart();
        } else if (command.equals("p")) {
            payment();
        } else if (command.equals("x")) {
            printCart();
        } else if (command.equals("c")) {
            clearCart();
        } else if (command.equals("a")) {
            addToInventory();
        } else if (command.equals("r")) {
            removeFromInventory();
//        } else if (command.equals("n")) {
//            setInventoryItemName();
        } else if (command.equals("s")) {
            setInventoryItemPrice();
        } else if (command.equals("y")) {
            printInventory();
        } else if (command.equals("i")) {
            clearInventory();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: Initializes system
    private void init() {
//        name = name;
        inventory = new Inventory(); // NEED TO FIND WAY TO SAVE INVENTORY LATER
        cart = new Cart();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: Displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tt -> Item to Cart");
        System.out.println("\tf -> Item from Cart");
        System.out.println("\tp -> Payment");
        System.out.println("\tx -> Print Cart");
        System.out.println("\tc -> Clear Cart");

        System.out.println("\n\ta -> Add to Inventory");
        System.out.println("\tr -> Remove from Inventory");
//        System.out.println("\tn -> Set name for Item in Inventory");
        System.out.println("\ts -> Set price for Item in Inventory");
        System.out.println("\ty -> Print Inventory");
        System.out.println("\ti -> Clear Inventory");

        System.out.println("\n\tq -> Quit");
    }

    // REQUIRES: Inventory contains required Item
    // MODIFIES: this, Item
    // EFFECTS: Move Item from Inventory to Cart
    public void itemToCart() {

        String itemName = inputItemName();
        int itemQuantity = inputItemQuantity();

        if (this.inventory.getNamedAmount(itemName) >= itemQuantity) {
            double itemPrice = this.inventory.getNamedPrice(itemName);
//            BigDecimal itemPrice = this.inventory.getNamedPrice(itemName);
            Item item = new Item(itemName, itemQuantity, itemPrice);
            this.inventory.takeFromList(item);
            this.cart.putIntoList(item);
            System.out.print("Moved " + itemQuantity + " of " + itemName + " to Inventory");
        } else {
            System.out.print("Not enough " + itemName + " in Inventory");
        }
    }

    // REQUIRES: Cart contains required Item
    // MODIFIES: this, Item
    // EFFECTS: Move Item from Cart to Inventory
    public void itemFromCart() {

        String itemName = inputItemName();
        int itemQuantity = inputItemQuantity();

        if (this.cart.getNamedAmount(itemName) >= itemQuantity) {
            double itemPrice = this.inventory.getNamedPrice(itemName);
//            BigDecimal itemPrice = this.inventory.getNamedPrice(itemName);
            Item item = new Item(itemName, itemQuantity, itemPrice);
            this.cart.takeFromList(item);
            this.inventory.putIntoList(item);
            System.out.print("Moved " + itemQuantity + " of " + itemName + " to Inventory");
        } else {
            System.out.print("Not enough " + itemName + " in Cart");
        }
    }

    // REQUIRES: Cart is not empty
    // EFFECTS: Print the receipt for the customer
    public void payment() {
        System.out.print("\nReceipt: ");
        for (Item obj: this.cart.getInternalList()) {
            System.out.print("\n\t" + obj.getName() + ": $" + obj.getPrice() + " * " + obj.getAmount());
        }
        System.out.print("\n\tTotal cost of items: $" + this.cart.totalPrice());
        System.out.print("\n\tTax: $" + this.cart.tax());
        System.out.print("\n\tFinal cost: $" + this.cart.finalPrice() + "\n");
    }

    // EFFECTS: Print current Cart
    public void printCart() {
        System.out.print("\nCart Items: ");
        for (Item obj: this.cart.getInternalList()) {
            System.out.print("\nName: " + obj.getName());
            System.out.print("\n\tQuantity: " + obj.getAmount());
            System.out.print("\n\tPrice: " + obj.getPrice());
        }
    }

    // MODIFIES: this
    // EFFECTS: Clear current Cart
    public void clearCart() {
        Boolean shouldClear = inputBool();
        if (shouldClear) {
            this.cart.clear();
            System.out.print("\nCart cleared");
        }
    }

    // MODIFIES: this, Item
    // EFFECTS: Add Item with given inputs to Inventory; if Item exists, take existing price
    public void addToInventory() {

        String itemName = inputItemName();
        int itemQuantity = inputItemQuantity();
        double itemPrice;
        if (this.inventory.getNamedAmount(itemName) > 0) {
            itemPrice = this.inventory.getNamedPrice(itemName);
        } else {
            itemPrice = inputItemPrice();
        }

        Item item = new Item(itemName, itemQuantity, itemPrice);
        this.inventory.putIntoList(item);
        System.out.print("\nAdded Item(s): ");
        System.out.print("\n\tName: " + itemName);
        System.out.print("\n\tQuantity: " + itemQuantity);
        System.out.print("\n\tPrice: " + itemPrice + "\n");
    }

    // MODIFIES: this, Item
    // EFFECTS: Remove Item with given inputs from Inventory; if Item exists, take existing price
    public void removeFromInventory() {

        String itemName = inputItemName();
        int itemQuantity = inputItemQuantity();
        double itemPrice;
        if (this.inventory.getNamedAmount(itemName) > 0) {
            itemPrice = this.inventory.getNamedPrice(itemName);
        } else {
            itemPrice = inputItemPrice();
        }

        Item item = new Item(itemName, itemQuantity, itemPrice);
        if (this.inventory.takeFromList(item)) {
            System.out.print("\nRemoved Item(s): ");
            System.out.print("\n\tName: " + itemName);
            System.out.print("\n\tQuantity: " + itemQuantity);
            System.out.print("\n\tPrice: " + itemPrice + "\n");
        } else {
            System.out.print("\n " + itemName + " is not in Inventory \n");
        }
    }

//    // REQUIRES: Item is in Inventory
//    // MODIFIES: this, Item
//    // EFFECTS: Set the name of an existing Item in Inventory
//    public void setInventoryItemName() {
//        String itemName = inputItemName();
//
//        System.out.print("\nNew name for " + itemName + ": ");
//        String itemNewName = inputItemName();
//        this.inventory.setNewName(itemName, itemNewName);
//        System.out.print(itemName + " changed to " + itemNewName);
//    }

    // REQUIRES: Item is in Inventory
    // MODIFIES: this, Item
    // EFFECTS: Set the price of an existing Item in Inventory
    public void setInventoryItemPrice() {
        String itemName = inputItemName();

        double itemPrice = this.inventory.getNamedPrice(itemName);
        System.out.print("\nCurrent item price: $" + itemPrice);
        itemPrice = inputItemPrice();
        this.inventory.setNamedPrice(itemName, itemPrice);
        System.out.print("New item price: $" + itemPrice);
    }

    // EFFECTS: Print current Inventory
    public void printInventory() {
        System.out.print("\nInventory Items: ");
        for (Item obj: this.inventory.getInternalList()) {
            System.out.print("\nName: " + obj.getName());
            System.out.print("\n\tQuantity: " + obj.getAmount());
            System.out.print("\n\tPrice: " + obj.getPrice());
        }
    }

    // MODIFIES: this
    // EFFECTS: Clear current Inventory
    public void clearInventory() {
        Boolean shouldClear = inputBool();
        if (shouldClear) {
            this.inventory.clear();
            System.out.print("\nInventory cleared");
        }
    }

    // EFFECTS: Input Item name
    private String inputItemName() {
        String itemName;
        System.out.print("\nEnter name of item: ");
        itemName = input.next();
        return itemName;
    }

    // EFFECTS: Input Item quantity
    private int inputItemQuantity() {
        int itemQuantity;
        System.out.print("\nEnter quantity of item: ");
        itemQuantity = input.nextInt();
        return itemQuantity;
    }

    // EFFECTS: Input Item price
    private double inputItemPrice() {
        double itemPrice;
        System.out.print("\nEnter price of item: ");
        itemPrice = input.nextDouble();
        return itemPrice;
    }

    // EFFECTS: Input Boolean
    private Boolean inputBool() {
        Boolean bool = false;
        String inputB = "";
        System.out.print("\nShould the list be cleared?: ");
        while (!(inputB.equals("y") || inputB.equals("n"))) {
            System.out.println("\n\ty -> YES");
            System.out.println("\n\tn -> NO");
            inputB = input.next();
            inputB = inputB.toLowerCase();
        }

        if (inputB.equals("y")) {
            bool = true;
        }

        return bool;
    }
}
