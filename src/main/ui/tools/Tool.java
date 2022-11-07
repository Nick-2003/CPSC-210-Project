package ui.tools;

import ui.StoreAppGUI;

import javax.swing.*;

public abstract class Tool {
    protected JButton button;
    protected StoreAppGUI store;


    public Tool(StoreAppGUI store, JComponent parent) {
        this.store = store;
        createButton(parent);
        addToParent(parent);
    }

    // MODIFIES: this
    // EFFECTS:  customizes the button used for this tool
    protected JButton customizeButton(JButton button) {
        button.setBorderPainted(true);
        button.setFocusPainted(true);
        button.setContentAreaFilled(true);
        return button;
    }

    // EFFECTS: creates button to activate tool
    protected abstract void createButton(JComponent parent);

    // MODIFIES: parent
    // EFFECTS:  adds the given button to the parent component
    public void addToParent(JComponent parent) {
        parent.add(button);
    }
}
