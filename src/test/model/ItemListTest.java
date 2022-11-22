package model;

import exceptions.NegativeValueException;
import exceptions.NotEnoughItemsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;


public class ItemListTest {
    private ItemList list;
    Item rice10 = new Item("White Rice", 10, 8.00);
    Item bread20 = new Item("Bread", 20, 3.50);

    public ItemListTest() throws NegativeValueException {
    }

    @BeforeEach
    public void setUp() {
        this.list = new ItemList("List");
        EventLog el = EventLog.getInstance();
        el.clear();
    }

    @Test
    public void testPutIntoList() {
        testInitial();

        try {
            list.putIntoList(rice10);
        } catch (NotEnoughItemsException e) {
            fail("NotEnoughItemsException should not be thrown");
        }
        Assertions.assertEquals(1, list.getInternalList().size());
        Assertions.assertEquals(10, list.getNamedAmount("White Rice"));

        List<Event> l = new ArrayList<>();
        EventLog el = EventLog.getInstance();
        for (Event next : el) {
            l.add(next);
        }
//        Assertions.assertTrue(l.contains(new Event("10 White Rice put into List")));
//        Assertions.assertFalse(l.contains(new Event("0 Bread put into List")));

        Assertions.assertTrue(containsDescription(l, "10 White Rice put into List"));
        Assertions.assertFalse(containsDescription(l, "0 Bread put into List"));
    }

    @Test
    public void testPutIntoListMulti() {
        testInitial();

        try {
            list.putIntoList(rice10);
            Assertions.assertEquals(1, list.getInternalList().size());
            Assertions.assertEquals(10, list.getNamedAmount("White Rice"));

            list.putIntoList(rice10);
        } catch (NotEnoughItemsException e) {
            fail("NotEnoughItemsException should not be thrown");
        }
        Assertions.assertEquals(1, list.getInternalList().size());
        Assertions.assertEquals(20, list.getNamedAmount("White Rice"));

        List<Event> l = new ArrayList<>();
        EventLog el = EventLog.getInstance();
        for (Event next : el) {
            l.add(next);
        }
//        Assertions.assertTrue(l.contains(new Event("10 White Rice put into List")));
//        Assertions.assertFalse(l.contains(new Event("0 Bread put into List")));

        Assertions.assertTrue(containsDescription(l, "10 White Rice put into List"));
        Assertions.assertFalse(containsDescription(l, "0 Bread put into List"));
    }

    @Test
    public void testPutMultiIntoList() {
        testInitial();

        try {
            list.putIntoList(rice10);
            Assertions.assertEquals(1, list.getInternalList().size());
            Assertions.assertEquals(10, list.getNamedAmount("White Rice"));
            Assertions.assertEquals(0, list.getNamedAmount("Bread"));

            list.putIntoList(bread20);
        } catch (NotEnoughItemsException e) {
            fail("NotEnoughItemsException should not be thrown");
        }

        Assertions.assertEquals(2, list.getInternalList().size());
        Assertions.assertEquals(10, list.getNamedAmount("White Rice"));
        Assertions.assertEquals(20, list.getNamedAmount("Bread"));

        List<Event> l = new ArrayList<>();
        EventLog el = EventLog.getInstance();
        for (Event next : el) {
            l.add(next);
        }
//        Assertions.assertTrue(l.contains(new Event("10 White Rice put into List")));
//        Assertions.assertTrue(l.contains(new Event("20 Bread put into List")));

        Assertions.assertTrue(containsDescription(l, "10 White Rice put into List"));
        Assertions.assertTrue(containsDescription(l, "20 Bread put into List"));
    }

    @Test
    public void testPutIntoListNone() {
        testInitial();

        try {
            list.putIntoList(new Item("White Rice", 0, 8));
        } catch (NotEnoughItemsException e) {
            fail("NotEnoughItemsException should not be thrown");
        } catch (NegativeValueException e) {
            fail("NegativeValueException should not be thrown");
        }
        Assertions.assertEquals(0, list.getInternalList().size());
        Assertions.assertEquals(0, list.getNamedAmount("White Rice"));

        List<Event> l = new ArrayList<>();
        EventLog el = EventLog.getInstance();
        for (Event next : el) {
            l.add(next);
        }
//        Assertions.assertFalse(l.contains(new Event("0 White Rice put into List")));
//        Assertions.assertFalse(l.contains(new Event("0 Bread put into List")));

        Assertions.assertFalse(containsDescription(l, "0 White Rice put into List"));
        Assertions.assertFalse(containsDescription(l, "0 Bread put into List"));
    }

