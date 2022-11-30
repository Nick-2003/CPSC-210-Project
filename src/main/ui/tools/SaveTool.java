package ui.tools;

import persistence.JsonWriter;
import ui.models.CartModel;
import ui.models.InventoryModel;
import ui.StoreAppGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;

import static javax.swing.JOptionPane.*;
import static ui.StoreAppGUI.JSON_CART;
import static ui.StoreAppGUI.JSON_INV;

// Represents a tool for the StoreAppGUI to save an item list from one of the models
public class SaveTool extends Tool {

    private final InventoryModel inventory;
    private final CartModel cart;
    private final JsonWriter jsonWriterInv;
    private final JsonWriter jsonWriterCart;

    public SaveTool(StoreAppGUI store, JComponent parent, InventoryModel inventoryStore, CartModel cartStore,
                    JsonWriter jsonWriterInv, JsonWriter jsonWriterCart) {
        super(store, parent);
        this.inventory = inventoryStore;
        this.cart = cartStore;
        this.jsonWriterInv = jsonWriterInv;
        this.jsonWriterCart = jsonWriterCart;
    }

    // MODIFIES: this
    // EFFECTS: Constructs a button which is then added to the JComponent (parent), which is passed in as a parameter
    protected void createButton(JComponent parent) {
        ToolAction a = new ToolAction("Save Item List");
        button = new JButton(a);
        addToParent(parent);
    }

    private class ToolAction extends Tool.ToolAction {

        public ToolAction(String name) {
            super(name); // Button for AbstractAction is now named "Move to Cart"
        }

        // EFFECTS: Runs button action
        public void actionPerformed(ActionEvent a) {
            String[] selectionValues = {"Inventory", "Cart"};
            String initialSelection = "Inventory";
            Object selectList = showInputDialog(null, "Select an ItemList to save: ",
                    "Select ItemList", QUESTION_MESSAGE, null, selectionValues, initialSelection);
            if (selectList != null) {
                saveItemList((String) selectList);
            }
        }

        // EFFECTS: Save ItemList to file
        private void saveItemList(String selectList) {
            if (selectList.equals("Cart")) {
                try {
                    jsonWriterCart.open();
                    jsonWriterCart.write(cart.getItemList());
                    jsonWriterCart.close();
                    showMessageDialog(null, "Saved " + cart.getItemList().getName() + " to "
                            + JSON_CART, "Save successful", INFORMATION_MESSAGE);
                } catch (FileNotFoundException e) {
                    showMessageDialog(null, "Unable to write to file: " + JSON_CART,
                            "FileNotFoundException", ERROR_MESSAGE);
                }
            } else if (selectList.equals("Inventory")) {
                try {
                    jsonWriterInv.open();
                    jsonWriterInv.write(inventory.getItemList());
                    jsonWriterInv.close();
                    showMessageDialog(null, "Saved " + inventory.getItemList().getName() + " to "
                                    + JSON_INV, "Save successful", INFORMATION_MESSAGE);
                } catch (FileNotFoundException e) {
                    showMessageDialog(null, "Unable to write to file: " + JSON_INV,
                            "FileNotFoundException", ERROR_MESSAGE);
                }
            }
        }
    }
}
