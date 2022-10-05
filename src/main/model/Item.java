package model;

public class Item {
    private static int nextAccountId = 1;  // tracks id of next account created
    private int id;
    private String name;
    private int quantity;
    private double price;

    // REQUIRES: name has non-zero length
    // MODIFIES: this
    // EFFECTS: Create new item with quantity and name
    public Item(String name, int quantity, double price) {
        this.id = nextAccountId++; // Should be changed to be automatically set
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public double getPrice() {
        return price;
    }

    // MODIFIES: this
    // EFFECTS: Changes the name of the item to name
    public void setName(String name) {
        this.name = name;
    }

    // REQUIRES: quantity - num >= 0
    // MODIFIES: this
    // EFFECTS: Changes the quantity of the item by num
    public void changeQuantity(int num) {
        this.quantity += num;
    }
}
