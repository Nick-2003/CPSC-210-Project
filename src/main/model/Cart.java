package model;

import java.util.ArrayList;

public class Cart implements ItemList {
    public static final double TAX = 0.05;

    ArrayList<Item> internalList; // Which one would be more important: Ordering or non-duplicates?

    public Cart() {
        this.internalList = new ArrayList<Item>();
    }


    // MODIFIES: this
    // EFFECTS: inserts item into set unless it's already there, in which case do nothing
    public void insert(Item obj) {
        if (!this.internalList.contains(obj)) {
            this.internalList.add(obj);

        }
    }

    // MODIFIES: this
    // EFFECTS: if the item is in the item list, them remove it from the item list, otherwise do nothing
    public void remove(Item obj) {
        if (this.internalList.contains(obj)) {
            this.internalList.remove(obj);
        }
    }

    // REQUIRES: ItemList is non-empty
    // EFFECTS: Returns total price of items in list
    public double totalCost() {
        double totalCost = 0;
        for (Item obj: this.internalList) {
            double priceTotal = obj.getQuantity() * obj.getPrice();
            totalCost += priceTotal;
        }
        return totalCost;
    }

    // REQUIRES: ItemList is non-empty
    // EFFECTS: Returns tax for items in list
    public double tax() {
        double taxation = totalCost() * TAX;
        return taxation;
    }

    // REQUIRES: ItemList is non-empty
    // EFFECTS: Returns final price for items in list
    public double finalPrice() {
        double finalPrice = totalCost() + tax();
        return finalPrice;
    }

    public void clear() {
        this.internalList = new ArrayList<Item>();
    }
}
