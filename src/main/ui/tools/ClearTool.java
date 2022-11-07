package ui.tools;

import ui.StoreAppGUI;

import javax.swing.*;

public class ClearTool extends Tool {
    public ClearTool(StoreAppGUI store, JComponent parent) {
        super(store, parent);
    }

    // MODIFIES: this
    // EFFECTS:  constructs a move button which is then added to the JComponent (parent)
    //           which is passed in as a parameter
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Clear");
        button = customizeButton(button);
        addToParent(parent);
    }
}
