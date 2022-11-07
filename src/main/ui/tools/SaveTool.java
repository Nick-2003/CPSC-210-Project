package ui.tools;

import ui.StoreAppGUI;

import javax.swing.*;

public class SaveTool extends Tool {
    public SaveTool(StoreAppGUI store, JComponent parent) {
        super(store, parent);
    }

    // MODIFIES: this
    // EFFECTS:  constructs a button which is then added to the JComponent (parent)
    //           which is passed in as a parameter
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Save Item List");
        addToParent(parent);
    }
}
