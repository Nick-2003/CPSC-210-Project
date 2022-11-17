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
    protected ItemList itemList;
    private static String[] columnNames = {"Name", "Quantity", "Price"};

    public ItemListModel(ItemList itemList) {
        super(setUpArray(itemList.getInternalList()), columnNames);
        this.itemList = itemList;
    }

    // MODIFIES: this
    // EFFECT: If item is already in itemList, update ItemListModel table, otherwise insert into end of table
    public void putIntoList(Item item) throws NotEnoughItemsException {
        if (this.itemList.getNamed(item.getName())) {
            this.itemList.putIntoList(item);
            setValueAt(this.itemList.getNamedAmount(item.getName()), getRowByName(item), 1);
        } else {
            this.itemList.putIntoList(item);
            String[] itemProperty = new String[]{item.getName(), String.valueOf(item.getAmount()),
                    String.valueOf(item.getPrice())};
            addRow(itemProperty);
        }
    }

    // MODIFIES: this
    // EFFECT: Takes item of given quantity from list; if item is not removed from itemList, update ItemListModel table,
    // otherwise remove from end of table
    public boolean takeFromList(Item item) throws NotEnoughItemsException {
        boolean result = false;
        if (this.itemList.takeFromList(item)) {
            result = true;
            if (this.itemList.getNamed(item.getName())) {
                setValueAt(this.itemList.getNamedAmount(item.getName()), getRowByName(item), 1);
            } else {
                removeRow(getRowByName(item));
            }
        }
        return result;
    }

    public ItemList getItemList() {
        return this.itemList;
    }

    // MODIFIES: this
    // EFFECTS: Clears list of items and corresponding table
    public void clear() {
        this.itemList.clear();
        setRowCount(0);
    }

//    public void setTableData(ItemList itemList) {
//        this.itemList = itemList;
//        fireTableDataChanged();
//    }

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
    protected int getRowByName(String itemName) {
        for (int i = getRowCount() - 1; i >= 0; --i) {
            if (getValueAt(i, 0).equals(itemName)) {
                // what if value is not unique?
                return i;
            }
        }
        return -1;
    }
}
