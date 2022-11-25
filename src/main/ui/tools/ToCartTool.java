// https://stackoverflow.com/questions/6555040/multiple-input-in-joptionpane-showinputdialog
// Used to create multiple inputs

package ui.tools;

import exceptions.NegativeValueException;
import exceptions.NotEnoughItemsException;
import model.Item;
import ui.models.CartModel;
import ui.models.InventoryModel;
import ui.StoreAppGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static javax.swing.JOptionPane.*;

// Represents a tool for the StoreAppGUI to move an item from the inventory to the cart
public class ToCartTool extends Tool {

    private final InventoryModel inventory;
    private final CartModel cart;

    public ToCartTool(StoreAppGUI store, JComponent parent, InventoryModel inventoryStore, CartModel cartStore) {
        super(store, parent);
        this.inventory = inventoryStore;
        this.cart = cartStore;
    }

    // MODIFIES: this
    // EFFECTS: Constructs a button which is then added to the JComponent (parent), which is passed in as a parameter
    protected void createButton(JComponent parent) {
        ToolAction a = new ToolAction("Move to Cart");
        button = new JButton(a);
        addToParent(parent);
    }

    private class ToolAction extends Tool.ToolAction {

        public ToolAction(String name) {
            super(name); // Button for AbstractAction is now named "Move to Cart"
        }

        // EFFECTS: Runs button action
        public void actionPerformed(ActionEvent a) {
            String itemName;
            int itemQuantity;

            JTextField name = new JTextField();
            JTextField quantity = new JTextField();
            Object[] message = {
                    "Name:", name,
                    "Quantity:", quantity
            };
            int option = showConfirmDialog(null, message, "Enter Item",
                    OK_CANCEL_OPTION);
            if (option == OK_OPTION) {
                itemName = name.getText();
                try {
                    itemQuantity = Integer.parseInt(quantity.getText());
                    itemToCart(itemName, itemQuantity);
                } catch (NumberFormatException e) {
                    showMessageDialog(null, new JLabel("Entry is blank"), "Entry is blank", INFORMATION_MESSAGE);
                }
            } else {
                showMessageDialog(null, new JLabel("Entry canceled"), "Entry canceled", INFORMATION_MESSAGE);
            }
        }

        // REQUIRES: Inventory contains required Item
        // MODIFIES: this
        // EFFECTS: Move Item from Inventory to Cart
        private void itemToCart(String itemName, int itemQuantity) {
            if (inventory.getItemList().getNamedAmount(itemName) >= itemQuantity) {
                double itemPrice = inventory.getItemList().getNamedPrice(itemName);
                Item item;
                try {
                    item = new Item(itemName, itemQuantity, itemPrice);
                    inventory.takeFromList(item);
                    cart.putIntoList(item);
                } catch (NegativeValueException e) {
                    showMessageDialog(null, "Quantity or price (or both) for input " + itemName
                                    + " is negative; request is invalid", "NegativeValueException", ERROR_MESSAGE);
                } catch (NotEnoughItemsException e) {
                    showMessageDialog(null, "Not enough items in inventory to move to cart; "
                            + "request is invalid", "NotEnoughItemsException", ERROR_MESSAGE);
                }
                showMessageDialog(null, "Moved " + itemQuantity + " of " + itemName
                        + " from Inventory to Cart", "Transfer successful", INFORMATION_MESSAGE);
            } else {
                showMessageDialog(null, "Not enough " + itemName + " in Inventory",
                        "Transfer failed", ERROR_MESSAGE);
            }
        }
    }
}