    @Test
    public void testTakeFromList() {
        testInitial();

        try {
            list.putIntoList(rice10);
            Assertions.assertEquals(1, list.getInternalList().size());
            Assertions.assertEquals(10, list.getNamedAmount("White Rice"));

            list.takeFromList(new Item("White Rice", 5, 8.00));
        } catch (NotEnoughItemsException e) {
            fail("NotEnoughItemsException should not be thrown");
        } catch (NegativeValueException e) {
            fail("NegativeValueException should not be thrown");
        }

        Assertions.assertEquals(1, list.getInternalList().size());
        Assertions.assertEquals(5, list.getNamedAmount("White Rice"));

        List<Event> l = new ArrayList<>();
        EventLog el = EventLog.getInstance();
        for (Event next : el) {
            l.add(next);
        }
//        Assertions.assertTrue(l.contains(new Event("5 White Rice taken from List")));
//        Assertions.assertFalse(l.contains(new Event("0 Bread taken from List")));

        Assertions.assertTrue(containsDescription(l, "5 White Rice taken from List"));
        Assertions.assertFalse(containsDescription(l, "0 Bread taken from List"));
    }

    @Test
    public void testTakeFromListMulti() {
        testInitial();

        Assertions.assertEquals(0, list.getInternalList().size());
        Assertions.assertEquals(0, list.getNamedAmount("White Rice"));

        try {
            list.putIntoList(rice10);
            Assertions.assertEquals(1, list.getInternalList().size());
            Assertions.assertEquals(10, list.getNamedAmount("White Rice"));

            list.takeFromList(new Item("White Rice", 5, 8.00));
            Assertions.assertEquals(1, list.getInternalList().size());
            Assertions.assertEquals(5, list.getNamedAmount("White Rice"));

            list.takeFromList(new Item("White Rice", 5, 8.00));
        } catch (NotEnoughItemsException e) {
            fail("NotEnoughItemsException should not be thrown");
        } catch (NegativeValueException e) {
            fail("NegativeValueException should not be thrown");
        }
        Assertions.assertEquals(0, list.getInternalList().size());
        Assertions.assertEquals(0, list.getNamedAmount("White Rice"));

        List<Event> l = new ArrayList<>();
        EventLog el = EventLog.getInstance();
        for (Event next : el) {
            l.add(next);
        }
//        Assertions.assertTrue(l.contains(new Event("5 White Rice taken from List")));
//        Assertions.assertFalse(l.contains(new Event("0 Bread taken from List")));

        Assertions.assertTrue(containsDescription(l, "5 White Rice taken from List"));
        Assertions.assertFalse(containsDescription(l, "0 Bread taken from List"));
    }

    @Test
    public void testTakeMultiFromList() {
        testInitial();

        try {
            list.putIntoList(rice10);
            list.putIntoList(bread20);
            Assertions.assertEquals(2, list.getInternalList().size());
            Assertions.assertEquals(10, list.getNamedAmount("White Rice"));
            Assertions.assertEquals(20, list.getNamedAmount("Bread"));

            list.takeFromList(new Item("White Rice", 5, 8.00));
            Assertions.assertEquals(2, list.getInternalList().size());
            Assertions.assertEquals(5, list.getNamedAmount("White Rice"));
            Assertions.assertEquals(20, list.getNamedAmount("Bread"));

            list.takeFromList(bread20);
        } catch (NotEnoughItemsException e) {
            fail("NotEnoughItemsException should not be thrown");
        } catch (NegativeValueException e) {
            fail("NegativeValueException should not be thrown");
        }

        Assertions.assertEquals(1, list.getInternalList().size());
        Assertions.assertEquals(5, list.getNamedAmount("White Rice"));
        Assertions.assertEquals(0, list.getNamedAmount("Bread"));

        List<Event> l = new ArrayList<>();
        EventLog el = EventLog.getInstance();
        for (Event next : el) {
            l.add(next);
        }
//        Assertions.assertTrue(l.contains(new Event("5 White Rice taken from List")));
//        Assertions.assertTrue(l.contains(new Event("20 Bread taken from List")));

        Assertions.assertTrue(containsDescription(l, "5 White Rice taken from List"));
        Assertions.assertTrue(containsDescription(l, "20 Bread taken from List"));
    }

