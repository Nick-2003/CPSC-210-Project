// https://docs.oracle.com/javase/8/docs/api/java/math/BigDecimal.html
// Used BigDecimal to round price value to 2 decimal places

package model;

import exceptions.NegativeValueException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

//public class Inventory implements ItemList {
public class Inventory extends ItemList {

//    private ArrayList<Item> internalList; // internalList from ItemList used instead
////    private HashSet<Item> internalList; // Do not need to override

    // EFFECTS: Creates new empty Inventory
    public Inventory(String name) {
        super(name); // Constructor for ItemList

//        this.internalList = new ArrayList<Item>();
//        this.internalList = new HashSet<Item>();
    }

    // EFFECTS: returns an unmodifiable list of Items in this workroom
    public List<Item> getItems() {
        return Collections.unmodifiableList(internalList);
    }

    // REQUIRES: name is present in ItemList
    // MODIFIES: this, Item
    // EFFECTS: Change name of Item of given name to newName
    public void setNewName(String name, String newName) {
        for (Item obj: internalList) {
            if (Objects.equals(obj.getName(), name)) {
                obj.setName(newName);
                break;
            }
        }
    }

    // REQUIRES: name is present in ItemList, price >= 0
    // MODIFIES: this, Item
    // EFFECTS: Change price of Item of given name
    public void setNamedPrice(String name, double price) throws NegativeValueException {
        if (price < 0) {
            throw new NegativeValueException();
        }
        for (Item obj: internalList) {
            if (Objects.equals(obj.getName(), name)) {
                obj.setPrice(BigDecimal.valueOf(price).setScale(2, RoundingMode.HALF_UP).doubleValue());
                break;
            }
        }
    }
}
