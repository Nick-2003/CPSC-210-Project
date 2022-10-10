package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CartTest {
    Cart cart;
    Item rice10 = new Item("White Rice", 10, 8.00);
    Item bread20 = new Item("Bread", 20, 3.50);

    @BeforeEach
    public void setUp() {
        cart = new Cart();
    }

    @Test
    public void testPutIntoList() {
        testInitial();

        cart.putIntoList(rice10);
        assertEquals(1, cart.getInternalList().size());
        assertEquals(10, cart.getNamedAmount("White Rice"));
    }

    @Test
    public void testPutIntoListMulti() {
        testInitial();

        cart.putIntoList(rice10);
        assertEquals(1, cart.getInternalList().size());
        assertEquals(10, cart.getNamedAmount("White Rice"));

        cart.putIntoList(rice10);
        assertEquals(1, cart.getInternalList().size());
        assertEquals(20, cart.getNamedAmount("White Rice"));
    }

    @Test
    public void testPutMultiIntoList() {
        testInitial();

        cart.putIntoList(rice10);
        assertEquals(1, cart.getInternalList().size());
        assertEquals(10, cart.getNamedAmount("White Rice"));
        assertEquals(0, cart.getNamedAmount("Bread"));

        cart.putIntoList(bread20);
        assertEquals(2, cart.getInternalList().size());
        assertEquals(10, cart.getNamedAmount("White Rice"));
        assertEquals(20, cart.getNamedAmount("Bread"));
    }

    @Test
    public void testPutIntoListNone() {
        testInitial();

        cart.putIntoList(new Item("White Rice", 0, 8));
        assertEquals(0, cart.getInternalList().size());
        assertEquals(0, cart.getNamedAmount("White Rice"));
    }
    
    @Test
    public void testTakeFromList() {
        testInitial();

        cart.putIntoList(rice10);
        assertEquals(1, cart.getInternalList().size());
        assertEquals(10, cart.getNamedAmount("White Rice"));

        cart.takeFromList(new Item("White Rice", 5, 8.00));
        assertEquals(1, cart.getInternalList().size());
        assertEquals(5, cart.getNamedAmount("White Rice"));
    }

    @Test
    public void testTakeFromListMulti() {
        testInitial();

        assertEquals(0, cart.getInternalList().size());
        assertEquals(0, cart.getNamedAmount("White Rice"));

        cart.putIntoList(rice10);
        assertEquals(1, cart.getInternalList().size());
        assertEquals(10, cart.getNamedAmount("White Rice"));

        cart.takeFromList(new Item("White Rice", 5, 8.00));
        assertEquals(1, cart.getInternalList().size());
        assertEquals(5, cart.getNamedAmount("White Rice"));

        cart.takeFromList(new Item("White Rice", 5, 8.00));
        assertEquals(0, cart.getInternalList().size());
        assertEquals(0, cart.getNamedAmount("White Rice"));
    }

    @Test
    public void testTakeMultiFromList() {
        testInitial();

        cart.putIntoList(rice10);
        cart.putIntoList(bread20);
        assertEquals(2, cart.getInternalList().size());
        assertEquals(10, cart.getNamedAmount("White Rice"));
        assertEquals(20, cart.getNamedAmount("Bread"));

        cart.takeFromList(new Item("White Rice", 5, 8.00));
        assertEquals(2, cart.getInternalList().size());
        assertEquals(5, cart.getNamedAmount("White Rice"));
        assertEquals(20, cart.getNamedAmount("Bread"));

        cart.takeFromList(bread20);
        assertEquals(1, cart.getInternalList().size());
        assertEquals(5, cart.getNamedAmount("White Rice"));
        assertEquals(0, cart.getNamedAmount("Bread"));
    }

    @Test
    public void testTakeFromListNone() {
        testInitial();

        cart.putIntoList(rice10);
        assertEquals(1, cart.getInternalList().size());
        assertEquals(10, cart.getNamedAmount("White Rice"));

        cart.takeFromList(new Item("White Rice", 0, 8));
        assertEquals(1, cart.getInternalList().size());
        assertEquals(10, cart.getNamedAmount("White Rice"));
    }
    
    @Test
    public void testGetNamedPrice() {
        testInitial();

        cart.putIntoList(rice10);
        assertEquals(8, cart.getNamedPrice("White Rice"));
    }

    @Test
    public void testGetNamedPriceMulti() {
        testInitial();

        cart.putIntoList(rice10);
        cart.putIntoList(bread20);
        assertEquals(8, cart.getNamedPrice("White Rice"));
        assertEquals(3.5, cart.getNamedPrice("Bread"));
    }

    @Test
    public void testTotalPrice() {
        testInitial();

        cart.putIntoList(rice10);
        cart.putIntoList(bread20);
        assertEquals(150, cart.totalPrice());
    }

    @Test
    public void testTotalPriceMulti() {
        testInitial();

        cart.putIntoList(rice10);
        cart.putIntoList(bread20);
        assertEquals(150, cart.totalPrice());

        cart.putIntoList(rice10);
        cart.putIntoList(bread20);
        assertEquals(300, cart.totalPrice());
    }

    @Test
    public void testTax() {
        testInitial();

        cart.putIntoList(rice10);
        cart.putIntoList(bread20);
        assertEquals(7.5, cart.tax());
    }

    @Test
    public void testTaxMulti() {
        testInitial();

        cart.putIntoList(rice10);
        cart.putIntoList(bread20);
        assertEquals(7.5, cart.tax());

        cart.putIntoList(rice10);
        cart.putIntoList(bread20);
        assertEquals(15, cart.tax());
    }

    @Test
    public void testFinalPrice() {
        testInitial();

        cart.putIntoList(rice10);
        cart.putIntoList(bread20);
        assertEquals(157.5, cart.finalPrice());
    }

    @Test
    public void testFinalPriceMulti() {
        testInitial();

        cart.putIntoList(rice10);
        cart.putIntoList(bread20);
        assertEquals(157.5, cart.finalPrice());

        cart.putIntoList(rice10);
        cart.putIntoList(bread20);
        assertEquals(315, cart.finalPrice());
    }

    @Test
    public void testClear() {
        testInitial();

        cart.putIntoList(rice10);
        cart.putIntoList(bread20);
        assertEquals(2, cart.getInternalList().size());
        assertEquals(10, cart.getNamedAmount("White Rice"));
        assertEquals(20, cart.getNamedAmount("Bread"));

        cart.clear();
        testInitial();
    }

    @Test
    public void testClearMulti() {
        testInitial();

        cart.putIntoList(rice10);
        cart.putIntoList(bread20);
        assertEquals(2, cart.getInternalList().size());
        assertEquals(10, cart.getNamedAmount("White Rice"));
        assertEquals(20, cart.getNamedAmount("Bread"));

        cart.clear();
        testInitial();

        cart.clear();
        testInitial();
    }

    private void testInitial() {
        assertEquals(0, cart.getInternalList().size());
        assertEquals(0, cart.getNamedAmount("White Rice"));
        assertEquals(0, cart.getNamedAmount("Bread"));
    }
}
