package model;

import java.util.Objects;
import java.util.Collection;
import java.util.HashSet;

public class Inventory implements ItemList {

    //    ArrayList<Item> internalList; // Which one would be more important: Ordering or non-duplicates?
    private HashSet<Item> internalList; // Do not need to override

    public Inventory() {
//        this.internalList = new ArrayList<Item>();
        this.internalList = new HashSet<Item>();
    }

    // MODIFIES: this
    // EFFECTS: Add to existing quantity of item in list unless it's not there, in which case insert new item to list
    public void putIntoList(Item obj) {
        int listSize = internalList.size();
        boolean listChange = false;
        for (Item obi: this.internalList) {
            if (Objects.equals(obi.getName(), obj.getName())) {
                obi.changeQuantity(obj.getQuantity());
                listChange = true;
                break;
            }
        }
        if (! listChange) {
            this.internalList.add(obj);
        }
    }

    // MODIFIES: this
    // EFFECTS: if the item is in the item list, them remove it from the item list, otherwise do nothing
    public void remove(Item obj) {
//        if (internalList.contains(obj)) {
//            internalList.remove(obj);
//        }
        internalList.remove(obj);
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

    // REQUIRES: name is present in ItemList, quantity > 0
    // EFFECTS: Returns amount of Item of given name
    public int availableAmount(String name) {
        int available = 0;
        for (Item obj: internalList) {
            if (Objects.equals(obj.getName(), name)) {
                available = obj.getQuantity();
                break;
            }
        }
        return available;
    }

    // REQUIRES: name is present in ItemList
    // EFFECTS: Returns price of Item of given name
    public double namedPrice(String name) {
        double cost = 0;
        for (Item obj: internalList) {
            if (Objects.equals(obj.getName(), name)) {
                cost = obj.getPrice();
                break;
            }
        }
        return cost;
    }

    // EFFECTS: Clears list of items
    public void clear() {
        this.internalList = new HashSet();
    }

    public HashSet<Item> getInternalList() {
        return internalList;
    }
}
