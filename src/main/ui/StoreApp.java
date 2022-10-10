package ui;

import model.Cart;
import model.Inventory;
import model.Item;

import java.math.BigDecimal;
import java.util.Scanner;

// Store application
public class StoreApp {

    private String name;
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
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("d")) {
//            doDeposit();
        } else if (command.equals("w")) {
//            doWithdrawal();
        } else if (command.equals("t")) {
//            doTransfer();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: Initializes system
    private void init() {
        name = name;
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
        System.out.println("\tt -> transfer");
        System.out.println("\tq -> Quit");
    }

    public void itemToCart(String itemName, int itemQuantity) {
        if (this.inventory.getNamedAmount(itemName) >= itemQuantity) {
            double itemPrice = this.inventory.getNamedPrice(itemName);
//            BigDecimal itemPrice = this.inventory.getNamedPrice(itemName);
            Item item = new Item(itemName, itemQuantity, itemPrice);
            this.inventory.takeFromList(item);
            this.cart.putIntoList(item);
        }
    }

    public void itemFromCart(String itemName, int itemQuantity) {
        if (this.inventory.getNamedAmount(itemName) >= itemQuantity) {
            double itemPrice = this.inventory.getNamedPrice(itemName);
//            BigDecimal itemPrice = this.inventory.getNamedPrice(itemName);
            Item item = new Item(itemName, itemQuantity, itemPrice);
            this.cart.takeFromList(item);
            this.inventory.putIntoList(item);
        }
    }
}
