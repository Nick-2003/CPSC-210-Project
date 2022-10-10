package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {
    Inventory inventory;
    Item rice10 = new Item("White Rice", 10, 8);
    Item bread20 = new Item("Bread", 20, 3.5);

    @BeforeEach
    public void setUp() {
        this.inventory = new Inventory();

    }

    @Test
    public void testPutIntoList() {
        testInitial();

        inventory.putIntoList(rice10);
        assertEquals(1, inventory.getInternalList().size());
        assertEquals(10, inventory.getNamedAmount("White Rice"));
    }

    @Test
    public void testPutIntoListMulti() {
        testInitial();

        inventory.putIntoList(rice10);
        assertEquals(1, inventory.getInternalList().size());
        assertEquals(10, inventory.getNamedAmount("White Rice"));

        inventory.putIntoList(rice10);
        assertEquals(1, inventory.getInternalList().size());
        assertEquals(20, inventory.getNamedAmount("White Rice"));
    }

    @Test
    public void testPutMultiIntoList() {
        testInitial();

        inventory.putIntoList(rice10);
        assertEquals(1, inventory.getInternalList().size());
        assertEquals(10, inventory.getNamedAmount("White Rice"));
        assertEquals(0, inventory.getNamedAmount("Bread"));

        inventory.putIntoList(bread20);
        assertEquals(2, inventory.getInternalList().size());
        assertEquals(10, inventory.getNamedAmount("White Rice"));
        assertEquals(20, inventory.getNamedAmount("Bread"));
    }

    @Test
    public void testPutIntoListNone() {
        testInitial();

        inventory.putIntoList(new Item("White Rice", 0, 8));
        assertEquals(0, inventory.getInternalList().size());
        assertEquals(0, inventory.getNamedAmount("White Rice"));
    }

    @Test
    public void testTakeFromList() {
        testInitial();

        inventory.putIntoList(rice10);
        assertEquals(1, inventory.getInternalList().size());
        assertEquals(10, inventory.getNamedAmount("White Rice"));

        inventory.takeFromList(new Item("White Rice", 5, 8));
        assertEquals(1, inventory.getInternalList().size());
        assertEquals(5, inventory.getNamedAmount("White Rice"));
    }

    @Test
    public void testTakeFromListMulti() {
        testInitial();

        assertEquals(0, inventory.getInternalList().size());
        assertEquals(0, inventory.getNamedAmount("White Rice"));

        inventory.putIntoList(rice10);
        assertEquals(1, inventory.getInternalList().size());
        assertEquals(10, inventory.getNamedAmount("White Rice"));

        inventory.takeFromList(new Item("White Rice", 5, 8));
        assertEquals(1, inventory.getInternalList().size());
        assertEquals(5, inventory.getNamedAmount("White Rice"));

        inventory.takeFromList(new Item("White Rice", 5, 8));
        assertEquals(0, inventory.getInternalList().size());
        assertEquals(0, inventory.getNamedAmount("White Rice"));
    }

    @Test
    public void testTakeMultiFromList() {
        testInitial();

        inventory.putIntoList(rice10);
        inventory.putIntoList(bread20);
        assertEquals(2, inventory.getInternalList().size());
        assertEquals(10, inventory.getNamedAmount("White Rice"));
        assertEquals(20, inventory.getNamedAmount("Bread"));

        inventory.takeFromList(new Item("White Rice", 5, 8));
        assertEquals(2, inventory.getInternalList().size());
        assertEquals(5, inventory.getNamedAmount("White Rice"));
        assertEquals(20, inventory.getNamedAmount("Bread"));

        inventory.takeFromList(bread20);
        assertEquals(1, inventory.getInternalList().size());
        assertEquals(5, inventory.getNamedAmount("White Rice"));
        assertEquals(0, inventory.getNamedAmount("Bread"));
    }

    @Test
    public void testTakeFromListNone() {
        testInitial();

        inventory.putIntoList(rice10);
        assertEquals(1, inventory.getInternalList().size());
        assertEquals(10, inventory.getNamedAmount("White Rice"));

        inventory.takeFromList(new Item("White Rice", 0, 8));
        assertEquals(1, inventory.getInternalList().size());
        assertEquals(10, inventory.getNamedAmount("White Rice"));
    }

    @Test
    public void testGetNamedPrice() {
        testInitial();

        inventory.putIntoList(rice10);
        assertEquals(8.00, inventory.getNamedPrice("White Rice"));
    }

    @Test
    public void testGetNamedPriceMulti() {
        testInitial();

        inventory.putIntoList(rice10);
        inventory.putIntoList(bread20);
        assertEquals(8.00, inventory.getNamedPrice("White Rice"));
        assertEquals(3.5, inventory.getNamedPrice("Bread"));
    }

    @Test
    public void testSetNamedPrice() {
        testInitial();

        inventory.putIntoList(rice10);
        assertEquals(8.00, inventory.getNamedPrice("White Rice"));
        inventory.setNamedPrice("White Rice", 9);
        assertEquals(9.00, inventory.getNamedPrice("White Rice"));
    }

    @Test
    public void testSetNamedPriceMulti() {
        testInitial();

        inventory.putIntoList(rice10);
        assertEquals(8.00, inventory.getNamedPrice("White Rice"));

        inventory.setNamedPrice("White Rice", 9);
        assertEquals(9.00, inventory.getNamedPrice("White Rice"));

        inventory.setNamedPrice("White Rice", 10);
        assertEquals(10.00, inventory.getNamedPrice("White Rice"));
    }

    @Test
    public void testSetNamedPrices() {
        testInitial();

        inventory.putIntoList(rice10);
        inventory.putIntoList(bread20);
        assertEquals(8.00, inventory.getNamedPrice("White Rice"));
        assertEquals(3.50, inventory.getNamedPrice("Bread"));

        inventory.setNamedPrice("White Rice", 9);
        assertEquals(9.00, inventory.getNamedPrice("White Rice"));
        assertEquals(3.50, inventory.getNamedPrice("Bread"));

        inventory.setNamedPrice("Bread", 4.5);
        assertEquals(9.00, inventory.getNamedPrice("White Rice"));
        assertEquals(4.50, inventory.getNamedPrice("Bread"));
    }

    @Test
    public void testSetNamedPricesMulti() {
        testInitial();

        inventory.putIntoList(rice10);
        inventory.putIntoList(bread20);
        assertEquals(8.00, inventory.getNamedPrice("White Rice"));
        assertEquals(3.50, inventory.getNamedPrice("Bread"));

        inventory.setNamedPrice("White Rice", 9);
        inventory.setNamedPrice("Bread", 4.5);
        assertEquals(9.00, inventory.getNamedPrice("White Rice"));
        assertEquals(4.50, inventory.getNamedPrice("Bread"));

        inventory.setNamedPrice("White Rice", 10);
        inventory.setNamedPrice("Bread", 5.5);
        assertEquals(10.00, inventory.getNamedPrice("White Rice"));
        assertEquals(5.50, inventory.getNamedPrice("Bread"));
    }

    @Test
    public void testClear() {
        testInitial();

        inventory.putIntoList(rice10);
        inventory.putIntoList(bread20);
        assertEquals(2, inventory.getInternalList().size());
        assertEquals(10.00, inventory.getNamedAmount("White Rice"));
        assertEquals(20.00, inventory.getNamedAmount("Bread"));

        inventory.clear();
        testInitial();
    }

    @Test
    public void testClearMulti() {
        testInitial();

        inventory.putIntoList(rice10);
        inventory.putIntoList(bread20);
        assertEquals(2, inventory.getInternalList().size());
        assertEquals(10.00, inventory.getNamedAmount("White Rice"));
        assertEquals(20.00, inventory.getNamedAmount("Bread"));

        inventory.clear();
        testInitial();

        inventory.clear();
        testInitial();
    }

    private void testInitial() {
        assertEquals(0, inventory.getInternalList().size());
        assertEquals(0, inventory.getNamedAmount("White Rice"));
        assertEquals(0, inventory.getNamedAmount("Bread"));
    }
}