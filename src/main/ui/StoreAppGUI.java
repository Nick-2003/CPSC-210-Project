// https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
// Based on AlarmSystem example for CPSC 210; used as reference for JFrame

// https://github.students.cs.ubc.ca/CPSC210/SimpleDrawingPlayer-Starter.git
// Based on SimpleDrawingPlayer-Starter example for CPSC 210; used as reference for JFrame

// https://stackoverflow.com/questions/36395762/how-can-i-display-an-image-in-a-jpanel
// https://stackoverflow.com/questions/6714045/how-to-resize-jlabel-imageicon
// Used as reference on displaying visual component

package ui;

import model.Cart;
import model.Event;
import model.EventLog;
import model.Inventory;
import model.Item;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.models.CartModel;
import ui.models.InventoryModel;
import ui.models.ItemListModel;
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
    private JPanel imagePanel;

    private CartModel cartModel;
    private InventoryModel inventoryModel;

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
        this.imagePanel = new JPanel();

        this.cartModel = new CartModel(new Cart("Cart"));
        this.inventoryModel = new InventoryModel(new Inventory("Inventory"));

//        try {
////            this.inventoryModel.putIntoList(new Item("White Rice", 10, 8.00));
//            for (int i = 0; i < 50; i++) {
//                this.inventoryModel.putIntoList(new Item("" + i + "", i + 1, 8.00));
//            }
//        } catch (NegativeValueException | NotEnoughItemsException e) {
//            throw new RuntimeException(e);
//        } // FOR TESTING PURPOSES // ONLY SHOWS BEFORE SETTING UP MODELS, DOES NOT ACTUALLY SAVE ANYTHING

        this.jsonWriterInv = new JsonWriter(JSON_INV);
        this.jsonReaderInv = new JsonReader(JSON_INV);
        this.jsonWriterCart = new JsonWriter(JSON_CART);
        this.jsonReaderCart = new JsonReader(JSON_CART);
        initializeGraphics();
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

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Do not close immediately
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
//                System.out.println("GUI has been closed");
                printLog(EventLog.getInstance());
                //THEN you can exit the program
                System.exit(0);
            }
        });

        createPanels();
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Creates panels
    private void createPanels() {
        panelSetup(this.buttonPanel1, "Functions for Cart", 0, 0, true);
        panelSetup(this.buttonPanel2, "Functions for Inventory", 0, 1, true);
        panelSetup(this.buttonPanel3, "Functions for Store", 0, 2, true);
        panelSetup(this.imagePanel, "", 0, 3, true);

        panelSetup(this.cartPanel, "Cart List", 1, 0, false);
        panelSetup(this.inventoryPanel, "Inventory List", 2, 0, false);

        setUpTables(this.inventoryModel, this.inventoryPanel);
        setUpTables(this.cartModel, this.cartPanel);

        toolSetUp();

        this.imagePanel.setLayout(new FlowLayout());
        ImageIcon myPicture = new ImageIcon("263142.png");
        Image image = myPicture.getImage(); // transform it
        Image newImg = image.getScaledInstance(WIDTH / 9, HEIGHT / 6,  java.awt.Image.SCALE_SMOOTH);
        myPicture = new ImageIcon(newImg);
        JLabel picLabel = new JLabel(myPicture);
        picLabel.setVerticalAlignment(SwingConstants.CENTER);
        picLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.imagePanel.add(picLabel);
    }

    // MODIFIES: this
    // EFFECTS: Creates tools for panels
    private void toolSetUp() {
        ToCartTool toCartTool = new ToCartTool(this, this.buttonPanel1, this.inventoryModel, this.cartModel);
        FromCartTool fromCartTool = new FromCartTool(this, this.buttonPanel1, this.inventoryModel, this.cartModel);
        PaymentTool paymentTool = new PaymentTool(this, this.buttonPanel1,
                this.cartModel.getItemList());
        AddToInventoryTool addToInventoryTool = new AddToInventoryTool(this, this.buttonPanel2,
                this.inventoryModel);
        RemoveFromInventoryTool removeFromInventoryTool = new RemoveFromInventoryTool(this, this.buttonPanel2,
                this.inventoryModel);
        SetPriceTool setPriceTool = new SetPriceTool(this, this.buttonPanel2, this.inventoryModel);
        SaveTool saveTool = new SaveTool(this, this.buttonPanel3, this.inventoryModel, this.cartModel,
                this.jsonWriterInv, this.jsonWriterCart);
        LoadTool loadTool = new LoadTool(this, this.buttonPanel3, this.inventoryModel, this.cartModel,
                this.jsonReaderInv, this.jsonReaderCart);
        ClearTool clearTool = new ClearTool(this, this.buttonPanel3, this.inventoryModel, this.cartModel);
    }

    // MODIFIES: this
    // EFFECTS: Creates basic layout for panels
    private void panelSetup(JPanel panel, String name, int partWidth, int partHeight, boolean split) {
        Border br = BorderFactory.createLineBorder(Color.black);
        if (split) {
            panel.setLayout(new GridLayout(0,1));
            panel.setBounds((WIDTH * partWidth / 3) + GAP / 2, GAP / 2 + (HEIGHT * partHeight / 4),
                    (WIDTH / 3) - GAP,HEIGHT / 4 - GAP * 5);
        } else {
            panel.setLayout(new FlowLayout());
            panel.setBounds((WIDTH * partWidth / 3) + GAP / 2, GAP / 2 + (HEIGHT * partHeight / 3),
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

    // MODIFIES: this
    // EFFECT: Take ItemList and set up JTable table
    public void setUpTables(ItemListModel itemModel, JPanel panel) {
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

    // EFFECT: Prints the log
    public void printLog(EventLog el) {
        for (Event next : el) {
//            logArea.setText(logArea.getText() + next.toString() + "\n\n");
            System.out.println(next.toString());
        }
//        repaint();
    }

    // EFFECT: Start system
    public static void main(String[] args) {
        new StoreAppGUI();
    }
}
