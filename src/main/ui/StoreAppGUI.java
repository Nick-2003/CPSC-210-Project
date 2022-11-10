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
import model.ItemList;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.tools.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StoreAppGUI extends JFrame {

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;
    public static final int GAP = 10;

    public static final String JSON_INV = "./data/inventory.json";
    public static final String JSON_CART = "./data/cart.json";

    private List<Tool> tools;

    private JPanel buttonPanel1;
    private JPanel buttonPanel2;
    private JPanel buttonPanel3;
    private JPanel cartPanel;
    private JPanel inventoryPanel;
//    private JScrollPane myScrollPane;
    private JTable cartTable;
    private JTable inventoryTable;

    protected Inventory inventory;
    protected Cart cart;
    protected Scanner input;

    private JsonWriter jsonWriterInv;
    private JsonWriter jsonWriterCart;
    private JsonReader jsonReaderInv;
    private JsonReader jsonReaderCart;

    public StoreAppGUI() {
        //this is the constructor
        super("Store App");
        this.buttonPanel1 = new JPanel();
        this.buttonPanel2 = new JPanel();
        this.buttonPanel3 = new JPanel();
        this.cartPanel = new JPanel();
        this.inventoryPanel = new JPanel();
        this.cartTable = new JTable();
        this.inventoryTable = new JTable();
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
    // EFFECTS: Draw JFrame window where Store app will operate, and populates the tools to be used to run the system
    private void initializeGraphics() {
        setLayout(new BorderLayout());
        setTitle("Store App");
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBounds(100,100,WIDTH, HEIGHT);
//        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        this.cartPanel.setLayout(new BoxLayout(this.cartPanel, BoxLayout.Y_AXIS));
        createPanels();
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Creates panels
    private void createPanels() {
        panelSetup(this.buttonPanel1, "Functions for Cart", 0, 0, true);
        panelSetup(this.buttonPanel2, "Functions for Inventory", 0, 1, true);
        panelSetup(this.buttonPanel3, "Functions for Store", 0, 2, true);
        panelSetup(this.cartPanel, "Cart", 1, 0, false);
        panelSetup(this.inventoryPanel, "Functions", 2, 0, false);

        try {
            Item rice10 = new Item("White Rice", 10, 8.00);
            this.cart.putIntoList(rice10);
            this.inventory.putIntoList(rice10);
        } catch (NegativeValueException e) {
            throw new RuntimeException(e);
        } catch (NotEnoughItemsException e) {
            throw new RuntimeException(e);
        } // FOR TESTING

        setUpTables(this.inventory, "Inventory List", this.inventoryPanel, this.inventoryTable, 2);
        setUpTables(this.cart, "Cart List", this.cartPanel, this.cartTable, 1);


//        tableInv.setRowHeight(HEIGHT / 50);
//        JScrollPane js = new JScrollPane(tableInv);
//        js.setVisible(true);
//        add(js);

        ToCartTool toCartTool = new ToCartTool(this, this.buttonPanel1, this.inventory, this.inventoryTable,
                this.cart, this.cartTable);
        FromCartTool fromCartTool = new FromCartTool(this, this.buttonPanel1, this.inventory, this.inventoryTable,
                this.cart, this.cartTable);
        PaymentTool paymentTool = new PaymentTool(this, this.buttonPanel1, this.cart);

//        AddToInventoryTool addToInventoryTool = new AddToInventoryTool(this, this.buttonPanel2, this.inventory);
        AddToInventoryTool addToInventoryTool = new AddToInventoryTool(this, this.buttonPanel2, this.inventory,
                this.inventoryTable);
        RemoveFromInventoryTool removeFromInventoryTool = new RemoveFromInventoryTool(this, this.buttonPanel2,
                this.inventory, this.inventoryTable);
        SetPriceTool setPriceTool = new SetPriceTool(this, this.buttonPanel2, this.inventory, this.inventoryTable);

        SaveTool saveTool = new SaveTool(this, this.buttonPanel3, this.inventory, this.cart, this.jsonWriterInv,
                this.jsonWriterCart);
        LoadTool loadTool = new LoadTool(this, this.buttonPanel3, this.inventory, this.cart, this.jsonReaderInv,
                this.jsonReaderCart);
//        PrintTool printTool = new PrintTool(this, this.buttonPanel3); // NOT SURE IF NECESSARY
        ClearTool clearTool = new ClearTool(this, this.buttonPanel3, this.inventory, this.inventoryTable,
                this.cart, this.cartTable);
    }

    // MODIFIES: this
    // EFFECTS: Creates basic layout for panels
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

    // MODIFIES: this
    // EFFECT: Reformat ArrayList<Item> to string formats for usage in table
    public static String[][] setUpArray(ArrayList<Item> lst) {
        List<String[]> rec = new ArrayList<String[]>();
        for (Item i : lst) {
            String[] in = new String[]{i.getName(), String.valueOf(i.getAmount()), "$" + i.getPrice()};
            rec.add(in);
        }
        String[][] rec1 = rec.toArray(new String[0][]);
        return rec1;
    }

    private void setUpTables(ItemList itemList, String name, JPanel panel, JTable table, int partw) {
        String[][] itemArray = setUpArray(itemList.getInternalList());
        DefaultTableModel model = new DefaultTableModel(itemArray, new String[]{"Name", "Quantity", "Price"});
        table = new JTable(model);
//        table.setPreferredSize(new Dimension(WIDTH / 9, HEIGHT * 24 / 25));
        table.getColumnModel().getColumn(0).setPreferredWidth(WIDTH / 9);
        table.getColumnModel().getColumn(1).setPreferredWidth(WIDTH / 9);
        table.getColumnModel().getColumn(2).setPreferredWidth(WIDTH / 9);
//        JTable table = new JTable(itemArray, header);

        JTableHeader newTableHead = table.getTableHeader();
//        JTableHeader newTableHead2 = new JTableHeader(new DefaultTableColumnModel());
        newTableHead.setPreferredSize(new Dimension(WIDTH / 9, HEIGHT / 25));
//        newTableHead.setMinimumSize(new Dimension(WIDTH / 3, HEIGHT / 25));

        ((DefaultTableCellRenderer) newTableHead.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        panel.add(new JLabel(name), BorderLayout.NORTH);
//        panel.add(newTableHead, BorderLayout.SOUTH);
        JScrollPane newTable = new JScrollPane(table);
//        newTable.setBounds((WIDTH * partw / 3) + GAP / 2, GAP / 2,
//                (WIDTH / 3) - GAP,HEIGHT / 3 - GAP * 5);
        panel.add(newTable, BorderLayout.CENTER);

//        panel.add(new JLabel(name), BoxLayout.Y_AXIS, -1);
//        panel.add(newTableHead, BoxLayout.Y_AXIS, -1);
//        panel.add(table, BoxLayout.Y_AXIS, -1);
    }

    //main method
    public static void main(String[] args) {
        new StoreAppGUI();
    }
}
