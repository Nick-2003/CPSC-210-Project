package ui.tools;

import exceptions.NegativeValueException;
import exceptions.NotEnoughItemsException;
import model.Cart;
import model.Inventory;
import model.Item;
import ui.StoreAppGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import static javax.swing.JOptionPane.*;

public class FromCartTool extends Tool {

    private Inventory inventory;
    private JTable inventoryTable;
    private Cart cart;
    private JTable cartTable;

    public FromCartTool(StoreAppGUI store, JComponent parent, Inventory inventoryStore, JTable inventoryTable,
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
        ToolAction a = new ToolAction("Move from Cart");
        button = new JButton(a);
        addToParent(parent);
    }

    private class ToolAction extends Tool.ToolAction {

        public ToolAction(String name) {
            super(name); // Button for AbstractAction is now named "Move to Cart"
        }

        public void actionPerformed(ActionEvent a) {
            String itemName = null;
            int itemQuantity = 0;

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
                itemQuantity = Integer.parseInt(quantity.getText());
            } else {
                System.out.println("Entry canceled");
            }
            itemFromCart(itemName, itemQuantity);
        }

        // REQUIRES: Inventory contains required Item
        // MODIFIES: this, store
        // EFFECTS: Move Item from Cart to Inventory
        private void itemFromCart(String itemName, int itemQuantity) {
            if (cart.getNamedAmount(itemName) >= itemQuantity) {
                double itemPrice = inventory.getNamedPrice(itemName);
                try {
                    Item item = new Item(itemName, itemQuantity, itemPrice);
                    cart.takeFromList(item);
                    inventory.putIntoList(item);
                    // UPDATE TABLE ACCORDINGLY HERE
                    modifyTable(inventory, inventoryTable);
                    modifyTable(cart, cartTable);
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
        } // SHOULD UPDATE TABLE ACCORDINGLY
    }
}
