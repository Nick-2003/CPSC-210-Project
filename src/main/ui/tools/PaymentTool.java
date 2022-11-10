// https://stackoverflow.com/questions/3549206/how-to-add-row-in-jtable
// Reference for adding rows to table

package ui.tools;

import model.Cart;
import ui.StoreAppGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;

import static javax.swing.JOptionPane.*;
import static ui.StoreAppGUI.*;

public class PaymentTool extends Tool {

    private Cart cart;
    private JPanel panel;
    private JFrame frame;

    public PaymentTool(StoreAppGUI store, JComponent parent, Cart cartStore) {
        super(store, parent);
        this.cart = cartStore;
        this.panel = new JPanel();
        this.frame = new JFrame("Receipt for Payment");
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
            super(name); // Button for AbstractAction is now named "Move to Cart"
        }

        public void actionPerformed(ActionEvent a) {
            payment();
        } // Modify to change table accordingly

        // EFFECTS: Save ItemList to file
        private void payment() {
            String[][] itemArray = setUpArray(cart.getInternalList());
            DefaultTableModel model = new DefaultTableModel(itemArray, new String[]{"Name", "Quantity", "Price"});
            JTable table = new JTable(model);
            showMessageDialog(null, table, "Cart Items", INFORMATION_MESSAGE);
            showMessageDialog(null, new JLabel("<html><p>Payment: </p> <p>Total cost of items: $"
                    + cart.totalPrice() + "</p> <p>Tax: $" + cart.tax() + "</p> <p>Final Cost: $" + cart.finalPrice()
                    + "</p> </html>"), "Final Cost", INFORMATION_MESSAGE);
        }
    }
}
