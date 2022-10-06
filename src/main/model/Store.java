package model;

public class Store {
    private String name;
    private Inventory inventory;

    private Cart cart;


    public Store(String name, Inventory inventory, Cart cart) {
        this.name = name;
//        this.inventory = inventory;
//        this.purchases = purchases;
        this.inventory = new Inventory();
        this.cart = new Cart();
    }

    public void itemToCart(Item item) {
//        if (item )
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
