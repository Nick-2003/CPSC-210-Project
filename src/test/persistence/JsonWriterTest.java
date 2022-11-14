package persistence;

import exceptions.NegativeValueException;
import exceptions.NotEnoughItemsException;
import model.Item;
import model.ItemList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {
    @Test
    void testWriterInvalidFile() {
        try {
            ItemList inv = new ItemList("My work room");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            ItemList inv = new ItemList("My inventory");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyInventory.json");
            writer.open();
            writer.write(inv);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyInventory.json");
            inv = reader.readInv();
            assertEquals("My inventory", inv.getName());
            assertEquals(0, inv.getInternalList().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        } catch (NegativeValueException e) {
            fail("NegativeValueException should not be thrown");
        } catch (NotEnoughItemsException e) {
            fail("NotEnoughItemsException should not be thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            ItemList inv = new ItemList("My inventory");
            inv.putIntoList(new Item("White Rice", 10, 8.00));
            inv.putIntoList(new Item("Bread", 20, 3.50));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralInventory.json");
            writer.open();
            writer.write(inv);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralInventory.json");
            inv = reader.readInv();
            assertEquals("My inventory", inv.getName());
            List<Item> items = inv.getItems();
            assertEquals(2, inv.getInternalList().size());
            checkItem("White Rice", 10, 8.00, items.get(0));
            checkItem("Bread", 20, 3.50, items.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        } catch (NegativeValueException e) {
            fail("NegativeValueException should not be thrown");
        } catch (NotEnoughItemsException e) {
            fail("NotEnoughItemsException should not be thrown");
        }
    }
}