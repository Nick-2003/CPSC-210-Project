package ui.models;

import model.Cart;
import model.ItemList;

// Represents a table model for Cart
public class CartModel extends ItemListModel {

    private final Cart itemList;

    public CartModel(ItemList itemList) {
        super(itemList);
        this.itemList = (Cart) itemList;
    }

    // EFFECTS: Return Cart associated with CartModel
    @Override
    public Cart getItemList() {
        return this.itemList;
    }
}
