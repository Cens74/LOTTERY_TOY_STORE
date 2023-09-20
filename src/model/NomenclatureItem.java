package model;

public class NomenclatureItem {
    private int itemID;
    private Toy toy;
    private int quantity;
    private double price;

    public NomenclatureItem(int itemID, Toy toy, int quantity, double price) {
        this.itemID = itemID;
        this.toy = toy;
        this.quantity = quantity;
        this.price = price;
    }
    public NomenclatureItem(int itemID, Toy toy) {
        this.itemID = itemID;
        this.toy = toy;
        this.quantity = 0;
        this.price = 0;
    }
    public NomenclatureItem(Toy toy) {
        this.itemID = 0;
        this.toy = toy;
        this.quantity = 0;
        this.price = 0;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public Toy getToy() {
        return toy;
    }

    public void setToy(Toy toy) {
        this.toy = toy;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("Товарная единица: ID = %-10d, %-100s, q-ty = %-10d, price = %-5.2f", itemID, toy, quantity, price);
    }
}
