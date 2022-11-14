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
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StoreAppGUI extends JFrame {

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;
    public static final int GAP = 10;

    public static final String JSON_INV = "./data/inventory.json";
    public static final String JSON_CART = "./data/cart.json";

    private JPanel buttonPanel1;
    private JPanel buttonPanel2;
    private JPanel buttonPanel3;
    private JPanel cartPanel;
    private JPanel inventoryPanel;
//    private JScrollPane myScrollPane;
    private ItemListModel cartModel;
    private ItemListModel inventoryModel;
    private JTable cartTable;
    private JTable inventoryTable;

    protected Inventory inventory;
    protected Cart cart;

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

        this.cart = new Cart("Cart");
        this.inventory = new Inventory("Inventory");

        try {
            this.cart.putIntoList(new Item("White Rice", 10, 8.00));
//            for (int i = 0; i < 40; i++) {
////                this.cart.putIntoList(new Item(Integer.toString(i), i + 1, i + 2));
//                this.cartModel.addItem(new Item(Integer.toString(i), i + 1, i + 2));
//            }
//            this.cartModel.addItem(new Item("White Rice", 10, 8.00));
//            this.inventory.putIntoList(rice10);
        } catch (NegativeValueException | NotEnoughItemsException e) {
            throw new RuntimeException(e);
        } // FOR TESTING PURPOSES // ONLY SHOWS BEFORE SETTING UP MODELS, DOES NOT ACTUALLY SAVE ANYTHING

        this.cartModel = new ItemListModel(this.cart);
        this.inventoryModel = new ItemListModel(this.inventory);

        this.cartTable = new JTable();
        this.inventoryTable = new JTable();

        initialiseSystem();
        initializeGraphics();
    }

    // MODIFIES: this
    // EFFECTS: Initializes system
    private void initialiseSystem() {
        inventory = new Inventory("Inventory");
        cart = new Cart("Cart");
        jsonWriterInv = new JsonWriter(JSON_INV);
        jsonReaderInv = new JsonReader(JSON_INV);
        jsonWriterCart = new JsonWriter(JSON_CART);
        jsonReaderCart = new JsonReader(JSON_CART);
    }

    // MODIFIES: this
    // EFFECTS: Draw JFrame window where Store app will operate
    private void initializeGraphics() {
        setLayout(new BorderLayout());
        setTitle("Store App");
        setResizable(false);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBounds(100,100,WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        createPanels();
//        this.cartPanel.setLayout(new BoxLayout(this.cartPanel, BoxLayout.Y_AXIS));
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Creates panels
    private void createPanels() {
        panelSetup(this.buttonPanel1, "Functions for Cart", 0, 0, true);
        panelSetup(this.buttonPanel2, "Functions for Inventory", 0, 1, true);
        panelSetup(this.buttonPanel3, "Functions for Store", 0, 2, true);
        panelSetup(this.cartPanel, "Cart List", 1, 0, false);
        panelSetup(this.inventoryPanel, "Inventory List", 2, 0, false);

//        setUpTables(this.inventory, this.inventoryPanel);
//        setUpTables(this.cart, this.cartPanel);



        setUpTables(this.inventoryModel, this.inventoryPanel);
        setUpTables(this.cartModel, this.cartPanel);



        toolSetUp();
    }

    // MODIFIES: this
    // EFFECTS: Creates tools for panels
    private void toolSetUp() {
        ToCartTool toCartTool = new ToCartTool(this, this.buttonPanel1, this.inventory, this.inventoryTable,
                this.cart, this.cartTable);
        FromCartTool fromCartTool = new FromCartTool(this, this.buttonPanel1, this.inventory, this.inventoryTable,
                this.cart, this.cartTable);
        PaymentTool paymentTool = new PaymentTool(this, this.buttonPanel1, this.cart);

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
    private void panelSetup(JPanel panel, String name, int partw, int parth, boolean split) {
        Border br = BorderFactory.createLineBorder(Color.black);
        if (split) {
            panel.setLayout(new GridLayout(0,1));
            panel.setBounds((WIDTH * partw / 3) + GAP / 2, GAP / 2 + (HEIGHT * parth / 3),
                    (WIDTH / 3) - GAP,HEIGHT / 3 - GAP * 5);
        } else {
            panel.setLayout(new FlowLayout());
            panel.setBounds((WIDTH * partw / 3) + GAP / 2, GAP / 2 + (HEIGHT * parth / 3),
                    (WIDTH / 3) - GAP,HEIGHT - GAP * 5);
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        }
        JLabel listName = new JLabel("<html><h1>" + name + "</h1></html>");
        listName.setSize(new Dimension(WIDTH / 3, HEIGHT / 25));
        listName.setHorizontalAlignment(SwingConstants.CENTER);
        listName.setVerticalAlignment(SwingConstants.CENTER);
        listName.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(listName, BorderLayout.CENTER);

        panel.setBorder(br);
        Container c = getContentPane();
        c.add(panel, BorderLayout.NORTH);
    }

    // MODIFIES: this
    // EFFECT: Reformat ArrayList<Item> to string formats for usage in table
    public static String[][] setUpArray(ArrayList<Item> lst) {
        List<String[]> rec = new ArrayList<>();
        for (Item i : lst) {
            String[] in = new String[]{i.getName(), String.valueOf(i.getAmount()), "$" + i.getPrice()};
            rec.add(in);
        }
        return rec.toArray(new String[0][]);
    }

//    // MODIFIES: this
//    // EFFECT: Take ItemList and set up JTable table
//    private void setUpTables(ItemList itemList, JPanel panel) {
//        ItemListModel model = new ItemListModel(itemList);
//        JTable table = new JTable(model);
//        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
//
//        table.getColumnModel().getColumn(0).setPreferredWidth(WIDTH / 9);
//        table.getColumnModel().getColumn(1).setPreferredWidth(WIDTH / 9);
//        table.getColumnModel().getColumn(2).setPreferredWidth(WIDTH / 9);
//
//        JTableHeader newTableHead = table.getTableHeader();
//        newTableHead.setPreferredSize(new Dimension(WIDTH / 9, HEIGHT / 25));
//        newTableHead.setMaximumSize(new Dimension(WIDTH / 9, HEIGHT / 25));
//
//        ((DefaultTableCellRenderer) newTableHead.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
//
//        JScrollPane newTable = new JScrollPane(table);
//        panel.add(newTable, BorderLayout.CENTER);
//    }

    // MODIFIES: this
    // EFFECT: Take ItemList and set up JTable table
    private void setUpTables(ItemListModel itemModel, JPanel panel) {
        JTable table = new JTable(itemModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        table.getColumnModel().getColumn(0).setPreferredWidth(WIDTH / 9);
        table.getColumnModel().getColumn(1).setPreferredWidth(WIDTH / 9);
        table.getColumnModel().getColumn(2).setPreferredWidth(WIDTH / 9);

        JTableHeader newTableHead = table.getTableHeader();
        newTableHead.setPreferredSize(new Dimension(WIDTH / 9, HEIGHT / 25));
        newTableHead.setMaximumSize(new Dimension(WIDTH / 9, HEIGHT / 25));

        ((DefaultTableCellRenderer) newTableHead.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        JScrollPane newTable = new JScrollPane(table);
        panel.add(newTable, BorderLayout.CENTER);
    }

    //main method
    public static void main(String[] args) {
        new StoreAppGUI();
    }
}
