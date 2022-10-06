package model;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Collection;
import java.util.HashSet;

public class Inventory implements ItemList {

//    ArrayList<Item> internalList; // Which one would be more important: Ordering or non-duplicates?
    Collection<Item> internalList; // Do not need to override

    public Inventory() {
//        this.internalList = new ArrayList<Item>();
        this.internalList = new HashSet();
    }

    // MODIFIES: this
    // EFFECTS: inserts item into set unless it's already there, in which case add to existing quantity
    public void insert(Item obj) {
        if (internalList.contains(obj)) {
            for (Item obi: internalList) {
                if (Objects.equals(obi.getName(), obj.getName())) {
                    obi.changeQuantity(obj.getQuantity());
                    break;
                }
            }
        } else {
            internalList.add(obj);
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
    // EFFECTS: Takes item from set unless it's not in the list or quantity =< 0, in which case return false
    public boolean take(Item obj) {
        boolean result = false;
        if (internalList.contains(obj)) {
            for (Item obi: internalList) {
                if (Objects.equals(obi.getName(), obj.getName()) && (obi.getQuantity() >= obj.getQuantity())) {
                    obi.changeQuantity(- obj.getQuantity());
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    // REQUIRES: name is present in ItemList, quantity =/= 0
    // EFFECTS: Returns if Item of given name is available for purchase
    public boolean available(String name) {
        boolean result = false;
        for (Item obj: internalList) {
            if ((Objects.equals(obj.getName(), name)) && (obj.getQuantity() > 0)) {
                result = true;
                break;
            }
        }
        return result;
    }
}
