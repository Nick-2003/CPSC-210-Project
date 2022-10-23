package model;

import exceptions.NegativeValueException;
import exceptions.NotEnoughItemsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    }

    protected void testInitial() {
        Assertions.assertEquals(0, list.getInternalList().size());
        Assertions.assertFalse(list.getNamed("White Rice"));
        Assertions.assertFalse(list.getNamed("Bread"));
    }
}
