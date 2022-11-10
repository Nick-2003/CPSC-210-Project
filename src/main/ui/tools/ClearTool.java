package ui.tools;

import model.Cart;
import model.Inventory;
import ui.StoreAppGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static javax.swing.JOptionPane.*;
import static javax.swing.JOptionPane.showMessageDialog;

public class ClearTool extends Tool {

    private Inventory inventory;
    private JTable inventoryTable;
    private Cart cart;
    private JTable cartTable;

    public ClearTool(StoreAppGUI store, JComponent parent, Inventory inventoryStore, JTable inventoryTable,
                     Cart cartStore, JTable cartTable) {
        super(store, parent);
        this.inventory = inventoryStore;
        this.inventoryTable = inventoryTable;
        this.cart = cartStore;
        this.cartTable = cartTable;
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
            super(name); // Button for AbstractAction is now named "Move to Cart"
        }

        public void actionPerformed(ActionEvent a) {
            String[] selectionValues = { "Cart", "Inventory"};
            String initialSelection = "Inventory";
            Object selectList = showInputDialog(null, "Select an ItemList to save: ",
                    "Select ItemList", QUESTION_MESSAGE, null, selectionValues, initialSelection);
            clearItemList((String) selectList);
        } // Modify to change table accordingly

        // MODIFIES: this,
        // EFFECTS: Clear current ItemList
        private void clearItemList(String selectList) {
            if (selectList.equals("Cart")) {
                cart.clear();
                // Update table here
                System.out.print("\nCart cleared\n");
                showMessageDialog(null, "\nCart cleared\n");
            } else {
                inventory.clear();
                // Update table here
                System.out.print("\nInventory cleared\n");
                showMessageDialog(null, "\nInventory cleared\n");
            }
        }
    }
}
