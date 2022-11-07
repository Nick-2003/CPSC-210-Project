// https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
// Based on AlarmSystem example for CPSC 210; used as reference for JFrame
// https://github.students.cs.ubc.ca/CPSC210/SimpleDrawingPlayer-Starter.git
// Based on SimpleDrawingPlayer-Starter example for CPSC 210; used as reference for JFrame

package ui;

import exceptions.NegativeValueException;
import exceptions.NotEnoughItemsException;
import model.Cart;
import model.Inventory;
import model.Item;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.tools.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StoreAppGUI extends JFrame {

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;
    public static final int GAP = 10;

    private static final String JSON_INV = "./data/inventory.json";
    private static final String JSON_CART = "./data/cart.json";

    private List<Tool> tools;

    private JPanel buttonPanel1;
    private JPanel buttonPanel2;
    private JPanel buttonPanel3;
    private JPanel cartPanel;
    private JPanel inventoryPanel;
    private JScrollPane myScrollPane;

    private Inventory inventory;
    private Cart cart;
    private Scanner input;

    private JsonWriter jsonWriterInv;
    private JsonReader jsonReaderInv;
    private JsonWriter jsonWriterCart;
    private JsonReader jsonReaderCart;

    public StoreAppGUI() {
        //this is the constructor
        super("Store App");
        this.buttonPanel1 = new JPanel();
        this.buttonPanel2 = new JPanel();
        this.buttonPanel3 = new JPanel();
        this.cartPanel = new JPanel();
        this.inventoryPanel = new JPanel();
        initialiseSystem();
        initializeGraphics();
    }

    // MODIFIES: this
    // EFFECTS: Initializes system
    private void initialiseSystem() {
        tools = new ArrayList<Tool>();


        inventory = new Inventory("Inventory");
        cart = new Cart("Cart");
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        jsonWriterInv = new JsonWriter(JSON_INV);
        jsonReaderInv = new JsonReader(JSON_INV);
        jsonWriterCart = new JsonWriter(JSON_CART);
        jsonReaderCart = new JsonReader(JSON_CART);
    }

    // MODIFIES: this
    // EFFECTS: Draw JFrame window where this DrawingEditor will operate, and populates the tools to be used to run
    // the system
    private void initializeGraphics() {
        setLayout(new BorderLayout());
        setTitle("Store App");
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBounds(100,100,WIDTH, HEIGHT);
//        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        createPanels();
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Declares and instantiates all panels
    private void createPanels() {
        panelSetup(this.buttonPanel1, "Functions for Cart", 0, 0, true);
        panelSetup(this.buttonPanel2, "Functions for Inventory", 0, 1, true);
        panelSetup(this.buttonPanel3, "Functions for Store", 0, 2, true);
        panelSetup(this.cartPanel, "Cart", 1, 0, false);
        panelSetup(this.inventoryPanel, "Functions", 2, 0, false);
        JLabel label = new JLabel("This is a text field");
        this.inventoryPanel.add(label);

        ArrayList<Item> lst = new ArrayList<Item>();
        Item rice10 = null;
        try {
            rice10 = new Item("White Rice", 10, 8.00);
            this.cart.putIntoList(rice10);
        } catch (NegativeValueException e) {
            throw new RuntimeException(e);
        } catch (NotEnoughItemsException e) {
            throw new RuntimeException(e);
        }
        lst.add(rice10);
        lst.add(rice10);

        List<String[]> rec = new ArrayList<String[]>();
        String[][] list1 = setUpArray(lst);
        String[][] list2 = setUpArray(this.cart.getInternalList());
        String[] header = { "Name", "Quantity", "Price" };
        JTable table1 = new JTable(list1, header);
        JTable table2 = new JTable(list2, header);
        JTableHeader head = table1.getTableHeader();
        head.setPreferredSize(new Dimension(WIDTH / 3, HEIGHT / 50));
        ((DefaultTableCellRenderer) head.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        table1.setRowHeight(HEIGHT / 50);
        JScrollPane js = new JScrollPane(table1);
        js.setVisible(true);
        add(js);

        ToCartTool toCartTool = new ToCartTool(this, this.buttonPanel1);
        FromCartTool fromCartTool = new FromCartTool(this, this.buttonPanel1);
        PaymentTool paymentTool = new PaymentTool(this, this.buttonPanel1);

        AddToInventoryTool addToInventoryTool = new AddToInventoryTool(this, this.buttonPanel2);
        RemoveFromInventoryTool removeFromInventoryTool = new RemoveFromInventoryTool(this, this.buttonPanel2);
        SetPriceTool setPriceTool = new SetPriceTool(this, this.buttonPanel2);

        SaveTool saveTool = new SaveTool(this, this.buttonPanel3);
        LoadTool loadTool = new LoadTool(this, this.buttonPanel3);
        PrintTool printTool = new PrintTool(this, this.buttonPanel3);
        ClearTool clearTool = new ClearTool(this, this.buttonPanel3);

        this.cartPanel.add(table2, BorderLayout.NORTH);

        this.inventoryPanel.add(head, BorderLayout.NORTH);
        this.inventoryPanel.add(table1, BorderLayout.NORTH);
    }

    private void panelSetup(JPanel panel, String title, int partw, int parth, boolean split) {
        Border br = BorderFactory.createLineBorder(Color.black);
        if (split) {
            panel.setLayout(new GridLayout(0,1));
            panel.setBounds((WIDTH * partw / 3) + GAP / 2, GAP / 2 + (HEIGHT * parth / 3),
                    (WIDTH / 3) - GAP,HEIGHT / 3 - GAP * 5);
        } else {
            panel.setLayout(new FlowLayout());
            panel.setBounds((WIDTH * partw / 3) + GAP / 2, GAP / 2 + (HEIGHT * parth / 3),
                    (WIDTH / 3) - GAP,HEIGHT - GAP * 5);
        }

//        panel.setBounds((WIDTH * partw / 3) + GAP / 2, GAP / 2 + (HEIGHT * parth / 3),
//                (WIDTH / 3) - GAP,HEIGHT * split / 3 - GAP * 5);
//        panel.setMinimumSize(new Dimension(((WIDTH / 3) - GAP) / 2, (HEIGHT * split / 3 - GAP * 5) / 2));
        panel.setBorder(br);
        Container c = getContentPane();
        c.add(panel, BorderLayout.NORTH);
    }

    private String[][] setUpArray(ArrayList<Item> lst) {
        List<String[]> rec = new ArrayList<String[]>();
        for (Item i : lst) {
            String[] in = new String[]{i.getName(), String.valueOf(i.getAmount()), "$" + i.getPrice()};
            rec.add(in);
        }
        String[][] rec1 = rec.toArray(new String[0][]);
        return rec1;
    }

//    private void addMenu() {
//        JMenuBar menuBar = new JMenuBar();
//        JMenu cartMenu = new JMenu("Cart");
//        cartMenu.setMnemonic('C');
//        addMenuItem(cartMenu, new AddSensorAction(),
//                KeyStroke.getKeyStroke("control C"));
//        menuBar.add(cartMenu);
//
//        JMenu codeMenu = new JMenu("Code");
//        codeMenu.setMnemonic('C');
////        addMenuItem(codeMenu, new AddCodeAction(), null);
////        addMenuItem(codeMenu, new RemoveCodeAction(), null);
//        menuBar.add(codeMenu);
//
//        JMenu systemMenu = new JMenu("System");
//        systemMenu.setMnemonic('y');
////        addMenuItem(systemMenu, new ArmAction(),
////                KeyStroke.getKeyStroke("control A"));
////        addMenuItem(systemMenu, new DisarmAction(),
////                KeyStroke.getKeyStroke("control D"));
//        menuBar.add(systemMenu);
//
//        setJMenuBar(menuBar);
//    }

//    private void addMenuItem(JMenu theMenu, AbstractAction action, KeyStroke accelerator) {
//        JMenuItem menuItem = new JMenuItem(action);
//        menuItem.setMnemonic(menuItem.getText().charAt(0));
//        menuItem.setAccelerator(accelerator);
//        theMenu.add(menuItem);
//    }

//    private class AddSensorAction extends AbstractAction {
//
//        AddSensorAction() {
//            super("Add Sensor");
//        }
//
//        @Override
//        public void actionPerformed(ActionEvent evt) {
//            String sensorLoc = JOptionPane.showInputDialog(null,
//                    "Item name",
//                    "Enter sensor location",
//                    JOptionPane.QUESTION_MESSAGE);
////            try {
////                if (sensorLoc != null) {
////                    Sensor s = new Sensor(sensorLoc, ac);
////                    desktop.add(new SensorUI(s, AlarmControllerUI.this));
////                }
////            } catch (DuplicateSensorException e) {
////                JOptionPane.showMessageDialog(null, e.getMessage(), "System Error",
////                        JOptionPane.ERROR_MESSAGE);
////            }
//        }
//    }

    //main method
    public static void main(String[] args) {
        new StoreAppGUI();
    }
}
