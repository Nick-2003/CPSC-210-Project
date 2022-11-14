// https://docs.oracle.com/javase/8/docs/api/java/math/BigDecimal.html
// Used BigDecimal to round price value to 2 decimal places

package model;

import exceptions.NegativeValueException;
import exceptions.NotEnoughItemsException;
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

    // REQUIRES: amount >= 0, price >= 0
    // MODIFIES: this
    // EFFECTS: Create new item with name, quantity and price to 2 decimal places
    public Item(String name, int amount, double price) throws NegativeValueException {
        if (amount < 0 || price < 0) {
            throw new NegativeValueException();
        }
        this.name = name;
        this.amount = amount;
        this.price = BigDecimal.valueOf(price).setScale(2, RoundingMode.HALF_UP).doubleValue();
//        this.sale = false; // Check if item is on sale or not
    }

    // REQUIRES: quantity - num >= 0
    // MODIFIES: this
    // EFFECTS: Changes the quantity of the item by num
    public void changeQuantity(int num) throws NotEnoughItemsException {
        if ((this.amount += num) < 0) {
            this.amount -= num;
            throw new NotEnoughItemsException();
        }
    }

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
    public void setPrice(double price) throws NegativeValueException {
        if (price < 0) {
            throw new NegativeValueException();
        }
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

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (o == null || getClass() != o.getClass()) {
//            return false;
//        }
//        Item item = (Item) o;
//        return Double.compare(item.price, price) == 0 && Objects.equals(name, item.name);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(name, price);
//    }
}
