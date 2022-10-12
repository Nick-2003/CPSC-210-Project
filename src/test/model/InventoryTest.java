package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//class InventoryTest extends ItemListTest {
class InventoryTest {

    protected Inventory list; // Change this if using ItemListTest
    Item rice10 = new Item("White Rice", 10, 8.00);
    Item bread20 = new Item("Bread", 20, 3.50);

    @BeforeEach
    public void setUp() {
        this.list = new Inventory();
    }

    //NEW
    @Test
    public void testSetNewName() {
        testInitial();

        list.putIntoList(rice10);
        assertTrue(list.getNamed("White Rice"));

        list.setNewName("White Rice", "Brown Rice");
        assertFalse(list.getNamed("White Rice"));
        assertTrue(list.getNamed("Brown Rice"));
    }

    @Test
    public void testSetNewNameMulti() {
        testInitial();

        list.putIntoList(rice10);
        assertTrue(list.getNamed("White Rice"));

        list.setNewName("White Rice", "Brown Rice");
        assertFalse(list.getNamed("White Rice"));
        assertTrue(list.getNamed("Brown Rice"));

        list.setNewName("Brown Rice", "Rice");
        assertFalse(list.getNamed("White Rice"));
        assertFalse(list.getNamed("Brown Rice"));
        assertTrue(list.getNamed("Rice"));
    }

    @Test
    public void testSetNewNames() {
        testInitial();

        list.putIntoList(rice10);
        list.putIntoList(bread20);
        assertTrue(list.getNamed("White Rice"));
        assertTrue(list.getNamed("Bread"));

        list.setNewName("White Rice", "Brown Rice");
        list.setNewName("Bread", "Toast");
        assertFalse(list.getNamed("White Rice"));
        assertTrue(list.getNamed("Brown Rice"));
        assertFalse(list.getNamed("Bread"));
        assertTrue(list.getNamed("Toast"));

    }

    @Test
    public void testSetNewNamesMulti() {
        testInitial();

        list.putIntoList(rice10);
        list.putIntoList(bread20);
        assertTrue(list.getNamed("White Rice"));
        assertTrue(list.getNamed("Bread"));

        list.setNewName("White Rice", "Brown Rice");
        list.setNewName("Bread", "Toast");
        assertFalse(list.getNamed("White Rice"));
        assertTrue(list.getNamed("Brown Rice"));
        assertFalse(list.getNamed("Bread"));
        assertTrue(list.getNamed("Toast"));

        list.setNewName("Brown Rice", "Rice");
        list.setNewName("Toast", "Sandwich");
        assertFalse(list.getNamed("Brown Rice"));
        assertTrue(list.getNamed("Rice"));
        assertFalse(list.getNamed("Toast"));
        assertTrue(list.getNamed("Sandwich"));
    }

    @Test
    public void testSetNamedPrice() {
        testInitial();

        list.putIntoList(rice10);
        assertEquals(8.00, list.getNamedPrice("White Rice"));
        list.setNamedPrice("White Rice", 9);
        assertEquals(9.00, list.getNamedPrice("White Rice"));
    }

    @Test
    public void testSetNamedPriceMulti() {
        testInitial();

        list.putIntoList(rice10);
        assertEquals(8.00, list.getNamedPrice("White Rice"));

        list.setNamedPrice("White Rice", 9);
        assertEquals(9.00, list.getNamedPrice("White Rice"));

        list.setNamedPrice("White Rice", 10);
        assertEquals(10.00, list.getNamedPrice("White Rice"));
    }

    @Test
    public void testSetNamedPrices() {
        testInitial();

        list.putIntoList(rice10);
        list.putIntoList(bread20);
        assertEquals(8.00, list.getNamedPrice("White Rice"));
        assertEquals(3.50, list.getNamedPrice("Bread"));

        list.setNamedPrice("White Rice", 9);
        assertEquals(9.00, list.getNamedPrice("White Rice"));
        assertEquals(3.50, list.getNamedPrice("Bread"));

        list.setNamedPrice("Bread", 4.5);
        assertEquals(9.00, list.getNamedPrice("White Rice"));
        assertEquals(4.50, list.getNamedPrice("Bread"));
    }

    @Test
    public void testSetNamedPricesMulti() {
        testInitial();

        list.putIntoList(rice10);
        list.putIntoList(bread20);
        assertEquals(8.00, list.getNamedPrice("White Rice"));
        assertEquals(3.50, list.getNamedPrice("Bread"));

        list.setNamedPrice("White Rice", 9);
        list.setNamedPrice("Bread", 4.5);
        assertEquals(9.00, list.getNamedPrice("White Rice"));
        assertEquals(4.50, list.getNamedPrice("Bread"));

        list.setNamedPrice("White Rice", 10);
        list.setNamedPrice("Bread", 5.5);
        assertEquals(10.00, list.getNamedPrice("White Rice"));
        assertEquals(5.50, list.getNamedPrice("Bread"));
    }

    protected void testInitial() {
        Assertions.assertEquals(0, list.getInternalList().size());
        Assertions.assertEquals(0, list.getNamedAmount("White Rice"));
        Assertions.assertEquals(0, list.getNamedAmount("Bread"));
    }
}