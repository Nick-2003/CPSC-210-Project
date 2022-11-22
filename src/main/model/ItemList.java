package model;

import exceptions.NotEnoughItemsException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Objects;

public class ItemList implements Writable {

    protected String name;
    protected ArrayList<Item> internalList; // Which one would be more important: Ordering or non-duplicates?

    // EFFECTS: Creates new empty ItemList
    public ItemList(String name) {
        this.name = name;
        this.internalList = new ArrayList<>();
    }

    // EFFECTS: returns an unmodifiable list of Items in this workroom
//    public List<Item> getItems() {
//        return Collections.unmodifiableList(internalList);
//    }
    public ArrayList<Item> getItems() {
//        return Collections.unmodifiableList(internalList);
        return this.internalList;
    }

    // MODIFIES: this
    // EFFECTS: Add to existing quantity of item in list unless it's not there, in which case insert new item to list
    public void putIntoList(Item obj) throws NotEnoughItemsException {
        if (obj.getAmount() > 0) {
            boolean listChange = false;
            for (Item obi : this.internalList) {
                if (Objects.equals(obi.getName(), obj.getName())) {
                    obi.changeQuantity(obj.getAmount());
                    listChange = true;
                    break;
                }
            }
            if (!listChange) {
                this.internalList.add(obj);
            }
            EventLog.getInstance().logEvent(new Event(obj.getAmount() + " " + obj.getName()
                    + " put into " + this.name));
        }
    }

    // MODIFIES: this
    // EFFECTS: Takes item of given quantity from list unless it's not in the list or quantity =< 0, in which case
    // return false
    public boolean takeFromList(Item obj) throws NotEnoughItemsException {
        boolean result = false;
        for (Item obi: this.internalList) {
            if (Objects.equals(obi.getName(), obj.getName())) {
                if (obi.getAmount() > obj.getAmount()) {
                    moreThanZero(obj);
                    obi.changeQuantity(- obj.getAmount());
                    result = true;
                    break;
                } else if (obi.getAmount() == obj.getAmount()) {
                    moreThanZero(obj);
                    this.internalList.remove(obi);
                    result = true;
                    break;
                } else {
                    throw new NotEnoughItemsException();
                }
            }
        }
        return result;
    }

    private void moreThanZero(Item obj) {
        if (obj.getAmount() > 0) {
            EventLog.getInstance().logEvent(new Event(obj.getAmount() + " " + obj.getName()
                    + " taken from " + this.name));
        }
    }

    // REQUIRES: name is present in ItemList
    // EFFECTS: Returns if Item of given name is in ItemList
    public boolean getNamed(String name) {
        boolean inList = false;
        for (Item obj: this.internalList) {
            if (Objects.equals(obj.getName(), name)) {
                inList = true;
                break;
            }
        }
        return inList;
    }

    // REQUIRES: name is present in ItemList
    // EFFECTS: Returns amount of Item of given name
    public int getNamedAmount(String name) {
        int available = 0;
        for (Item obj: this.internalList) {
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
        for (Item obj: this.internalList) {
            if (Objects.equals(obj.getName(), name)) {
                cost = obj.getPrice();
                break;
            }
        }
        return cost;
    }

    // MODIFIES: this
    // EFFECTS: Clears list of items
    public void clear() {
        this.internalList = new ArrayList<>();
        EventLog.getInstance().logEvent(new Event(this.name + " cleared"));
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Item> getInternalList() {
        return this.internalList;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("items", itemsToJson());
        EventLog.getInstance().logEvent(new Event(this.name + " has items saved"));
        return json;
    }

    // EFFECTS: returns Items in this ItemList as a JSON array
    private JSONArray itemsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Item t : this.internalList) {
            jsonArray.put(t.toJson());
        }
        return jsonArray;
    }
}
