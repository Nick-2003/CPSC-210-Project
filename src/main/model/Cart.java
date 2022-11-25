// https://docs.oracle.com/javase/8/docs/api/java/math/BigDecimal.html
// Used BigDecimal to round price value to 2 decimal places

package model;

import java.math.BigDecimal;
import java.math.RoundingMode;

// Represents a named cart that contains a list of items
public class Cart extends ItemList {
    public static final double TAX = 0.05;

    // EFFECTS: Creates new empty Cart
    public Cart(String name) {
        super(name); // Constructor for ItemList
    }

    // REQUIRES: ItemList is non-empty
    // EFFECTS: Returns total cost of items in list
    public double totalPrice() {
        double totalPrice = 0;
        for (Item obj: this.internalList) {
            double priceTotal = obj.getAmount() * obj.getPrice();
            priceTotal = BigDecimal.valueOf(priceTotal).setScale(2, RoundingMode.HALF_UP).doubleValue();
            totalPrice += priceTotal;
        }
        return totalPrice;
    }

    // REQUIRES: ItemList is non-empty
    // EFFECTS: Returns tax for items in list
    public double tax() {
        double taxation = totalPrice() * TAX;
        taxation = BigDecimal.valueOf(taxation).setScale(2, RoundingMode.HALF_UP).doubleValue();
//        BigDecimal taxation = totalPrice().multiply(TAX).setScale(2, RoundingMode.HALF_UP);
        return taxation;
    }

    // REQUIRES: ItemList is non-empty
    // EFFECTS: Returns final price for items in list
    public double finalPrice() {
        double finalPrice = totalPrice() + tax();
        finalPrice = BigDecimal.valueOf(finalPrice).setScale(2, RoundingMode.HALF_UP).doubleValue();
//        BigDecimal finalPrice = totalPrice().add(tax()).setScale(2, RoundingMode.HALF_UP);
        return finalPrice;
    }
}
