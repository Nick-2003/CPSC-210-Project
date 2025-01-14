package ui.tools;

import ui.models.CartModel;
import ui.models.InventoryModel;
import ui.StoreAppGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static javax.swing.JOptionPane.*;
import static javax.swing.JOptionPane.showMessageDialog;

// Represents a tool for the StoreAppGUI to clear a table
public class ClearTool extends Tool {

    private final InventoryModel inventory;
    private final CartModel cart;

    public ClearTool(StoreAppGUI store, JComponent parent, InventoryModel inventoryStore, CartModel cartStore) {
        super(store, parent);
        this.inventory = inventoryStore;
        this.cart = cartStore;
    }

    // MODIFIES: this
    // EFFECTS: Constructs a button which is then added to the JComponent (parent), which is passed in as a parameter
    protected void createButton(JComponent parent) {
        ToolAction a = new ToolAction("Clear Item List");
        button = new JButton(a);
        addToParent(parent);
    }

    private class ToolAction extends Tool.ToolAction {

        public ToolAction(String name) {
            super(name);
        }

        // EFFECTS: Runs button action
        public void actionPerformed(ActionEvent a) {
            String[] selectionValues = {"Inventory", "Cart"};
            String initialSelection = "Inventory";
            Object selectList = showInputDialog(null, "Select an ItemList to save: ",
                    "Select ItemList", QUESTION_MESSAGE, null, selectionValues, initialSelection);
            if (selectList != null) {
                clearItemList((String) selectList);
            }
        }

        // MODIFIES: this
        // EFFECTS: Clear current ItemList
        private void clearItemList(String selectList) {
            if (selectList.equals("Cart")) {
                cart.clear();
                showMessageDialog(null, "Cart cleared", "Clear successful",
                        INFORMATION_MESSAGE);
            } else {
                inventory.clear();
                showMessageDialog(null, "Inventory cleared", "Clear successful",
                        INFORMATION_MESSAGE);
            }
        }
    }
}
