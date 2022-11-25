package ui.models;

import exceptions.NegativeValueException;
import model.Inventory;
import model.ItemList;

// Represents a table model for Inventory
public class InventoryModel extends ItemListModel {

    private final Inventory itemList;

    public InventoryModel(ItemList itemList) {
        super(itemList);
        this.itemList = (Inventory) itemList;
    }

    // REQUIRES: name is present in ItemList, price >= 0
    // MODIFIES: this, Item
    // EFFECTS: Change price of Item of given name
    public void setNamedPrice(String name, double price) throws NegativeValueException {
        if (this.itemList.getNamed(name)) {
            this.itemList.setNamedPrice(name, price);
            setValueAt(this.getItemList().getNamedPrice(name), getRowByName(name), 2);
        }
    }

    // EFFECTS: Return Inventory associated with InventoryModel
    @Override
    public Inventory getItemList() {
        return this.itemList;
    }
}
