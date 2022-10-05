package model;

import java.util.ArrayList;

public class Purchases implements ItemList {

    ArrayList<Item> internalList; // Which one would be more important: Ordering or non-duplicates?

    public Purchases(ArrayList<Item> internalList) {
        this.internalList = new ArrayList<Item>();
    }

    // MODIFIES: this
    // EFFECTS: inserts item into set unless it's already there, in which case do nothing
    public void insert(Item obj) {
        if (!internalList.contains(obj)) {
            internalList.add(obj);
        }
    }

    // MODIFIES: this
    // EFFECTS: if the item is in the item list, them remove it from the item list.
    //          Otherwise, do nothing
    public void remove(Item obj) {
        if (internalList.contains(obj)) {
            internalList.remove(obj);
        }
    }

    // REQUIRES: ItemList is non-empty
    // EFFECTS: Returns total price of items in list
    public double totalCost() {
        double totalCost = 0;
        for (Item obj: internalList) {
            double priceTotal = obj.getQuantity() * obj.getPrice();
            totalCost += priceTotal;
        }
        return totalCost;
    }
}
