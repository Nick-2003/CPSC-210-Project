package ui.tools;

import exceptions.NegativeValueException;
import exceptions.NotEnoughItemsException;
import model.Inventory;
import model.Item;
import ui.StoreAppGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static javax.swing.JOptionPane.*;

public class RemoveFromInventoryTool extends Tool {

    private Inventory inventory;
    private JTable inventoryTable;

    public RemoveFromInventoryTool(StoreAppGUI store, JComponent parent, Inventory inventoryStore,
                                   JTable inventoryTable) {
        super(store, parent);
        this.inventory = inventoryStore;
        this.inventoryTable = inventoryTable;
    }

    // MODIFIES: this
    // EFFECTS: Constructs a button which is then added to the JComponent (parent), which is passed in as a parameter
    protected void createButton(JComponent parent) {
        ToolAction a = new ToolAction("Remove from Inventory");
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
            double itemPriceInput = 0;

            JTextField name = new JTextField();
            JTextField quantity = new JTextField();
            JTextField price = new JTextField();
            Object[] message = {
                    "Name:", name,
                    "Quantity:", quantity,
                    "Price:", price
            };
            int option = showConfirmDialog(null, message, "Enter Item",
                    OK_CANCEL_OPTION);
            if (option == OK_OPTION) {
                itemName = name.getText();
                itemQuantity = Integer.parseInt(quantity.getText());
                itemPriceInput = BigDecimal.valueOf(Double.parseDouble(price.getText()))
                        .setScale(2, RoundingMode.HALF_UP).doubleValue();
            } else {
                System.out.println("Entry canceled");
            }
            removeFromInventory(itemName, itemQuantity, itemPriceInput);
        } // Modify to change table accordingly

        // REQUIRES: Inventory contains required Item
        // MODIFIES: this, store
        // EFFECTS: Move Item from Inventory to Cart
        private void removeFromInventory(String itemName, int itemQuantity, double itemPriceInput) {
            double itemPrice;
            if (inventory.getNamedAmount(itemName) > 0) {
                itemPrice = inventory.getNamedPrice(itemName);
            } else {
                itemPrice = itemPriceInput;
            }
            Item item;
            try {
                item = new Item(itemName, itemQuantity, itemPrice);
                if (inventory.takeFromList(item)) {
                    showMessageDialog(null,
                            new JLabel("<html><p>Removed Item(s): </p> <p>Name: " + itemName
                                    + "</p> <p>Quantity: " + itemQuantity + "</p> <p>Price: $"
                                    + itemPrice + "</p> </html>"));
                    // Modify table here
                } else {
                    System.out.print("\n " + itemName + " is not in Inventory \n");
                }
                modifyTable(inventory, inventoryTable);
            } catch (NegativeValueException e) {
                System.out.print("Quantity or price (or both) for input " + itemName
                        + " is negative; request is invalid");
                showMessageDialog(null, "Quantity or price (or both) for input " + itemName
                        + " is negative; request is invalid");
            } catch (NotEnoughItemsException e) {
                System.out.print("New quantity for " + itemName + " in inventory is less than 0; request is invalid");
                showMessageDialog(null,
                        "New quantity for " + itemName + " in inventory is less than 0; request is invalid");
            }
        } // SHOULD UPDATE TABLE ACCORDINGLY
    }
}