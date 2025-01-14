package model;

import exceptions.NegativeValueException;
import exceptions.NotEnoughItemsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//class InventoryTest extends ItemListTest {
class InventoryTest {

    protected Inventory list; // Change this if using ItemListTest
    Item rice10 = new Item("White Rice", 10, 8.00);
    Item bread20 = new Item("Bread", 20, 3.50);

    InventoryTest() throws NegativeValueException {
    }

    @BeforeEach
    public void setUp() {
        this.list = new Inventory("List");
        EventLog el = EventLog.getInstance();
        el.clear();
    }

    //NEW
    @Test
    public void testSetNewName() {
        testInitial();

        try {
            list.putIntoList(rice10);
        } catch (NotEnoughItemsException e) {
            fail("NotEnoughItemsException should not be thrown");
        }
        assertTrue(list.getNamed("White Rice"));

        list.setNewName("White Rice", "Brown Rice");
        assertFalse(list.getNamed("White Rice"));
        assertTrue(list.getNamed("Brown Rice"));
    }

    @Test
    public void testSetNewNameMulti() {
        testInitial();

        try {
            list.putIntoList(rice10);
        } catch (NotEnoughItemsException e) {
            fail("NotEnoughItemsException should not be thrown");
        }
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

        try {
            list.putIntoList(rice10);
            list.putIntoList(bread20);
        } catch (NotEnoughItemsException e) {
            fail("NotEnoughItemsException should not be thrown");
        }
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

        try {
            list.putIntoList(rice10);
            list.putIntoList(bread20);
        } catch (NotEnoughItemsException e) {
            fail("NotEnoughItemsException should not be thrown");
        }
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
    public void testSetNewNameNull() {
        testInitial();

        try {
            list.putIntoList(rice10);
        } catch (NotEnoughItemsException e) {
            fail("NotEnoughItemsException should not be thrown");
        }
        assertTrue(list.getNamed("White Rice"));

        list.setNewName("White Rice", "");
        assertFalse(list.getNamed("White Rice"));
        assertTrue(list.getNamed(""));
    }

    @Test
    public void testSetNamedPrice() {
        testInitial();

        try {
            list.putIntoList(rice10);
            assertEquals(8.00, list.getNamedPrice("White Rice"));
            list.setNamedPrice("White Rice", 9);
        } catch (NotEnoughItemsException e) {
            fail("NotEnoughItemsException should not be thrown");
        } catch (NegativeValueException e) {
            fail("NegativeValueException should not be thrown");
        }
        assertEquals(9.00, list.getNamedPrice("White Rice"));

        List<Event> l = new ArrayList<>();
        EventLog el = EventLog.getInstance();
        for (Event next : el) {
            l.add(next);
        }
//        Assertions.assertTrue(l.contains(new Event("White Rice price changed to 9.0 in List")));
//        Assertions.assertFalse(l.contains(new Event("Bread price changed to 4.5 in List")));

        Assertions.assertTrue(containsDescription(l, "White Rice price changed to 9.0 in List"));
        Assertions.assertFalse(containsDescription(l, "Bread price changed to 4.5 in List"));
    }

    @Test
    public void testSetNamedPriceMulti() {
        testInitial();

        try {
            list.putIntoList(rice10);
            assertEquals(8.00, list.getNamedPrice("White Rice"));

            list.setNamedPrice("White Rice", 9);
            assertEquals(9.00, list.getNamedPrice("White Rice"));

            list.setNamedPrice("White Rice", 10);
        } catch (NotEnoughItemsException e) {
            fail("NotEnoughItemsException should not be thrown");
        } catch (NegativeValueException e) {
            fail("NegativeValueException should not be thrown");
        }
        assertEquals(10.00, list.getNamedPrice("White Rice"));

        List<Event> l = new ArrayList<>();
        EventLog el = EventLog.getInstance();
        for (Event next : el) {
            l.add(next);
        }
//        Assertions.assertTrue(l.contains(new Event("White Rice price changed to 9.0 in List")));
//        Assertions.assertTrue(l.contains(new Event("White Rice price changed to 10.0 in List")));
//        Assertions.assertFalse(l.contains(new Event("Bread price changed to 4.5 in List")));

        Assertions.assertTrue(containsDescription(l, "White Rice price changed to 9.0 in List"));
        Assertions.assertTrue(containsDescription(l, "White Rice price changed to 10.0 in List"));
        Assertions.assertFalse(containsDescription(l, "Bread price changed to 4.5 in List"));
    }

    @Test
    public void testSetNamedPrices() {
        testInitial();

        try {
            list.putIntoList(rice10);
            list.putIntoList(bread20);
            assertEquals(8.00, list.getNamedPrice("White Rice"));
            assertEquals(3.50, list.getNamedPrice("Bread"));

            list.setNamedPrice("White Rice", 9);
            assertEquals(9.00, list.getNamedPrice("White Rice"));
            assertEquals(3.50, list.getNamedPrice("Bread"));

            list.setNamedPrice("Bread", 4.5);
        } catch (NotEnoughItemsException e) {
            fail("NotEnoughItemsException should not be thrown");
        } catch (NegativeValueException e) {
            fail("NegativeValueException should not be thrown");
        }

        assertEquals(9.00, list.getNamedPrice("White Rice"));
        assertEquals(4.50, list.getNamedPrice("Bread"));

        List<Event> l = new ArrayList<>();
        EventLog el = EventLog.getInstance();
        for (Event next : el) {
            l.add(next);
        }
//        Assertions.assertTrue(l.contains(new Event("White Rice price changed to 9.0 in List")));
//        Assertions.assertTrue(l.contains(new Event("Bread price changed to 4.5 in List")));

        Assertions.assertTrue(containsDescription(l, "White Rice price changed to 9.0 in List"));
        Assertions.assertTrue(containsDescription(l, "Bread price changed to 4.5 in List"));
    }

    @Test
    public void testSetNamedPricesMulti() {
        testInitial();

        try {
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
        } catch (NotEnoughItemsException e) {
            fail("NotEnoughItemsException should not be thrown");
        } catch (NegativeValueException e) {
            fail("NegativeValueException should not be thrown");
        }

        assertEquals(10.00, list.getNamedPrice("White Rice"));
        assertEquals(5.50, list.getNamedPrice("Bread"));

        List<Event> l = new ArrayList<>();
        EventLog el = EventLog.getInstance();
        for (Event next : el) {
            l.add(next);
        }
//        Assertions.assertTrue(l.contains(new Event("White Rice price changed to 9.0 in List")));
//        Assertions.assertTrue(l.contains(new Event("White Rice price changed to 10.0 in List")));
//        Assertions.assertTrue(l.contains(new Event("Bread price changed to 4.5 in List")));
//        Assertions.assertTrue(l.contains(new Event("Bread price changed to 5.5 in List")));

        Assertions.assertTrue(containsDescription(l, "White Rice price changed to 9.0 in List"));
        Assertions.assertTrue(containsDescription(l, "White Rice price changed to 10.0 in List"));
        Assertions.assertTrue(containsDescription(l, "Bread price changed to 4.5 in List"));
        Assertions.assertTrue(containsDescription(l, "Bread price changed to 5.5 in List"));
    }

    @Test
    public void testSetNamedPriceNegative() {
        testInitial();

        try {
            list.putIntoList(rice10);
            assertEquals(8.00, list.getNamedPrice("White Rice"));
            list.setNamedPrice("White Rice", -8);
            fail("NegativeValueException should be thrown");
        } catch (NotEnoughItemsException e) {
            fail("NotEnoughItemsException should not be thrown");
        } catch (NegativeValueException e) {
            // All clear
        }
        assertEquals(8.00, list.getNamedPrice("White Rice"));
        List<Event> l = new ArrayList<>();
        EventLog el = EventLog.getInstance();
        for (Event next : el) {
            l.add(next);
        }
//        Assertions.assertFalse(l.contains(new Event("White Rice price changed to -8.0 in List")));
//        Assertions.assertFalse(l.contains(new Event("Bread price changed to 4.5 in List")));

        Assertions.assertFalse(containsDescription(l, "White Rice price changed to -8.0 in List"));
        Assertions.assertFalse(containsDescription(l, "Bread price changed to 4.5 in List"));
    }

    protected void testInitial() {
        Assertions.assertEquals(0, list.getInternalList().size());
        Assertions.assertEquals(0, list.getNamedAmount("White Rice"));
        Assertions.assertEquals(0, list.getNamedAmount("Bread"));
    }

    public boolean containsDescription(List<Event> l, String description) {
        boolean result = false;
        for (Event next : l) {
            result = next.getDescription().equals(description);
            if (result) {
                break;
            }
        }
        return result;
    }
}