package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ItemTest {
    Item rice;
    Item bread;

    @BeforeEach
    public void setUp() {
        rice = new Item("White Rice", 10, 8.00);
        bread = new Item("Bread", 20, 3.00);
    }

    @Test
    public void testChangeQuantity() {
        assertEquals(10, rice.getQuantity());
        rice.changeQuantity(-5);
        assertEquals(5, rice.getQuantity());
    }

    @Test
    public void testChangeQuantityMulti() {
        assertEquals(10, rice.getQuantity());
        rice.changeQuantity(-5);
        assertEquals(5, rice.getQuantity());
        rice.changeQuantity(10);
        assertEquals(15, rice.getQuantity());
    }
}