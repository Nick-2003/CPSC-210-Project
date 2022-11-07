package ui.tools;

import ui.StoreAppGUI;

import javax.swing.*;

public class LoadTool extends Tool {
    public LoadTool(StoreAppGUI store, JComponent parent) {
        super(store, parent);
    }

    // MODIFIES: this
    // EFFECTS:  constructs a button which is then added to the JComponent (parent)
    //           which is passed in as a parameter
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Load Item List");
        addToParent(parent);
    }
}