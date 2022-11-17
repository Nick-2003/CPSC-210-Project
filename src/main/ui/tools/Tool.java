package ui.tools;

import ui.StoreAppGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;

public abstract class Tool {
    protected JButton button;
    protected StoreAppGUI store;

    public Tool(StoreAppGUI store, JComponent parent) {
        this.store = store;
        createButton(parent);
        addToParent(parent);
    }

    // MODIFIES: this
    // EFFECTS: Customizes the button used for this tool
    protected JButton customizeButton(JButton button) {
        button.setBorderPainted(true);
        button.setFocusPainted(true);
        button.setContentAreaFilled(true);
        return button;
    }

    // MODIFIES: this
    // EFFECTS: Constructs a button which is then added to the JComponent (parent), which is passed in as a parameter
    protected abstract void createButton(JComponent parent);

    // MODIFIES: parent
    // EFFECTS: Adds the given button to the parent component
    public void addToParent(JComponent parent) {
        parent.add(button);
    }

    // MODIFIES: this, parent
    // EFFECTS: Constructs the action for the button
    protected abstract class ToolAction extends AbstractAction {

        public ToolAction(String name) {
            super(name);
        }

        public abstract void actionPerformed(ActionEvent a);

//        // MODIFIES: this, parent
//        // EFFECTS: Applies changes to table
//        protected void modifyTable(ItemList itemList, JTable table) {
//            String[][] itemArray = StoreAppGUI.setUpArray(itemList.getInternalList());
//            DefaultTableModel model = new DefaultTableModel(itemArray, new String[]{"Name", "Quantity", "Price"});
//            table.setModel(model);
//        }
    }
}
