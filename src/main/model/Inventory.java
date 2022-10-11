package model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Objects;

//public class Inventory implements ItemList {
public class Inventory extends ItemList {

//    ArrayList<Item> internalList; // Which one would be more important: Ordering or non-duplicates?
////    private HashSet<Item> internalList; // Do not need to override
//
//    public Inventory() {
//        this.internalList = new ArrayList<Item>();
////        this.internalList = new HashSet<Item>();
//    }

    // REQUIRES: name is present in ItemList
    // EFFECTS: Change price of Item of given name
    public void setNamedPrice(String name, double price) {
        for (Item obj: internalList) {
            if (Objects.equals(obj.getName(), name)) {
//                obj.setPrice(price);
                obj.setPrice(BigDecimal.valueOf(price).setScale(2, RoundingMode.HALF_UP).doubleValue());
                break;
            }
        }
    }
}
