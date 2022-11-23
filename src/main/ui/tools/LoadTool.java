package ui.tools;

import exceptions.NegativeValueException;
import exceptions.NotEnoughItemsException;
import model.Item;
import persistence.JsonReader;
import ui.models.CartModel;
import ui.models.InventoryModel;
import ui.StoreAppGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

import static javax.swing.JOptionPane.*;
import static javax.swing.JOptionPane.showMessageDialog;
import static ui.StoreAppGUI.JSON_CART;
import static ui.StoreAppGUI.JSON_INV;

public class LoadTool extends Tool {

    private InventoryModel inventory;
    private CartModel cart;
    private JsonReader jsonReaderInv;
    private JsonReader jsonReaderCart;

    public LoadTool(StoreAppGUI store, JComponent parent, InventoryModel inventoryStore, CartModel cartStore,
                    JsonReader jsonReaderInv, JsonReader jsonReaderCart) {
        super(store, parent);
        this.inventory = inventoryStore;
        this.cart = cartStore;
        this.jsonReaderInv = jsonReaderInv;
        this.jsonReaderCart = jsonReaderCart;
    }

    // MODIFIES: this
    // EFFECTS: Constructs a button which is then added to the JComponent (parent), which is passed in as a parameter
    protected void createButton(JComponent parent) {
        ToolAction a = new ToolAction("Load Item List");
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
            Object selectList = showInputDialog(null, "Select an ItemList to load: ",
                    "Select ItemList", QUESTION_MESSAGE, null, selectionValues, initialSelection);
            if (selectList != null) {
                loadItemList((String) selectList);
            }
        }

        // MODIFIES: this
        // EFFECTS: Loads ItemList from file
        private void loadItemList(String selectList) {
            if (selectList.equals("Cart")) {
                try {
                    cart.clear();
                    for (Item i: jsonReaderCart.readCart().getInternalList()) {
                        // ITEMS ARE ADDED EVEN BEFORE BEING PUT INTO LIST
                        cart.putIntoList(i);
                    }
                    loadItemListMessage(cart.getItemList().getName(), JSON_CART);
                } catch (IOException | NegativeValueException | NotEnoughItemsException | RuntimeException e) {
                    showMessageDialog(null, new JLabel("<html><p>Unable to read from file: " + JSON_CART
                            + "</p> <p>Error: " + e + "</p> </html>"), "Error occurred", ERROR_MESSAGE);
                }
            } else if (selectList.equals("Inventory")) {
                try {
                    inventory.clear();
                    for (Item i: jsonReaderInv.readInv().getInternalList()) {
                        inventory.putIntoList(i);
                    }
                    loadItemListMessage(inventory.getItemList().getName(), JSON_INV);
                } catch (IOException | NegativeValueException | NotEnoughItemsException | RuntimeException e) {
                    showMessageDialog(null, new JLabel("<html><p>Unable to read from file: " + JSON_INV
                            + "</p> <p>Error: " + e + "</p> </html>"), "Error occurred", ERROR_MESSAGE);
                }
            }
        } // SHOULD UPDATE TABLE ACCORDINGLY
    }

    // EFFECTS: Print item message
    private void loadItemListMessage(String cart, String jsonCart) {
        showMessageDialog(null, "Loaded " + cart + " from " + jsonCart, "Load successful",
                INFORMATION_MESSAGE);
    }
}