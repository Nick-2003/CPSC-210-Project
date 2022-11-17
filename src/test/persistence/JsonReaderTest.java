package persistence;

import exceptions.NegativeValueException;
import exceptions.NotEnoughItemsException;
import model.Item;
import model.ItemList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ItemList inv1 = reader.readInv();

            fail("IOException expected");
        } catch (IOException e) {
            // pass
        } catch (NegativeValueException e) {
            fail("NegativeValueException should not be thrown");
        } catch (NotEnoughItemsException e) {
            fail("NotEnoughItemsException should not be thrown");
        }
        try {
            ItemList inv2 = reader.readCart();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        } catch (NegativeValueException e) {
            fail("NegativeValueException should not be thrown");
        } catch (NotEnoughItemsException e) {
            fail("NotEnoughItemsException should not be thrown");
        }

    }

    @Test
    void testReaderEmptyInventory() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyInventory.json");
        try {
            ItemList inv1 = reader.readInv();
            assertEquals("My inventory", inv1.getName());
            assertEquals(0, inv1.getInternalList().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        } catch (NegativeValueException e) {
            fail("NegativeValueException should not be thrown");
        } catch (NotEnoughItemsException e) {
            fail("NotEnoughItemsException should not be thrown");
        }
        try {
            ItemList inv2 = reader.readCart();
            assertEquals("My inventory", inv2.getName());
            assertEquals(0, inv2.getInternalList().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        } catch (NegativeValueException e) {
            fail("NegativeValueException should not be thrown");
        } catch (NotEnoughItemsException e) {
            fail("NotEnoughItemsException should not be thrown");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralInventory.json");
        try {
            ItemList inv1 = reader.readInv();
            assertEquals("My inventory", inv1.getName());
            List<Item> items = inv1.getItems();
            assertEquals(2, inv1.getInternalList().size());
            checkItem("White Rice", 10, 8.00, items.get(0));
            checkItem("Bread", 20, 3.50, items.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        } catch (NegativeValueException e) {
            fail("NegativeValueException should not be thrown");
        } catch (NotEnoughItemsException e) {
            fail("NotEnoughItemsException should not be thrown");
        }
        try {
            ItemList inv2 = reader.readCart();
            assertEquals("My inventory", inv2.getName());
            List<Item> items = inv2.getItems();
            assertEquals(2, inv2.getInternalList().size());
            checkItem("White Rice", 10, 8.00, items.get(0));
            checkItem("Bread", 20, 3.50, items.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        } catch (NegativeValueException e) {
            fail("NegativeValueException should not be thrown");
        } catch (NotEnoughItemsException e) {
            fail("NotEnoughItemsException should not be thrown");
        }
    }
}