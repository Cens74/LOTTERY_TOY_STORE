package model;

import exceptions.WrongDataFormatException;
import utils.Tuner;

import java.util.Arrays;

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

    /**
     * Конструктор номенклатурной единицы по строке специального формата, содержащей через пробел информацию о единице товара:

     * 0. toyID - идентификатор игрушки (целое положительное число)
     * 1. toyType - заключенная в угловые скобки строка из русских букв, пробелов, точек, запятых, дефисов, двойных кавычек, круглых скобок
     * 2. toyName - заключенная в угловые скобки строка из русских букв, пробелов, точек, запятых, дефисов, двойных кавычек, круглых скобок и цифр
     * 3. Quantity - количество номенклатурных единиц данного вида в магазине, положительное целое число
     * 4. Price - цена: double с двумя знаками после точки или запятой, или без дробной части
     * @param nomenclatureFields
     */
    public NomenclatureItem (int itemID, String[] nomenclatureFields) throws WrongDataFormatException {
        Tuner tuner = new Tuner();
        this.itemID = itemID;
        int toyID;
        if (nomenclatureFields.length != 5) throw new WrongDataFormatException("НЕКОРРЕКТНОЕ ЧИСЛО ПОЛЕЙ В СТРОКЕ, " +
                "ОПИСЫВАЮЩЕЙ НОМЕНКЛАТУРНУЮ ЕДИНИЦУ. ");
        if (tuner.isID(nomenclatureFields[0])) {
            toyID = Integer.parseInt(nomenclatureFields[0]);
            if (tuner.isTypeOfToy(nomenclatureFields[1]) && tuner.isNameOfToy(nomenclatureFields[2])) {
                System.out.println("Поля содержат тип и название игрушки, все в порядке. ");
                this.toy = new Toy(toyID, nomenclatureFields[1], nomenclatureFields[2]);
                this.quantity = tuner.parseQty(nomenclatureFields[3]);
                this.price = tuner.parsePrice(nomenclatureFields[4]);
            }
        } else throw new WrongDataFormatException("НЕКОРРЕКТНЫЙ ФОРМАТ ДАННЫХ В СТРОКЕ, " +
                "ОПИСЫВАЮЩЕЙ НОМЕНКЛАТУРНУЮ ЕДИНИЦУ. ");
    }
    public int getItemID() {
        return itemID;
    }

    public Toy getToy() {
        return toy;
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
        return String.format("Товар: ID = %3d, %-103s, q-ty = %4d, price = %-5.2f", itemID, toy.toString(), quantity, price);
    }
}
