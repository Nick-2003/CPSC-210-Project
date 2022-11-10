// https://stackoverflow.com/questions/6555040/multiple-input-in-joptionpane-showinputdialog
// Used to create multiple inputs

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

public class ToCartTool extends Tool {

    private Inventory inventory;
    private JTable inventoryTable;
    private Cart cart;
    private JTable cartTable;


    public ToCartTool(StoreAppGUI store, JComponent parent, Inventory inventoryStore, JTable inventoryTable,
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
        ToolAction a = new ToolAction("Move to Cart");
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
            int option = JOptionPane.showConfirmDialog(null, message, "Enter Item",
                    JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                itemName = name.getText();
                itemQuantity = Integer.parseInt(quantity.getText());
            } else {
                System.out.println("Entry canceled");
            }
            itemToCart(itemName, itemQuantity);
        } // Modify to change table accordingly

        // REQUIRES: Inventory contains required Item
        // MODIFIES: this, store
        // EFFECTS: Move Item from Inventory to Cart
        private void itemToCart(String itemName, int itemQuantity) {
            if (inventory.getNamedAmount(itemName) >= itemQuantity) {
                double itemPrice = inventory.getNamedPrice(itemName);
                Item item;
                try {
                    item = new Item(itemName, itemQuantity, itemPrice);
                    inventory.takeFromList(item);
                    cart.putIntoList(item);
                    modifyTable(inventory, inventoryTable);
                    modifyTable(cart, cartTable);
                } catch (NegativeValueException e) {
                    System.out.print("Quantity or price (or both) for input " + itemName
                            + " is negative; request is invalid");
                    showMessageDialog(null, "Quantity or price (or both) for input " + itemName
                                    + " is negative; request is invalid");
                } catch (NotEnoughItemsException e) {
                    System.out.print("Not enough items in inventory to move to cart; request is invalid");
                    showMessageDialog(null,
                            "Not enough items in inventory to move to cart; request is invalid");
                }
                System.out.print("Moved " + itemQuantity + " of " + itemName + " from Inventory to Cart\n");
                showMessageDialog(null,
                        "Moved " + itemQuantity + " of " + itemName + " from Inventory to Cart\n");
            } else {
                System.out.print("Not enough " + itemName + " in Inventory\n");
                showMessageDialog(null, "Not enough " + itemName + " in Inventory");
            }
        } // SHOULD UPDATE TABLE ACCORDINGLY
    }
}