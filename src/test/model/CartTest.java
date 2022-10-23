package model;

import exceptions.NegativeValueException;
import exceptions.NotEnoughItemsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

//public class CartTest extends ItemListTest {
public class CartTest {

    protected Cart list; // Change this if using ItemListTest
    Item rice10 = new Item("White Rice", 10, 8.00);
    Item bread20 = new Item("Bread", 20, 3.50);

    public CartTest() throws NegativeValueException {
    }

    @BeforeEach
    public void setUp() {
        this.list = new Cart("List");
    }

    ////
    @Test
    public void testTotalPrice() {
        testInitial();

        try {
            this.list.putIntoList(rice10);
            this.list.putIntoList(bread20);
        } catch (NotEnoughItemsException e) {
            fail("NotEnoughItemsException should not be thrown");
        }
        assertEquals(150, this.list.totalPrice());
    }

    @Test
    public void testTotalPriceMulti() {
        testInitial();

        try {
            this.list.putIntoList(rice10);
            this.list.putIntoList(bread20);
            assertEquals(150, list.totalPrice());

            list.putIntoList(rice10);
            list.putIntoList(bread20);
        } catch (NotEnoughItemsException e) {
            fail("NotEnoughItemsException should not be thrown");
        }
        assertEquals(300, list.totalPrice());
    }

    @Test
    public void testTax() {
        testInitial();

        try {
            this.list.putIntoList(rice10);
            this.list.putIntoList(bread20);
        } catch (NotEnoughItemsException e) {
            fail("NotEnoughItemsException should not be thrown");
        }
        assertEquals(7.5, list.tax());
    }

    @Test
    public void testTaxMulti() {
        testInitial();

        try {
            this.list.putIntoList(rice10);
            this.list.putIntoList(bread20);
            assertEquals(7.5, list.tax());

            list.putIntoList(rice10);
            list.putIntoList(bread20);
        } catch (NotEnoughItemsException e) {
            fail("NotEnoughItemsException should not be thrown");
        }

        assertEquals(15, list.tax());
    }

    @Test
    public void testFinalPrice() {
        testInitial();

        try {
            this.list.putIntoList(rice10);
            this.list.putIntoList(bread20);
        } catch (NotEnoughItemsException e) {
            fail("NotEnoughItemsException should not be thrown");
        }
        assertEquals(157.5, list.finalPrice());
    }

    @Test
    public void testFinalPriceMulti() {
        testInitial();

        try {
            this.list.putIntoList(rice10);
            this.list.putIntoList(bread20);
            assertEquals(157.5, list.finalPrice());

            list.putIntoList(rice10);
            list.putIntoList(bread20);
        } catch (NotEnoughItemsException e) {
            fail("NotEnoughItemsException should not be thrown");
        }

        assertEquals(315, list.finalPrice());
    }

    protected void testInitial() {
        Assertions.assertEquals(0, list.getInternalList().size());
        Assertions.assertEquals(0, list.getNamedAmount("White Rice"));
        Assertions.assertEquals(0, list.getNamedAmount("Bread"));
    }
}
