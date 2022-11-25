// https://stackoverflow.com/questions/3549206/how-to-add-row-in-jtable
// Reference for adding rows to table

package ui.tools;

import ui.models.CartModel;
import ui.models.ItemListModel;
import ui.StoreAppGUI;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;

import static javax.swing.JOptionPane.*;

// Represents a tool for the StoreAppGUI to produce the receipt
public class PaymentTool extends Tool {

    private final CartModel cart;
    private final JPanel panel;

    public PaymentTool(StoreAppGUI store, JComponent parent, CartModel cartStore) {
        super(store, parent);
        this.cart = cartStore;
        this.panel = new JPanel();
    }

    // MODIFIES: this
    // EFFECTS: Constructs a button which is then added to the JComponent (parent), which is passed in as a parameter
    protected void createButton(JComponent parent) {
        ToolAction a = new ToolAction("Receipt for Payment");
        button = new JButton(a);
        addToParent(parent);
    }

    private class ToolAction extends Tool.ToolAction {

        public ToolAction(String name) {
            super(name);
        }

        // EFFECTS: Runs button action
        public void actionPerformed(ActionEvent a) {
            payment();
        }

        // EFFECTS: Save ItemList to file
        private void payment() {
            ItemListModel model = new ItemListModel(cart.getItemList());
            JTable table = new JTable(model);
            JScrollPane newTable = new JScrollPane(table);
            JTableHeader newTableHead = table.getTableHeader();

            panel.add(newTable, BorderLayout.CENTER);
            panel.add(newTableHead, BorderLayout.CENTER);
            showMessageDialog(null, panel, "Cart Items", INFORMATION_MESSAGE);
            showMessageDialog(null, new JLabel("<html><p>Payment: </p> <p>Total cost of items: $"
                    + cart.getItemList().totalPrice() + "</p> <p>Tax: $" + cart.getItemList().tax()
                    + "</p> <p>Final Cost: $" + cart.getItemList().finalPrice() + "</p> </html>"),
                    "Final Cost", INFORMATION_MESSAGE);
        }
    }
}
