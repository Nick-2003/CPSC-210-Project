package model;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Collection;
import java.util.HashSet;

public class Inventory implements ItemList {

//    ArrayList<Item> internalList; // Which one would be more important: Ordering or non-duplicates?
    Collection<Item> internalList;

    public Inventory(ArrayList<Item> internalList) {
//        this.internalList = new ArrayList<Item>();
        this.internalList = new HashSet();
    }

    // MODIFIES: this
    // EFFECTS: inserts item into set unless it's already there, in which case add to existing quantity
    public void insert(Item obj) {
        if (!internalList.contains(obj)) {
            internalList.add(obj);
        }
    }

    // MODIFIES: this
    // EFFECTS: if the item is in the item list, them remove it from the item list.
    //          Otherwise, do nothing
    public void remove(Item obj) {
//        if (internalList.contains(obj)) {
//            internalList.remove(obj);
//        }
        internalList.remove(obj);
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
