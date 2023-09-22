package model;

import exceptions.WrongDataFormatException;
import utils.Tuner;

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

    /**
     * Конструктор номенклатурной единицы по строке специального формата, содержащей через пробел информацию о единице товара:

     * toyID - идентификатор игрушки (целое положительное число)
     * toyType - заключенная в угловые скобки строка из русских букв, пробелов, точек, запятых, дефисов, двойных кавычек, круглых скобок
     * toyName - заключенная в угловые скобки строка из русских букв, пробелов, точек, запятых, дефисов, двойных кавычек, круглых скобок и цифр
     * Quantity - количество номенклатурных единиц данного вида в магазине, положительное целое число
     * Price - цена: double с двумя знаками после точки или запятой, или без дробной части
     * @param nomenclatureFields
     */
    public NomenclatureItem (String[] nomenclatureFields) throws WrongDataFormatException {
            Tuner tuner = new Tuner();
            int toyID = 0;
            Toy toy;
            if (tuner.isID(nomenclatureFields[0])) toyID = Integer.parseInt(nomenclatureFields[0]);
            if (tuner.isTypeOfToy(nomenclatureFields[1]) && tuner.isNameOfToy(nomenclatureFields[2])) {
                toy = new Toy(toyID, nomenclatureFields[1], nomenclatureFields[2]);
            }

        } catch NumberFormatException {

        }
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
