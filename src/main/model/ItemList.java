package model;

import java.util.ArrayList;
import java.util.Objects;

public class ItemList {

    protected ArrayList<Item> internalList; // Which one would be more important: Ordering or non-duplicates?
//    private HashSet<Item> internalList; // Do not need to override

    public ItemList() {
        this.internalList = new ArrayList<Item>();
    }

    // MODIFIES: this
    // EFFECTS: Add to existing quantity of item in list unless it's not there, in which case insert new item to list
    public void putIntoList(Item obj) {
//        int listSize = internalList.size();
        boolean listChange = false;
        for (Item obi: this.internalList) {
            if (Objects.equals(obi.getName(), obj.getName())) {
                obi.changeQuantity(obj.getAmount());
                listChange = true;
                break;
            }
        }
        if (! listChange) {
            this.internalList.add(obj);
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
                if (obi.getAmount() > obj.getAmount()) {
                    obi.changeQuantity(- obj.getAmount());
                    result = true;
                    break;
                } else if (obi.getAmount() == obj.getAmount()) {
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
    public int getNamedAmount(String name) {
        int available = 0;
        for (Item obj: internalList) {
            if (Objects.equals(obj.getName(), name)) {
                available = obj.getAmount();
                break;
            }
        }
        return available;
    }

    // REQUIRES: name is present in ItemList
    // EFFECTS: Returns price of Item of given name
    public double getNamedPrice(String name) {
        double cost = 0;
//        BigDecimal cost = BigDecimal.valueOf(0);
        for (Item obj: internalList) {
            if (Objects.equals(obj.getName(), name)) {
                cost = obj.getPrice();
//                cost = obj.getPrice().setScale(2, RoundingMode.HALF_UP);
                break;
            }
        }
        return cost;
    }
}
