// https://docs.oracle.com/javase/8/docs/api/java/math/BigDecimal.html
// Used BigDecimal to round price value to 2 decimal places

package model;

import org.json.JSONObject;
import persistence.Writable;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Item implements Writable {
//    private static int nextItemId = 1;  // tracks id of next account created
//    private int id;
    private String name;
    private int amount;
    private double price;

    // REQUIRES: name has non-zero length
    // MODIFIES: this
    // EFFECTS: Create new item with name, quantity and price to 2 decimal places
    public Item(String name, int amount, double price) {
        this.name = name;
        this.amount = amount;
        this.price = BigDecimal.valueOf(price).setScale(2, RoundingMode.HALF_UP).doubleValue();
//        this.sale = false; // Check if item is on sale or not
    }

    // REQUIRES: quantity - num >= 0
    // MODIFIES: this
    // EFFECTS: Changes the quantity of the item by num
    public void changeQuantity(int num) {
        this.amount += num;
    }

//    // EFFECTS: Get the id of the item
//    public int getId() {
//        return this.id;
//    }

    // EFFECTS: Get the name of the item
    public String getName() {
        return this.name;
    }

    // EFFECTS: Get the quantity of the item
    public int getAmount() {
        return this.amount;
    }

    // EFFECTS: Get the price of the item
    public double getPrice() {
        return this.price;
    }

    // MODIFIES: this
    // EFFECTS: Changes the name of the item to given name
    public void setName(String name) {
        this.name = name;
    }

    // MODIFIES: this
    // EFFECTS: Changes the price of the item to given price
    public void setPrice(double price) {
        this.price = BigDecimal.valueOf(price).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("amount", amount);
        json.put("price", price);
        return json;
    }
}
