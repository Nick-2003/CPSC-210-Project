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

public class AddToInventoryTool extends Tool {

    private InventoryModel inventory;

    public AddToInventoryTool(StoreAppGUI store, JComponent parent, InventoryModel inventoryStore) {
        super(store, parent);
        this.inventory = inventoryStore;
    }

    // MODIFIES: this
    // EFFECTS: Constructs a button which is then added to the JComponent (parent), which is passed in as a parameter
    protected void createButton(JComponent parent) {
        ToolAction a = new ToolAction("Add to Inventory");
        button = new JButton(a);
        addToParent(parent);
    }

    private class ToolAction extends Tool.ToolAction {

        public ToolAction(String name) {
            super(name);
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
                    addToInventory(itemName, itemQuantity, itemPriceInput);
                } catch (NumberFormatException e) {
                    showMessageDialog(null, new JLabel("Entry is blank"), "Entry is blank", INFORMATION_MESSAGE);
                }
            } else {
                showMessageDialog(null, new JLabel("Entry canceled"), "Entry canceled", INFORMATION_MESSAGE);
            }
        }

        // MODIFIES: this
        // EFFECTS: Add Item with given inputs to Inventory; if Item exists, take existing price
        private void addToInventory(String itemName, int itemQuantity, double itemPriceInput) {
            double itemPrice;
            if (inventory.getItemList().getNamedAmount(itemName) > 0) {
                itemPrice = inventory.getItemList().getNamedPrice(itemName);
            } else {
                itemPrice = itemPriceInput;
            }
            Item item;
            try {
                item = new Item(itemName, itemQuantity, itemPrice);
                inventory.putIntoList(item);
                showMessageDialog(null, new JLabel("<html><p>Name: " + itemName
                                + "</p> <p>Quantity: " + itemQuantity + "</p> <p>Price: $" + itemPrice
                        + "</p> </html>"), "Added Item(s)", INFORMATION_MESSAGE);
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