package model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

//public class Cart implements ItemList {
public class Cart extends ItemList {
    public static final double TAX = 0.05;
//    public static final BigDecimal TAX = BigDecimal.valueOf(0.05);

//    private ArrayList<Item> internalList; // Which one would be more important: Ordering or non-duplicates?
//
//    public Cart() {
//        this.internalList = new ArrayList<Item>();
//    }

    // REQUIRES: ItemList is non-empty
    // EFFECTS: Returns total cost of items in list
    public double totalPrice() {
        double totalPrice = 0;
//        BigDecimal totalPrice = BigDecimal.valueOf(0);
        for (Item obj: this.internalList) {
            double priceTotal = obj.getAmount() * obj.getPrice();
            priceTotal = BigDecimal.valueOf(priceTotal).setScale(2, RoundingMode.HALF_UP).doubleValue();
//            priceTotal = obj.getPrice().multiply(BigDecimal.valueOf(obj.getAmount()))
//            .setScale(2, RoundingMode.HALF_UP);
            totalPrice += priceTotal;
//            totalPrice = totalPrice.add(priceTotal);
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

    // EFFECTS: Clears list of items
    public void clear() {
        this.internalList = new ArrayList<Item>();
    }

    public ArrayList<Item> getInternalList() {
        return internalList;
    }
}
