// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// Based on supplied Workroom example for CPSC 210; used as reference for persistence classes

package persistence;

import model.ItemList;
import org.json.JSONObject;

import java.io.*;

// Represents a writer that writes JSON representation of workroom to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: Constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: Opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: Writes JSON representation of ItemList to file
    public void write(ItemList itl) {
        JSONObject json = itl.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: Closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: Writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}