    @Test
    public void testTakeFromListNone() {
        testInitial();

        try {
            list.putIntoList(rice10);
            Assertions.assertEquals(1, list.getInternalList().size());
            Assertions.assertEquals(10, list.getNamedAmount("White Rice"));

            list.takeFromList(new Item("White Rice", 0, 8));
        } catch (NotEnoughItemsException e) {
            fail("NotEnoughItemsException should not be thrown");
        } catch (NegativeValueException e) {
            fail("NegativeValueException should not be thrown");
        }
        Assertions.assertEquals(1, list.getInternalList().size());
        Assertions.assertEquals(10, list.getNamedAmount("White Rice"));

        List<Event> l = new ArrayList<>();
        EventLog el = EventLog.getInstance();
        for (Event next : el) {
            l.add(next);
        }
//        Assertions.assertFalse(l.contains(new Event("0 White Rice taken from List")));
//        Assertions.assertFalse(l.contains(new Event("0 Bread taken from List")));

        Assertions.assertFalse(containsDescription(l, "0 White Rice taken from List"));
        Assertions.assertFalse(containsDescription(l, "0 Bread taken from List"));
    }

    @Test
    public void testTakeFromListNegative() {
        testInitial();

        try {
            list.putIntoList(rice10);
            Assertions.assertEquals(1, list.getInternalList().size());
            Assertions.assertEquals(10, list.getNamedAmount("White Rice"));

            list.takeFromList(new Item("White Rice", -5, 8.00));
            fail("NegativeValueException should be thrown");
        } catch (NotEnoughItemsException e) {
            fail("NotEnoughItemsException should not be thrown");
        } catch (NegativeValueException e) {
            // All clear
        }
        Assertions.assertEquals(1, list.getInternalList().size());
        Assertions.assertEquals(10, list.getNamedAmount("White Rice"));

        List<Event> l = new ArrayList<>();
        EventLog el = EventLog.getInstance();
        for (Event next : el) {
            l.add(next);
        }
//        Assertions.assertFalse(l.contains(new Event("-5 White Rice taken from List")));
//        Assertions.assertFalse(l.contains(new Event("Bread taken from List")));

        Assertions.assertFalse(containsDescription(l, "-5 White Rice taken from List"));
        Assertions.assertFalse(containsDescription(l, "0 Bread taken from List"));
    }

    @Test
    public void testTakeFromListNotEnough() {
        testInitial();

        try {
            list.putIntoList(rice10);
            Assertions.assertEquals(1, list.getInternalList().size());
            Assertions.assertEquals(10, list.getNamedAmount("White Rice"));

            list.takeFromList(new Item("White Rice", 15, 8.00));
            fail("NotEnoughItemsException should be thrown");
        } catch (NotEnoughItemsException e) {
            // All clear
        } catch (NegativeValueException e) {
            fail("NegativeValueException should not be thrown");
        }
        Assertions.assertEquals(1, list.getInternalList().size());
        Assertions.assertEquals(10, list.getNamedAmount("White Rice"));

        List<Event> l = new ArrayList<>();
        EventLog el = EventLog.getInstance();
        for (Event next : el) {
            l.add(next);
        }
//        Assertions.assertFalse(l.contains(new Event("15 White Rice taken from List")));
//        Assertions.assertFalse(l.contains(new Event("0 Bread taken from List")));

        Assertions.assertFalse(containsDescription(l, "15 White Rice taken from List"));
        Assertions.assertFalse(containsDescription(l, "0 Bread taken from List"));
    }

    @Test
    public void testGetNamed() {
        testInitial();

        try {
            list.putIntoList(rice10);
        } catch (NotEnoughItemsException e) {
            fail("NotEnoughItemsException should not be thrown");
        }
        Assertions.assertTrue(list.getNamed("White Rice"));
    }

