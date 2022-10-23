package persistence;

import exceptions.NegativeValueException;
import exceptions.NotEnoughItemsException;
import model.Inventory;
import model.Item;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Inventory inv = reader.read();
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
            Inventory inv = reader.read();
            assertEquals("My inventory", inv.getName());
            assertEquals(0, inv.getInternalList().size());
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
            Inventory inv = reader.read();
            assertEquals("My inventory", inv.getName());
            List<Item> items = inv.getItems();
            assertEquals(2, inv.getInternalList().size());
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