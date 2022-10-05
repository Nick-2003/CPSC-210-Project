package model;

public interface ItemList {
    // MODIFIES: this
    // EFFECTS: inserts item into set unless it's already there, in which case do nothing
    void insert(Item obj);

    // MODIFIES: this
    // EFFECTS: if the item is in the item list, them remove it from the item list.
    //          Otherwise, do nothing
    void remove(Item obj);
}
