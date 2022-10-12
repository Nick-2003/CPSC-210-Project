package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

//public class CartTest extends ItemListTest {
public class CartTest {

    protected Cart list; // Change this if using ItemListTest
    Item rice10 = new Item("White Rice", 10, 8.00);
    Item bread20 = new Item("Bread", 20, 3.50);

    @BeforeEach
    public void setUp() {
        this.list = new Cart();
    }

    ////
    @Test
    public void testTotalPrice() {
        testInitial();

        this.list.putIntoList(rice10);
        this.list.putIntoList(bread20);
        assertEquals(150, this.list.totalPrice());
    }

    @Test
    public void testTotalPriceMulti() {
        testInitial();

        list.putIntoList(rice10);
        list.putIntoList(bread20);
        assertEquals(150, list.totalPrice());

        list.putIntoList(rice10);
        list.putIntoList(bread20);
        assertEquals(300, list.totalPrice());
    }

    @Test
    public void testTax() {
        testInitial();

        list.putIntoList(rice10);
        list.putIntoList(bread20);
        assertEquals(7.5, list.tax());
    }

    @Test
    public void testTaxMulti() {
        testInitial();

        list.putIntoList(rice10);
        list.putIntoList(bread20);
        assertEquals(7.5, list.tax());

        list.putIntoList(rice10);
        list.putIntoList(bread20);
        assertEquals(15, list.tax());
    }

    @Test
    public void testFinalPrice() {
        testInitial();

        list.putIntoList(rice10);
        list.putIntoList(bread20);
        assertEquals(157.5, list.finalPrice());
    }

    @Test
    public void testFinalPriceMulti() {
        testInitial();

        list.putIntoList(rice10);
        list.putIntoList(bread20);
        assertEquals(157.5, list.finalPrice());

        list.putIntoList(rice10);
        list.putIntoList(bread20);
        assertEquals(315, list.finalPrice());
    }

    protected void testInitial() {
        Assertions.assertEquals(0, list.getInternalList().size());
        Assertions.assertEquals(0, list.getNamedAmount("White Rice"));
        Assertions.assertEquals(0, list.getNamedAmount("Bread"));
    }
}
