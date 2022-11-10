package ui.tools;

import exceptions.NegativeValueException;
import model.Inventory;
import ui.StoreAppGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static javax.swing.JOptionPane.*;

public class SetPriceTool extends Tool {

    private Inventory inventory;
    private JTable inventoryTable;

    public SetPriceTool(StoreAppGUI store, JComponent parent, Inventory inventoryStore,
                        JTable inventoryTable) {
        super(store, parent);
        this.inventory = inventoryStore;
        this.inventoryTable = inventoryTable;
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

        public void actionPerformed(ActionEvent a) {
            String itemName = null;
            double itemPriceInput = 0;

            JTextField name = new JTextField();
            JTextField price = new JTextField();
            Object[] message = {
                    "Name:", name,
                    "Price:", price
            };
            int option = showConfirmDialog(null, message, "Enter Item",
                    OK_CANCEL_OPTION);
            if (option == OK_OPTION) {
                itemName = name.getText();
                itemPriceInput = BigDecimal.valueOf(Double.parseDouble(price.getText()))
                        .setScale(2, RoundingMode.HALF_UP).doubleValue();
            } else {
                System.out.println("Entry canceled");
            }
            setInventoryItemPrice(itemName, itemPriceInput);
        } // Modify to change table accordingly

        // REQUIRES: Item is in Inventory
        // MODIFIES: this, Item
        // EFFECTS: Set the price of an existing Item in Inventory
        private void setInventoryItemPrice(String itemName, double itemPriceInput) {
            try {
                inventory.setNamedPrice(itemName, itemPriceInput);
                modifyTable(inventory, inventoryTable);
                // SHOULD UPDATE TABLE ACCORDINGLY
                System.out.print("New item price: $" + itemPriceInput + "\n");
                showMessageDialog(null, new JLabel("<html><p>New item price: </p> <p>Name: "
                        + itemName + "</p> <p>Price: " + itemPriceInput + "</p> </html>"),
                        "Set price successful", INFORMATION_MESSAGE);
            } catch (NegativeValueException e) {
                System.out.print("Price for input " + itemName + " is negative; request is invalid");
                showMessageDialog(null, "Price for input " + itemName
                        + " is negative; request is invalid", "Transfer failed", ERROR_MESSAGE);
            }

        }
    }
}