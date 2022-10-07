package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {
    Inventory inventory;

    @BeforeEach
    public void setUp() {
        inventory = new Inventory();
    }

    @Test
    public void testPutIntoList() {
        assertEquals(0, inventory.getInternalList().size());
        assertEquals(0, inventory.availableAmount("White Rice"));

        inventory.putIntoList(new Item("White Rice", 10, 8.00));
        assertEquals(1, inventory.getInternalList().size());
        assertEquals(10, inventory.availableAmount("White Rice"));
    }

    @Test
    public void testPutIntoListMulti() {
        assertEquals(0, inventory.getInternalList().size());
        assertEquals(0, inventory.availableAmount("White Rice"));

        inventory.putIntoList(new Item("White Rice", 10, 8.00));
        assertEquals(1, inventory.getInternalList().size());
        assertEquals(10, inventory.availableAmount("White Rice"));

        inventory.putIntoList(new Item("White Rice", 10, 8.00));
        assertEquals(1, inventory.getInternalList().size());
        assertEquals(20, inventory.availableAmount("White Rice"));
    }

    @Test
    public void testPutMultiIntoList() {
        assertEquals(0, inventory.getInternalList().size());
        assertEquals(0, inventory.availableAmount("White Rice"));
        assertEquals(0, inventory.availableAmount("Bread"));

        inventory.putIntoList(new Item("White Rice", 10, 8.00));
        assertEquals(1, inventory.getInternalList().size());
        assertEquals(10, inventory.availableAmount("White Rice"));
        assertEquals(0, inventory.availableAmount("Bread"));

        inventory.putIntoList(new Item("Bread", 20, 3.00));
        assertEquals(2, inventory.getInternalList().size());
        assertEquals(10, inventory.availableAmount("White Rice"));
        assertEquals(20, inventory.availableAmount("Bread"));
    }

    @Test
    public void testTakeFromList() {
        inventory.putIntoList(new Item("White Rice", 10, 8.00));
        assertEquals(1, inventory.getInternalList().size());
        assertEquals(10, inventory.availableAmount("White Rice"));

        inventory.takeFromList(new Item("White Rice", 5, 8.00));
        assertEquals(1, inventory.getInternalList().size());
        assertEquals(5, inventory.availableAmount("White Rice"));
    }

    @Test
    public void testTakeFromListMulti() {
        assertEquals(0, inventory.getInternalList().size());
        assertEquals(0, inventory.availableAmount("White Rice"));

        inventory.putIntoList(new Item("White Rice", 10, 8.00));
        assertEquals(1, inventory.getInternalList().size());
        assertEquals(10, inventory.availableAmount("White Rice"));

        inventory.takeFromList(new Item("White Rice", 5, 8.00));
        assertEquals(1, inventory.getInternalList().size());
        assertEquals(5, inventory.availableAmount("White Rice"));

        inventory.takeFromList(new Item("White Rice", 5, 8.00));
        assertEquals(0, inventory.getInternalList().size());
        assertEquals(0, inventory.availableAmount("White Rice"));
    }

    @Test
    public void testTakeMultiFromList() {
        inventory.putIntoList(new Item("White Rice", 10, 8.00));
        inventory.putIntoList(new Item("Bread", 20, 3.00));
        assertEquals(2, inventory.getInternalList().size());
        assertEquals(10, inventory.availableAmount("White Rice"));
        assertEquals(20, inventory.availableAmount("Bread"));

        inventory.takeFromList(new Item("White Rice", 5, 8.00));
        assertEquals(2, inventory.getInternalList().size());
        assertEquals(5, inventory.availableAmount("White Rice"));
        assertEquals(20, inventory.availableAmount("Bread"));

        inventory.takeFromList(new Item("Bread", 20, 3.00));
        assertEquals(1, inventory.getInternalList().size());
        assertEquals(5, inventory.availableAmount("White Rice"));
        assertEquals(0, inventory.availableAmount("Bread"));
    }
}