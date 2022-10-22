//package persistence;
//
//import model.Item;
//import model.Inventory;
//import org.junit.jupiter.api.Test;
//
//import java.io.IOException;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class JsonWriterTest extends JsonTest {
//    @Test
//    void testWriterInvalidFile() {
//        try {
//            Inventory inv = new Inventory("My work room");
//            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
//            writer.open();
//            fail("IOException was expected");
//        } catch (IOException e) {
//            // pass
//        }
//    }
//
//    @Test
//    void testWriterEmptyWorkroom() {
//        try {
//            WorkRoom wr = new WorkRoom("My work room");
//            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
//            writer.open();
//            writer.write(wr);
//            writer.close();
//
//            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkroom.json");
//            wr = reader.read();
//            assertEquals("My work room", wr.getName());
//            assertEquals(0, wr.numThingies());
//        } catch (IOException e) {
//            fail("Exception should not have been thrown");
//        }
//    }
//
//    @Test
//    void testWriterGeneralWorkroom() {
//        try {
//            WorkRoom wr = new WorkRoom("My work room");
//            wr.addThingy(new Thingy("saw", Category.METALWORK));
//            wr.addThingy(new Thingy("needle", Category.STITCHING));
//            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWorkroom.json");
//            writer.open();
//            writer.write(wr);
//            writer.close();
//
//            JsonReader reader = new JsonReader("./data/testWriterGeneralWorkroom.json");
//            wr = reader.read();
//            assertEquals("My work room", wr.getName());
//            List<Thingy> thingies = wr.getThingies();
//            assertEquals(2, thingies.size());
//            checkThingy("saw", Category.METALWORK, thingies.get(0));
//            checkThingy("needle", Category.STITCHING, thingies.get(1));
//
//        } catch (IOException e) {
//            fail("Exception should not have been thrown");
//        }
//    }
//}