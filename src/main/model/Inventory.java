// https://docs.oracle.com/javase/8/docs/api/java/math/BigDecimal.html
// Used BigDecimal to round price value to 2 decimal places

package model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

//public class Inventory implements ItemList {
public class Inventory extends ItemList {

//    private ArrayList<Item> internalList; // internalList from ItemList used instead
////    private HashSet<Item> internalList; // Do not need to override

    public Inventory() {
        super(); // Constructor for ItemList
//        this.internalList = new ArrayList<Item>();
//        this.internalList = new HashSet<Item>();
    }

    // REQUIRES: name is present in ItemList
    // EFFECTS: Change name of Item of given name to newName
    public void setNewName(String name, String newName) {
        for (Item obj: internalList) {
            if (Objects.equals(obj.getName(), name)) {
                obj.setName(newName);
                break;
            }
        }
    }

    // REQUIRES: name is present in ItemList
    // EFFECTS: Change price of Item of given name
    public void setNamedPrice(String name, double price) {
        for (Item obj: internalList) {
            if (Objects.equals(obj.getName(), name)) {
                obj.setPrice(BigDecimal.valueOf(price).setScale(2, RoundingMode.HALF_UP).doubleValue());
                break;
            }
        }
    }
}
