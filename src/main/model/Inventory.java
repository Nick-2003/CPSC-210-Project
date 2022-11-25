// https://docs.oracle.com/javase/8/docs/api/java/math/BigDecimal.html
// Used BigDecimal to round price value to 2 decimal places

package model;

import exceptions.NegativeValueException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

// Represents a named inventory that contains a list of items
public class Inventory extends ItemList {

    // EFFECTS: Creates new empty Inventory
    public Inventory(String name) {
        super(name); // Constructor for ItemList
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
                EventLog.getInstance().logEvent(new Event(obj.getName()
                        + " price changed to " + obj.getPrice() + " in " + this.getName()));
                break;
            }
        }
    }
}
