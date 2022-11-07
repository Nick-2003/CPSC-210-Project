package ui.tools;

import ui.StoreAppGUI;

import javax.swing.*;

public class SetPriceTool extends Tool {
    public SetPriceTool(StoreAppGUI store, JComponent parent) {
        super(store, parent);
    }

    // MODIFIES: this
    // EFFECTS:  constructs a move button which is then added to the JComponent (parent)
    //           which is passed in as a parameter
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Set Price in Inventory");
        addToParent(parent);
    }
}