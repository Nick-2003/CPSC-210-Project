package model;

import java.util.ArrayList;
import java.util.Objects;

public class Cart implements ItemList {
    public static final double TAX = 0.05;

    private ArrayList<Item> internalList; // Which one would be more important: Ordering or non-duplicates?

    public Cart() {
        this.internalList = new ArrayList<Item>();
    }

    // MODIFIES: this
    // EFFECTS: Add to existing quantity of item in list unless it's not there, in which case insert new item to list
    public void putIntoList(Item obj) {
        int listSize = internalList.size();
        for (Item obi: this.internalList) {
            if (Objects.equals(obi.getName(), obj.getName())) {
                obi.changeQuantity(obj.getQuantity());
                break;
            }
        }
        if (this.internalList.size() == listSize) {
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

    // REQUIRES: obj.getQuantity() > 0
    // MODIFIES: this
    // EFFECTS: Takes item of given quantity from set unless it's not in the list or quantity =< 0, in which case
    // return false
    public boolean takeFromList(Item obj) {
        boolean result = false;
        for (Item obi: this.internalList) {
            if (Objects.equals(obi.getName(), obj.getName())) {
                if (obi.getQuantity() > obj.getQuantity()) {
                    obi.changeQuantity(- obj.getQuantity());
                    result = true;
                    break;
                } else if (obi.getQuantity() == obj.getQuantity()) {
                    this.internalList.remove(obi);
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    // REQUIRES: ItemList is non-empty
    // EFFECTS: Returns total cost of items in list
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

    // EFFECTS: Clears list of items
    public void clear() {
        this.internalList = new ArrayList<Item>();
    }

    public ArrayList<Item> getInternalList() {
        return internalList;
    }
}
