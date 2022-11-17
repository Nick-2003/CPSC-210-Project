package ui;

import model.Cart;
import model.ItemList;

public class CartModel extends ItemListModel {

    private Cart itemList;

    public CartModel(ItemList itemList) {
        super(itemList);
        this.itemList = (Cart) itemList;
    }

    @Override
    public Cart getItemList() {
        return this.itemList;
    }
}
