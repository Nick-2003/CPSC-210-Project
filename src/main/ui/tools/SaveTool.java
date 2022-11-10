package ui.tools;

import model.Cart;
import model.Inventory;
import persistence.JsonWriter;
import ui.StoreAppGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;

import static javax.swing.JOptionPane.*;
import static ui.StoreAppGUI.JSON_CART;
import static ui.StoreAppGUI.JSON_INV;

public class SaveTool extends Tool {

    private Inventory inventory;
    private Cart cart;
    private JsonWriter jsonWriterInv;
    private JsonWriter jsonWriterCart;

    public SaveTool(StoreAppGUI store, JComponent parent, Inventory inventoryStore, Cart cartStore,
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

        public void actionPerformed(ActionEvent a) {
            String[] selectionValues = { "Cart", "Inventory"};
            String initialSelection = "Inventory";
            Object selectList = showInputDialog(null, "Select an ItemList to save: ",
                    "Select ItemList", QUESTION_MESSAGE, null, selectionValues, initialSelection);
            saveItemList((String) selectList);
        } // Modify to change table accordingly

        // EFFECTS: Save ItemList to file
        private void saveItemList(String selectList) {
            if (selectList.equals("Cart")) {
                try {
                    jsonWriterCart.open();
                    jsonWriterCart.write(cart);
                    jsonWriterCart.close();
                    showMessageDialog(null, "Saved " + cart.getName() + " to " + JSON_CART);
                } catch (FileNotFoundException e) {
                    showMessageDialog(null, "Unable to write to file: " + JSON_CART);
                }
            } else if (selectList.equals("Inventory")) {
                try {
                    jsonWriterInv.open();
                    jsonWriterInv.write(inventory);
                    jsonWriterInv.close();
                    showMessageDialog(null, "Saved " + inventory.getName() + " to " + JSON_INV,
                            "Save successful", INFORMATION_MESSAGE);
                } catch (FileNotFoundException e) {
                    showMessageDialog(null, "Unable to write to file: " + JSON_INV,
                            "FileNotFoundException", ERROR_MESSAGE);
                }
            }
        }
    }
}
