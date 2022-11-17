package ui;

import exceptions.NegativeValueException;
import model.Inventory;
import model.ItemList;

public class InventoryModel extends ItemListModel {

    private Inventory itemList;

    public InventoryModel(ItemList itemList) {
        super(itemList);
        this.itemList = (Inventory) itemList;
    }

    public void setNamedPrice(String name, double price) throws NegativeValueException {
        if (this.itemList.getNamed(name)) {
            this.itemList.setNamedPrice(name, price);
            setValueAt(this.getItemList().getNamedPrice(name), getRowByName(name), 2);
        }
    }

    @Override
    public Inventory getItemList() {
        return this.itemList;
    }
}
