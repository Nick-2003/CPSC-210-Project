//package persistence;
//
//import model.Item;
//import model.ItemList;
//import org.junit.jupiter.api.Test;
//
//import java.io.IOException;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class JsonReaderTest extends JsonTest {
//
//    @Test
//    void testReaderNonExistentFile() {
//        JsonReader reader = new JsonReader("./data/noSuchFile.json");
//        try {
//            Inventory inv = reader.read();
//            fail("IOException expected");
//        } catch (IOException e) {
//            // pass
//        }
//    }
//
//    @Test
//    void testReaderEmptyWorkRoom() {
//        JsonReader reader = new JsonReader("./data/testReaderEmptyInventory.json");
//        try {
//            Inventory inv = reader.read();
//            assertEquals("My inventory", inv.getName());
//            assertEquals(0, inv.getInternalList().size());
//        } catch (IOException e) {
//            fail("Couldn't read from file");
//        }
//    }
//
//    @Test
//    void testReaderGeneralWorkRoom() {
//        JsonReader reader = new JsonReader("./data/testReaderGeneralInventory.json");
//        try {
//            Inventory inv = reader.read();
//            assertEquals("My work room", inv.getName());
//            assertEquals(2, inv.getInternalList().size());
//            checkThingy("needle", Category.STITCHING, thingies.get(0));
//            checkThingy("saw", Category.WOODWORK, thingies.get(1));
//        } catch (IOException e) {
//            fail("Couldn't read from file");
//        }
//    }
//}