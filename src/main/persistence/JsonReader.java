// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// Based on supplied Workroom example for CPSC 210; used as reference for persistence classes

package persistence;

import model.Inventory;
import model.Item;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads ItemList from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Inventory read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseInventory(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses ItemList from JSON object and returns it
    private Inventory parseInventory(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Inventory inv = new Inventory(name);
        addItems(inv, jsonObject);
        return inv;
    }

    // MODIFIES: itl
    // EFFECTS: parses Items from JSON object and adds them to ItemList
    private void addItems(Inventory inv, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Items");
        for (Object json : jsonArray) {
            JSONObject nextItem = (JSONObject) json;
            addItem(inv, nextItem);
        }
    }

    // MODIFIES: inv
    // EFFECTS: parses Item from JSON object and adds it to workroom
    private void addItem(Inventory inv, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int amount = jsonObject.getInt("amount");
        double price = jsonObject.getDouble("price");
//        Category category = Category.valueOf(jsonObject.getString("category"));
        Item it = new Item(name, amount, price);
        inv.putIntoList(it);
    }
}

