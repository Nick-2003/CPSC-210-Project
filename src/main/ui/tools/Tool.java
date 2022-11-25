// https://github.students.cs.ubc.ca/CPSC210/SimpleDrawingPlayer-Starter.git
// Based on SimpleDrawingPlayer-Starter example for CPSC 210; used as reference for buttons in system

package ui.tools;

import ui.StoreAppGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;

// Represents a tool button to be added to the StoreAppGUI
public abstract class Tool {
    protected JButton button;
    protected StoreAppGUI store;

    public Tool(StoreAppGUI store, JComponent parent) {
        this.store = store;
        createButton(parent);
        addToParent(parent);
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

        // EFFECTS: Runs button action
        public abstract void actionPerformed(ActionEvent a);

    }
}
