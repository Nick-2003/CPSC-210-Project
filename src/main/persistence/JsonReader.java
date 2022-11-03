// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// Based on supplied Workroom example for CPSC 210; used as reference for persistence classes

package persistence;

import exceptions.NegativeValueException;
import exceptions.NotEnoughItemsException;
import model.Inventory;
import model.Item;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.ItemList;
import org.json.*;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private final String source;

    // EFFECTS: Constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: Reads ItemList from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ItemList read() throws IOException, NegativeValueException, NotEnoughItemsException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseItemList(jsonObject);
    }

    // EFFECTS: Reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: Parses ItemList from JSON object and returns it
    private ItemList parseItemList(JSONObject jsonObject) throws NegativeValueException, NotEnoughItemsException {
        String name = jsonObject.getString("name");
        ItemList inv = new Inventory(name);
        addItems(inv, jsonObject);
        return inv;
    }

    // MODIFIES: inv
    // EFFECTS: Parses Items from JSON object and adds them to ItemList
    private void addItems(ItemList inv, JSONObject jsonObject) throws NegativeValueException, NotEnoughItemsException {
        JSONArray jsonArray = jsonObject.getJSONArray("items");
        for (Object json : jsonArray) {
            JSONObject nextItem = (JSONObject) json;
            addItem(inv, nextItem);
        }
    }

    // MODIFIES: inv
    // EFFECTS: Parses Item from JSON object and adds it to ItemList
    private void addItem(ItemList inv, JSONObject jsonObject) throws NegativeValueException, NotEnoughItemsException {
        String name = jsonObject.getString("name");
        int amount = jsonObject.getInt("amount");
        double price = jsonObject.getDouble("price");
        Item it = new Item(name, amount, price);
        inv.putIntoList(it);
    }
}

