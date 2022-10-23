package model;

import exceptions.NegativeValueException;
import exceptions.NotEnoughItemsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {
    Item rice10;
    Item bread20;

    @BeforeEach
    public void setUp() {
        try {
            rice10 = new Item("White Rice", 10, 8);
            bread20 = new Item("Bread", 20, 3.5);
        } catch (NegativeValueException e) {
            fail("Items should work");
        }
    }

    @Test
    public void testChangeQuantity() {
        assertEquals(10, rice10.getAmount());
        try {
            rice10.changeQuantity(-5);
        } catch (NotEnoughItemsException e) {
            fail("NotEnoughItemsException should not be thrown");
        }
        assertEquals(5, rice10.getAmount());
    }

    @Test
    public void testChangeQuantityMulti() {
        assertEquals(10, rice10.getAmount());
        try {
            rice10.changeQuantity(-5);
            assertEquals(5, rice10.getAmount());
            rice10.changeQuantity(10);
        } catch (NotEnoughItemsException e) {
            fail("NotEnoughItemsException should not be thrown");
        }
        assertEquals(15, rice10.getAmount());
    }

    @Test
    public void testChangeQuantityZero() {
        assertEquals(10, rice10.getAmount());
        try {
            rice10.changeQuantity(0);
        } catch (NotEnoughItemsException e) {
            fail("NotEnoughItemsException should not be thrown");
        }
        assertEquals(10, rice10.getAmount());
    }

    @Test
    public void testChangeQuantityNeg() {
        assertEquals(10, rice10.getAmount());
        try {
            rice10.changeQuantity(-15);
            fail("NotEnoughItemsException should be thrown");
        } catch (NotEnoughItemsException e) {
            // All good
        }
        assertEquals(10, rice10.getAmount());
    }

    @Test
    public void testSetPrice() {
        assertEquals(8, rice10.getPrice());
        rice10.setPrice(9);
        assertEquals(9, rice10.getPrice());
    }

    @Test
    public void testSetPriceMulti() {
        assertEquals(8, rice10.getPrice());
        rice10.setPrice(9);
        assertEquals(9, rice10.getPrice());
        rice10.setPrice(7);
        assertEquals(7, rice10.getPrice());
    }

    @Test
    public void testSetPriceOneDecimal() {
        assertEquals(8, rice10.getPrice());
        rice10.setPrice(8.1);
        assertEquals(8.1, rice10.getPrice());
    }

    @Test
    public void testSetPriceTwoDecimal() {
        assertEquals(8, rice10.getPrice());
        rice10.setPrice(8.12);
        assertEquals(8.12, rice10.getPrice());
    }

    @Test
    public void testSetPriceTwoMoreDecimalDown() {
        assertEquals(8, rice10.getPrice());
        rice10.setPrice(8.124);
        assertEquals(8.12, rice10.getPrice());
    }

    @Test
    public void testSetPriceTwoMoreDecimalUp() {
        assertEquals(8, rice10.getPrice());
        rice10.setPrice(8.125);
        assertEquals(8.13, rice10.getPrice());
    }

    @Test
    public void testSetName()  {
        assertEquals("White Rice", rice10.getName());
        rice10.setName("Rice");
        assertEquals("Rice", rice10.getName());
    }

    @Test
    public void testSetNameMulti()  {
        assertEquals("White Rice", rice10.getName());
        rice10.setName("Rice");
        assertEquals("Rice", rice10.getName());
        rice10.setName("Brown Rice");
        assertEquals("Brown Rice", rice10.getName());
    }

    @Test
    public void testSetNameBlank()  {
        assertEquals("White Rice", rice10.getName());
        rice10.setName("");
        assertEquals("", rice10.getName());
    }
}