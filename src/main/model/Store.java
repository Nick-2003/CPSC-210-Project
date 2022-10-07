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

    public void itemToCart(String itemName, int itemQuantity) {
        if (this.inventory.availableAmount(itemName) >= itemQuantity) {
            double itemPrice = this.inventory.namedPrice(itemName);
            Item item = new Item(itemName, itemQuantity, itemPrice);
            this.inventory.takeFromList(item);
            this.cart.putIntoList(item);
        }
    }

    public void itemFromCart(String itemName, int itemQuantity) {
        if (this.inventory.availableAmount(itemName) >= itemQuantity) {
            double itemPrice = this.inventory.namedPrice(itemName);
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
