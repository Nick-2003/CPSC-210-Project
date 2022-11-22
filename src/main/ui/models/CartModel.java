package ui.models;

import model.Cart;
import model.ItemList;

public class CartModel extends ItemListModel {

    private Cart itemList;

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
