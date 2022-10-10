package model;

import java.math.BigDecimal;

public class Store {
    private String name;
    private Inventory inventory;
    private Cart cart;

    public Store(String name) {
        this.name = name;
        this.inventory = new Inventory();
        this.cart = new Cart();
    }

    public void itemToCart(String itemName, int itemQuantity) {
        if (this.inventory.getNamedAmount(itemName) >= itemQuantity) {
            double itemPrice = this.inventory.getNamedPrice(itemName);
//            BigDecimal itemPrice = this.inventory.getNamedPrice(itemName);
            Item item = new Item(itemName, itemQuantity, itemPrice);
            this.inventory.takeFromList(item);
            this.cart.putIntoList(item);
        }
    }

    public void itemFromCart(String itemName, int itemQuantity) {
        if (this.inventory.getNamedAmount(itemName) >= itemQuantity) {
            double itemPrice = this.inventory.getNamedPrice(itemName);
//            BigDecimal itemPrice = this.inventory.getNamedPrice(itemName);
            Item item = new Item(itemName, itemQuantity, itemPrice);
            this.cart.takeFromList(item);
            this.inventory.putIntoList(item);
        }
    }



    public String getName() {
        return name;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Cart getCart() {
        return cart;
    }

    public void setName(String name) {
        this.name = name;
    }
}
