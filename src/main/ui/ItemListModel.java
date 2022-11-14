// https://tips4java.wordpress.com/2008/11/21/row-table-model/
// Used as reference for model creation

package ui;

import exceptions.NotEnoughItemsException;
import model.Item;
import model.ItemList;

import javax.swing.table.DefaultTableModel;

import static ui.StoreAppGUI.setUpArray;

public class ItemListModel extends DefaultTableModel {

//    private ArrayList<Item> internalList;
    private ItemList itemList;
    private static String[] columnNames = {"Name", "Quantity", "Price"};

    public ItemListModel(ItemList itemList) {
        super(setUpArray(itemList.getInternalList()), columnNames);
        this.itemList = itemList;
    }

    // MODIFIES: this
    // EFFECT: If item is already in itemList, update ItemListModel table, otherwise insert into end of table
    public void addItem(Item item) throws NotEnoughItemsException {
        if (this.itemList.getNamed(item.getName())) {
            itemList.putIntoList(item);
            fireTableRowsUpdated(getRowByName(item), getRowByName(item));
        } else {
            itemList.putIntoList(item);
            fireTableRowsInserted(getRowByName(item), getRowByName(item));
        }
    }

    // MODIFIES: this
    // EFFECT: Takes item of given quantity from list; if item is not removed from itemList, update ItemListModel table,
    // otherwise remove from end of table
    public void removeItem(Item item) throws NotEnoughItemsException {
        itemList.takeFromList(item);
        if (this.itemList.getNamed(item.getName())) {
            fireTableRowsUpdated(getRowByName(item), getRowByName(item));
        } else {
            fireTableRowsDeleted(getRowByName(item), getRowByName(item));
        }
    }

    // EFFECT: Get the row number of the Item with itemName
    private int getRowByName(Item item) {
        String itemName = item.getName();
        for (int i = getRowCount() - 1; i >= 0; --i) {
            if (getValueAt(i, 0).equals(itemName)) {
                // what if value is not unique?
                return i;
            }
        }
        return -1;
    }

    // EFFECT: Get the row number of the Item with itemName
    private int getRowByName(String itemName) {
        for (int i = getRowCount() - 1; i >= 0; --i) {
            if (getValueAt(i, 0).equals(itemName)) {
                // what if value is not unique?
                return i;
            }
        }
        return -1;
    }
}
