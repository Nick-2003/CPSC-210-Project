package persistence;

import model.Item;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkItem(String name, int amount, double price, Item it) {
        assertEquals(name, it.getName());
        assertEquals(amount, it.getAmount());
        assertEquals(price, it.getPrice());
    }
}
