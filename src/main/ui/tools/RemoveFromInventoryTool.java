package ui.tools;

import exceptions.NegativeValueException;
import exceptions.NotEnoughItemsException;
import model.Item;
import ui.InventoryModel;
import ui.StoreAppGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static javax.swing.JOptionPane.*;

public class RemoveFromInventoryTool extends Tool {

    private InventoryModel inventory;

    public RemoveFromInventoryTool(StoreAppGUI store, JComponent parent, InventoryModel inventoryStore) {
        super(store, parent);
        this.inventory = inventoryStore;
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

        // EFFECTS: Runs button action
        public void actionPerformed(ActionEvent a) {
            String itemName;
            int itemQuantity;
            double itemPriceInput;
            JTextField name = new JTextField();
            JTextField quantity = new JTextField();
            JTextField price = new JTextField();
            Object[] message = {"Name:", name, "Quantity:", quantity, "Price:", price};
            int option = showConfirmDialog(null, message, "Enter Item", OK_CANCEL_OPTION);
            if (option == OK_OPTION) {
                itemName = name.getText();
                try {
                    itemQuantity = Integer.parseInt(quantity.getText());
                    itemPriceInput = BigDecimal.valueOf(Double.parseDouble(price.getText()))
                            .setScale(2, RoundingMode.HALF_UP).doubleValue();
                    removeFromInventory(itemName, itemQuantity, itemPriceInput);
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
        private void removeFromInventory(String itemName, int itemQuantity, double itemPriceInput) {
            double itemPrice;
            if (inventory.getItemList().getNamedAmount(itemName) > 0) {
                itemPrice = inventory.getItemList().getNamedPrice(itemName);
            } else {
                itemPrice = itemPriceInput;
            }
            try {
                Item item = new Item(itemName, itemQuantity, itemPrice);
                if (inventory.takeFromList(item)) {
                    showMessageDialog(null, new JLabel("<html><p>Name: " + itemName
                            + "</p> <p>Quantity: " + itemQuantity + "</p> <p>Price: $" + itemPrice + "</p> </html>"),
                            "Removed Item(s)", INFORMATION_MESSAGE);
                } else {
                    showMessageDialog(null, itemName + " is not in Inventory ",
                            "Transfer failed", ERROR_MESSAGE);
                }
//                modifyTable(inventory, inventoryTable);
            } catch (NegativeValueException e) {
                showMessageDialog(null, "Quantity or price (or both) for input " + itemName
                        + " is negative; request is invalid", "NegativeValueException", ERROR_MESSAGE);
            } catch (NotEnoughItemsException e) {
                showMessageDialog(null, "New quantity for " + itemName + " in inventory is less "
                        + "than 0; request is invalid", "NotEnoughItemsException", ERROR_MESSAGE);
            }
        }
    }
}