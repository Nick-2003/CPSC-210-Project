package ui.tools;

import ui.StoreAppGUI;

import javax.swing.*;

public class FromCartTool extends Tool {
    public FromCartTool(StoreAppGUI store, JComponent parent) {
        super(store, parent);
    }

    // MODIFIES: this
    // EFFECTS:  constructs a move button which is then added to the JComponent (parent)
    //           which is passed in as a parameter
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Move from Cart");
        addToParent(parent);
    }
}
