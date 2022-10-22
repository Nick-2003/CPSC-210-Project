// https://docs.oracle.com/javase/8/docs/api/java/math/BigDecimal.html
// Used BigDecimal to round price value to 2 decimal places

// https://github.students.cs.ubc.ca/CPSC210/TellerApp
// Used as reference for interface

package ui;

import model.Cart;
import model.Inventory;
import model.Item;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Store application
public class StoreApp {

    private static final String JSON_STORE = "./data/inventory.json";

    private Inventory inventory;
    private Cart cart;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: Runs the  application
    public StoreApp() throws FileNotFoundException {
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
                // Add error if cart is not empty
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
        } else if (command.equals("a")) {
            addToInventory();
        } else if (command.equals("r")) {
            removeFromInventory();
        } else if (command.equals("e")) {
            setInventoryItemPrice();
        } else if (command.equals("s")) {
            saveInventory();
        } else if (command.equals("l")) {
            loadInventory();
        } else if (command.equals("i")) {
            printItemList();
        } else if (command.equals("c")) {
            clearItemList();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: Initializes system
    private void init() {
//        name = name;
        inventory = new Inventory("Inventory"); // NEED TO FIND WAY TO SAVE INVENTORY LATER
        cart = new Cart("Cart");
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // EFFECTS: Displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tt -> Item to Cart");
        System.out.println("\tf -> Item from Cart");
        System.out.println("\tp -> Payment");

        System.out.println("\n\ta -> Add to Inventory");
        System.out.println("\tr -> Remove from Inventory");
//        System.out.println("\tn -> Set name for Item in Inventory");
        System.out.println("\te -> Set price for Item in Inventory");
        System.out.println("\ts -> Save Inventory");
        System.out.println("\tl -> Load Inventory");

        System.out.println("\n\ti -> Print Item List");
        System.out.println("\tl -> Clear Item List");
        System.out.println("\tq -> Quit");
    }

    // REQUIRES: Inventory contains required Item
    // MODIFIES: this, Item
    // EFFECTS: Move Item from Inventory to Cart
    private void itemToCart() {

        String itemName = inputItemName();
        int itemQuantity = inputItemQuantity();

        if (this.inventory.getNamedAmount(itemName) >= itemQuantity) {
            double itemPrice = this.inventory.getNamedPrice(itemName);
//            BigDecimal itemPrice = this.inventory.getNamedPrice(itemName);
            Item item = new Item(itemName, itemQuantity, itemPrice);
            this.inventory.takeFromList(item);
            this.cart.putIntoList(item);
            System.out.print("Moved " + itemQuantity + " of " + itemName + " to Inventory\n");
        } else {
            System.out.print("Not enough " + itemName + " in Inventory\n");
        }
    }

    // REQUIRES: Cart contains required Item
    // MODIFIES: this, Item
    // EFFECTS: Move Item from Cart to Inventory
    private void itemFromCart() {

        String itemName = inputItemName();
        int itemQuantity = inputItemQuantity();

        if (this.cart.getNamedAmount(itemName) >= itemQuantity) {
            double itemPrice = this.inventory.getNamedPrice(itemName);
//            BigDecimal itemPrice = this.inventory.getNamedPrice(itemName);
            Item item = new Item(itemName, itemQuantity, itemPrice);
            this.cart.takeFromList(item);
            this.inventory.putIntoList(item);
            System.out.print("Moved " + itemQuantity + " of " + itemName + " to Inventory\n");
        } else {
            System.out.print("Not enough " + itemName + " in Cart\n");
        }
    }

    // REQUIRES: Cart is not empty
    // EFFECTS: Print the receipt for the customer
    private void payment() {
        System.out.print("\nReceipt: ");
        for (Item obj: this.cart.getInternalList()) {
            System.out.print("\n\t" + obj.getName() + ": $" + obj.getPrice() + " * " + obj.getAmount());
        }
        System.out.print("\n\tTotal cost of items: $" + this.cart.totalPrice());
        System.out.print("\n\tTax: $" + this.cart.tax());
        System.out.print("\n\tFinal cost: $" + this.cart.finalPrice() + "\n");
    }

    // MODIFIES: this, Item
    // EFFECTS: Add Item with given inputs to Inventory; if Item exists, take existing price
    private void addToInventory() {

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
    private void removeFromInventory() {

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
//    private void setInventoryItemName() {
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
    private void setInventoryItemPrice() {
        String itemName = inputItemName();

        double itemPrice = this.inventory.getNamedPrice(itemName);
        System.out.print("\nCurrent item price: $" + itemPrice);
        itemPrice = inputItemPrice();
        this.inventory.setNamedPrice(itemName, itemPrice);
        System.out.print("New item price: $" + itemPrice + "\n");
    }

    // EFFECTS: saves Inventory to file
    private void saveInventory() {
        try {
            jsonWriter.open();
            jsonWriter.write(inventory);
            jsonWriter.close();
            System.out.println("Saved " + inventory.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads Inventory from file
    private void loadInventory() {
        try {
            inventory = jsonReader.read();
            System.out.println("Loaded " + inventory.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: Print current ItemList
    private void printItemList() {
        String selectList = inputListString();
        if (selectList.equals("c")) {
            System.out.print("\nCart Items: ");
            for (Item obj: this.cart.getInternalList()) {
                System.out.print("\nName: " + obj.getName());
                System.out.print("\n\tQuantity: " + obj.getAmount());
                System.out.print("\n\tPrice: " + obj.getPrice() + "\n");
            }
        } else {
            System.out.print("\nInventory Items: ");
            for (Item obj: this.inventory.getInternalList()) {
                System.out.print("\nName: " + obj.getName());
                System.out.print("\n\tQuantity: " + obj.getAmount());
                System.out.print("\n\tPrice: " + obj.getPrice() + "\n");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Clear current ItemList
    private void clearItemList() {
        String selectList = inputListString();
        Boolean shouldClear = inputBool();
        if (!shouldClear) {
            System.out.print("\nReturning to screen\n");
        } else if (shouldClear && (selectList.equals("c"))) {
            this.cart.clear();
            System.out.print("\nCart cleared\n");
        } else if (shouldClear && (selectList.equals("i"))) {
            this.inventory.clear();
            System.out.print("\nInventory cleared\n");
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

    // EFFECTS: Input String for List
    private String inputListString() {
        String inputS = "";
        System.out.print("\nSelect which list to clear: ");
        while (!(inputS.equals("c") || inputS.equals("i"))) {
            System.out.println("\n\tc -> Cart");
            System.out.println("\n\ti -> Inventory");
            inputS = input.next();
            inputS = inputS.toLowerCase();
        }
        return inputS;
    }
}
