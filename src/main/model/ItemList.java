package model;

public interface ItemList {
    // MODIFIES: this
    // EFFECTS: inserts item into list/set unless it's already there, in which case do nothing
    void putIntoList(Item obj);

    // MODIFIES: this
    // EFFECTS: if the item is in the item list, them remove it from the item list, otherwise do nothing
    void remove(Item obj);

    boolean takeFromList(Item obj);
}
