package ui.tools;

import exceptions.NegativeValueException;
import ui.InventoryModel;
import ui.StoreAppGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static javax.swing.JOptionPane.*;

public class SetPriceTool extends Tool {

    private InventoryModel inventory;

    public SetPriceTool(StoreAppGUI store, JComponent parent, InventoryModel inventoryStore) {
        super(store, parent);
        this.inventory = inventoryStore;
    }

    // MODIFIES: this
    // EFFECTS: Constructs a button which is then added to the JComponent (parent), which is passed in as a parameter
    protected void createButton(JComponent parent) {
        ToolAction a = new ToolAction("Set Price in Inventory");
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
            double itemPriceInput;

            JTextField name = new JTextField();
            JTextField price = new JTextField();
            Object[] message = {"Name:", name, "Price:", price};
            int option = showConfirmDialog(null, message, "Enter Item",
                    OK_CANCEL_OPTION);
            if (option == OK_OPTION) {
                itemName = name.getText();
                try {
                    itemPriceInput = BigDecimal.valueOf(Double.parseDouble(price.getText()))
                            .setScale(2, RoundingMode.HALF_UP).doubleValue();
                    setInventoryItemPrice(itemName, itemPriceInput);
                } catch (NumberFormatException e) {
                    showMessageDialog(null, new JLabel("Entry is blank"), "Entry is blank", INFORMATION_MESSAGE);
                }
            } else {
                showMessageDialog(null, new JLabel("Entry canceled"), "Entry canceled", INFORMATION_MESSAGE);
            }
        }

        // REQUIRES: Item is in Inventory
        // MODIFIES: this, Item
        // EFFECTS: Set the price of an existing Item in Inventory
        private void setInventoryItemPrice(String itemName, double itemPriceInput) {
            try {
                inventory.setNamedPrice(itemName, itemPriceInput);
                showMessageDialog(null, new JLabel("<html><p>New item price: </p> <p>Name: "
                        + itemName + "</p> <p>Price: $" + itemPriceInput + "</p> </html>"),
                        "Set price successful", INFORMATION_MESSAGE);
            } catch (NegativeValueException e) {
                showMessageDialog(null, "Price for input " + itemName
                        + " is negative; request is invalid", "Transfer failed", ERROR_MESSAGE);
            }

        }
    }
}