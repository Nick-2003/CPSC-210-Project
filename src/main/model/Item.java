package model;

public class Item {
    private static int nextItemId = 1;  // tracks id of next account created
    private int id;
    private String name;
    private int quantity;
    private double price;

    // REQUIRES: name has non-zero length
    // MODIFIES: this
    // EFFECTS: Create new item with quantity and name
    public Item(String name, int quantity, double price) {
        this.id = nextItemId++; // Should be changed to be automatically set
        this.name = name;
        this.quantity = quantity;
        this.price = price;
//        this.sale = false; // Check if item is on sale or not
    }

    // REQUIRES: quantity - num >= 0
    // MODIFIES: this
    // EFFECTS: Changes the quantity of the item by num
    public void changeQuantity(int num) {
        this.quantity += num;
    }

    // EFFECTS: Get the id of the item
    public int getId() {
        return this.id;
    }

    // EFFECTS: Get the name of the item
    public String getName() {
        return this.name;
    }

    // EFFECTS: Get the quantity of the item
    public int getQuantity() {
        return this.quantity;
    }

    // EFFECTS: Get the price of the item
    public double getPrice() {
        return this.price;
    }

    // MODIFIES: this
    // EFFECTS: Changes the name of the item to given name
    public void setName(String name) {
        this.name = name;
    }
}
