package model;

public class Item {
    private final int id;
    private int quantity;
    private String name;

    // REQUIRES: name has non-zero length
    // MODIFIES: this
    // EFFECTS: Create new item with quantity and name
    public Item(int id, int quantity, String name) {
        this.id = id; // Should be changed to be automatically set
        this.quantity = quantity;
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public String getName() {
        return this.name;
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

    // REQUIRES: name is present in ItemList
    // EFFECTS:
    public void availableName(String name) {

    }
}