    @Test
    public void testGetNamedMulti() {
        testInitial();

        try {
            list.putIntoList(rice10);
            Assertions.assertTrue(list.getNamed("White Rice"));
            Assertions.assertFalse(list.getNamed("Bread"));
            list.putIntoList(bread20);
        } catch (NotEnoughItemsException e) {
            fail("NotEnoughItemsException should not be thrown");
        }
        Assertions.assertTrue(list.getNamed("White Rice"));
        Assertions.assertTrue(list.getNamed("Bread"));
    }

    @Test
    public void testGetNamedAmount() {
        testInitial();

        try {
            list.putIntoList(rice10);
        } catch (NotEnoughItemsException e) {
            fail("NotEnoughItemsException should not be thrown");
        }
        Assertions.assertEquals(10, list.getNamedAmount("White Rice"));
    }

    @Test
    public void testGetNamedAmountMulti() {
        testInitial();

        try {
            list.putIntoList(rice10);
            list.putIntoList(bread20);
        } catch (NotEnoughItemsException e) {
            fail("NotEnoughItemsException should not be thrown");
        }
        Assertions.assertEquals(10, list.getNamedAmount("White Rice"));
        Assertions.assertEquals(20, list.getNamedAmount("Bread"));
    }

    @Test
    public void testGetNamedAmountNone() {
        testInitial();

        Assertions.assertEquals(0, list.getNamedAmount("White Rice"));
    }

    @Test
    public void testGetNamedPrice() {
        testInitial();

        try {
            list.putIntoList(rice10);
        } catch (NotEnoughItemsException e) {
            fail("NotEnoughItemsException should not be thrown");
        }
        Assertions.assertEquals(8, list.getNamedPrice("White Rice"));
    }

    @Test
    public void testGetNamedPriceMulti() {
        testInitial();

        try {
            list.putIntoList(rice10);
            list.putIntoList(bread20);
        } catch (NotEnoughItemsException e) {
            fail("NotEnoughItemsException should not be thrown");
        }
        Assertions.assertEquals(8, list.getNamedPrice("White Rice"));
        Assertions.assertEquals(3.5, list.getNamedPrice("Bread"));
    }

    @Test
    public void testGetNamedPriceNone() {
        testInitial();

        Assertions.assertEquals(0, list.getNamedPrice("White Rice"));
    }

    @Test
    public void testClear() {
        testInitial();

        try {
            list.putIntoList(rice10);
            list.putIntoList(bread20);
        } catch (NotEnoughItemsException e) {
            fail("NotEnoughItemsException should not be thrown");
        }
        Assertions.assertEquals(2, list.getInternalList().size());
        Assertions.assertEquals(10, list.getNamedAmount("White Rice"));
        Assertions.assertEquals(20, list.getNamedAmount("Bread"));

        list.clear();
        testInitial();

        List<Event> l = new ArrayList<>();
        EventLog el = EventLog.getInstance();
        for (Event next : el) {
            l.add(next);
        }
//        Assertions.assertTrue(l.contains(new Event("List cleared")));

        Assertions.assertTrue(containsDescription(l, "List cleared"));
    }

    @Test
    public void testClearMulti() {
        testInitial();

        try {
            list.putIntoList(rice10);
            list.putIntoList(bread20);
        } catch (NotEnoughItemsException e) {
            fail("NotEnoughItemsException should not be thrown");
        }
        Assertions.assertEquals(2, list.getInternalList().size());
        Assertions.assertEquals(10, list.getNamedAmount("White Rice"));
        Assertions.assertEquals(20, list.getNamedAmount("Bread"));

        list.clear();
        testInitial();

        list.clear();
        testInitial();

        List<Event> l = new ArrayList<>();
        EventLog el = EventLog.getInstance();
        for (Event next : el) {
            l.add(next);
        }
//        Assertions.assertTrue(l.contains(new Event("List cleared")));

        Assertions.assertTrue(containsDescription(l, "List cleared"));
    }

    protected void testInitial() {
        Assertions.assertEquals(0, list.getInternalList().size());
        Assertions.assertFalse(list.getNamed("White Rice"));
        Assertions.assertFalse(list.getNamed("Bread"));
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
