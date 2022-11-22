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

public class FromCartTool extends Tool {

    private InventoryModel inventory;
    private CartModel cart;

    public FromCartTool(StoreAppGUI store, JComponent parent, InventoryModel inventoryStore, CartModel cartStore) {
        super(store, parent);
        this.inventory = inventoryStore;
//        this.inventoryTable = inventoryTable;
        this.cart = cartStore;
//        this.cartTable = cartTable;
    }

    // MODIFIES: this
    // EFFECTS: Constructs a button which is then added to the JComponent (parent), which is passed in as a parameter
    protected void createButton(JComponent parent) {
        ToolAction a = new ToolAction("Move from Cart");
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
                    itemFromCart(itemName, itemQuantity);
                } catch (NumberFormatException e) {
                    showMessageDialog(null, new JLabel("Entry is blank"), "Entry is blank", INFORMATION_MESSAGE);
                }
            } else {
                showMessageDialog(null, new JLabel("Entry canceled"), "Entry canceled", INFORMATION_MESSAGE);
            }

        }

        // REQUIRES: Inventory contains required Item
        // MODIFIES: this
        // EFFECTS: Move Item from Cart to Inventory
        private void itemFromCart(String itemName, int itemQuantity) {
            if (cart.getItemList().getNamedAmount(itemName) >= itemQuantity) {
                double itemPrice = cart.getItemList().getNamedPrice(itemName);
                try {
                    Item item = new Item(itemName, itemQuantity, itemPrice);
                    cart.takeFromList(item);
                    inventory.putIntoList(item);
                } catch (NegativeValueException e) {
                    showMessageDialog(null, "Quantity or price (or both) for input " + itemName
                            + " is negative; request is invalid", "NegativeValueException", ERROR_MESSAGE);
                } catch (NotEnoughItemsException e) {
                    showMessageDialog(null, "Not enough items in cart to move to inventory; "
                            + "request is invalid", "NotEnoughItemsException", ERROR_MESSAGE);
                }
                showMessageDialog(null, "Moved " + itemQuantity + " of " + itemName
                        + " from Cart to Inventory", "Transfer successful", INFORMATION_MESSAGE);
            } else {
                showMessageDialog(null, "Not enough " + itemName + " in Cart",
                        "Transfer failed", ERROR_MESSAGE);
            }
        }
    }
}
