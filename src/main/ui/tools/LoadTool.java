package ui.tools;

import exceptions.NegativeValueException;
import exceptions.NotEnoughItemsException;
import model.Cart;
import model.Inventory;
import persistence.JsonReader;
import ui.StoreAppGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

import static javax.swing.JOptionPane.*;
import static javax.swing.JOptionPane.showMessageDialog;
import static ui.StoreAppGUI.JSON_CART;
import static ui.StoreAppGUI.JSON_INV;

public class LoadTool extends Tool {

    private Inventory inventory;
    private Cart cart;
    private JsonReader jsonReaderInv;
    private JsonReader jsonReaderCart;

    public LoadTool(StoreAppGUI store, JComponent parent, Inventory inventoryStore, Cart cartStore,
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

        public void actionPerformed(ActionEvent a) {
            String[] selectionValues = {"Inventory", "Cart"};
            String initialSelection = "Inventory";
            Object selectList = showInputDialog(null, "Select an ItemList to save: ",
                    "Select ItemList", QUESTION_MESSAGE, null, selectionValues, initialSelection);
            loadItemList((String) selectList);
        } // Modify to change table accordingly

        // MODIFIES: this
        // EFFECTS: loads ItemList from file
        private void loadItemList(String selectList) {
            if (selectList.equals("Cart")) {
                try {
//                    cart = (Cart) jsonReaderCart.read(); // Make jsonReaderCart.read() a cart for the time being
                    cart = (Cart) jsonReaderCart.read();
                    // Update table here
                    System.out.println("Loaded " + cart.getName() + " from " + JSON_CART);
                } catch (IOException | NegativeValueException | NotEnoughItemsException e) {
                    System.out.println("Unable to read from file: " + JSON_CART);
                    showMessageDialog(null, "Unable to read from file: " + JSON_CART);
                }
            } else if (selectList.equals("Inventory")) {
                try {
                    inventory = (Inventory) jsonReaderInv.read();
                    // Update table here
                    System.out.println("Loaded " + inventory.getName() + " from " + JSON_INV);
                } catch (IOException | NegativeValueException | NotEnoughItemsException e) {
                    System.out.println("Unable to read from file: " + JSON_INV);
                    showMessageDialog(null, "Unable to read from file: " + JSON_INV);
                }
            }
        } // SHOULD UPDATE TABLE ACCORDINGLY
    }
}