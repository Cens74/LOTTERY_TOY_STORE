package model;

import exceptions.ErrorWhileReadingTheFileException;
import exceptions.WrongDataFormatException;
import exceptions.WrongPathToFileException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLOutput;
import java.util.*;

/**
 * Класс ToyStore моделирует магазин игрушек.
 * Каждая номенклатурная единица имеет свой ID и содержит игрушку, ее количество в магазине и цену.
 * Игрушка, в свою очередь - это ID игрушки, Тип (машинка, кукла, конструктор, настольная игра..) и Название (в кавычках)
 * ID номенклатурным единицам присваивается автоматически, по мере наполнения номенклатурного справочника, начиная с 1.
 *
 */
public class ToyStore {
    private int lastID; // самый старший использованный идентификатор товара
    private final List<NomenclatureItem> toysAssortment; // список товарной номенклатуры
    private PriorityQueue<Integer> freedIDQueue; // очередь свободных ID, мЕньших lastID (товар выведен из номенклатуры)

    public ToyStore() {
        this.lastID = 0;
        this.toysAssortment = new ArrayList<>();
        this.freedIDQueue = new PriorityQueue<>();
    }

    public List<NomenclatureItem> getToysAssortment() {

        return toysAssortment;
    }

    /**
     * Конструктор создает магазин игрушек по данным из файла, в котором перечислены игрушки, их количество и цены.
     * 0. toyID - идентификатор игрушки (целое положительное число)
     * 1. toyType - заключенная в угловые скобки строка из русских букв, пробелов, точек, запятых, дефисов, двойных кавычек, круглых скобок
     * 2. toyName - заключенная в угловые скобки строка из русских букв, пробелов, точек, запятых, дефисов, двойных кавычек, круглых скобок и цифр
     * 3. Quantity - количество номенклатурных единиц данного вида в магазине, положительное целое число
     * 4. Price - цена: double с двумя знаками после точки или запятой, или без дробной части
     *
     * @param fullFileName
     * @throws IOException
     * @throws WrongDataFormatException
     */
    public ToyStore(Path fullFileName) throws IOException, WrongDataFormatException {
        lastID = 0;
        toysAssortment = new ArrayList<>();
        freedIDQueue = new PriorityQueue<>();
        String temp;
        String[] nomenclatureFields;
        File file = new File(fullFileName.toString());
        try (FileReader fr = new FileReader(file); BufferedReader in = new BufferedReader(fr)) {
                NomenclatureItem newItem;
                temp = in.readLine();
                while (temp != null) {
                    System.out.println(temp);
                    if (temp == null) break;
                    nomenclatureFields = temp.split(";");
                    System.out.println("\u001B[36m"+ "nomenclatureFields = "+Arrays.toString(nomenclatureFields)+ "\u001B[0m");
                    lastID++;
                    System.out.printf("lastID = %d\n", lastID);
                    newItem = new NomenclatureItem(lastID, nomenclatureFields);
                    System.out.println(String.format("New Nomenclature item # %d", lastID)+"\u001B[31m" + newItem.toString() + "\u001B[0m");
                    toysAssortment.add(newItem);
                    System.out.println(Arrays.toString(toysAssortment.toArray()));
                    temp = in.readLine();
                    System.out.println("\u001B[32m"+temp+"\u001B[0m");
                }
            } catch (FileNotFoundException e) {
            throw new WrongPathToFileException(String.format("ФАЙЛ %s НЕ НАЙДЕН!!!", fullFileName));
        }

    }
    /**
     * Функция возвращает 0, если игрушка отсутствует в номенклатурном справочнике,
     * и ID номенклатуры, если присутствует.
     * Номенклатура нумеруется с 1.
      * @param toy
     * @return
     */
    public int getNomenclatureID (Toy toy) {
        int result = 0;
        for (NomenclatureItem elem: toysAssortment) {
            if (elem.getToy().equals(toy)) {
                result = elem.getItemID();
                break;
            }
        }
        return result;
    }
    public int getToyQtyInStore (Toy toy) {
        int toyInd = this.getNomenclatureID(toy);
        return toysAssortment.get(toyInd).getQuantity();
    }
    public int getNomenclatureQtyInStore (int nomID) {
        int result = 0;
        for (NomenclatureItem elem: toysAssortment) {
            if (elem.getItemID() == nomID) {
                result = elem.getQuantity();
                break;
            }
        }
        return result;
    }
    public Toy getToyByNomenclatureID (int nomID) {
        Toy result = new Toy();
        for (NomenclatureItem elem: toysAssortment) {
            if (elem.getItemID() == nomID) {
                result = elem.getToy();
                break;
            }
        }
        return result;
    }

    /**
     * Метод добавляет игрушку в номенклатуру в указанном количестве и с указанной ценой.
     * Если игрушка уже есть в номенклатуре, суммирует количество с уже существующим и усредняет цену.
     * @param toy
     * @param qty
     * @param price
     */
    public void addToNomenclature (Toy toy, int qty, double price) {
        int id = getNomenclatureID(toy);
        int ind = id-1;
        NomenclatureItem newItem = null;
        if (id > 0) {                                           // Такая игрушка уже есть в номенклатуре
            newItem = new NomenclatureItem(id, toy);
            int newQty = qty+toysAssortment.get(ind).getQuantity();
            double newPrice = (toysAssortment.get(ind).getPrice()*toysAssortment.get(ind).getQuantity() +
                    price*qty)/newQty;
            newItem.setQuantity(newQty);
            newItem.setPrice(newPrice);
        } else {                                                  // Такой игрушки еще нет в номенклатуре
            if (!freedIDQueue.isEmpty()) {
                id = freedIDQueue.poll();
            } else {
                ind = lastID++;
                id = lastID;
                newItem = new NomenclatureItem(id, toy, qty, price);
            }
        }
        toysAssortment.add(ind, newItem);
    }

    /**
     * В данном методе предполагается, что такая игрушка точно есть в номенклатурном справочнике.
     * Если вдруг нет, то метод просто ничего не делает.
     * @param toy
     */
    public void removeFromNomenclature (Toy toy) {
        int id = getNomenclatureID(toy);
        if (id != 0) {
            int ind = id-1;
            toysAssortment.set(ind, null);
            if (id == lastID) {
                int i = ind-1;
                for (; i >= 0 && toysAssortment.get(i) == null; i--) {}
                if (i == -1) {
                    // из номенклатуры был удален последний товар
                    lastID = 0;
                    toysAssortment.clear();
                    freedIDQueue.clear();
                    }
                } else {
                // данный товар не последний. В узел с данным ID записываем null, а сам ID заносим в очередь
                // освободившихся ID
                freedIDQueue.add(id);
                toysAssortment.set(ind, null);
            }
        }
    }
    /***************************************************/
    public int addToyToAssortment (Toy toy) {
        Integer itemID = 0;
        NomenclatureItem item = new NomenclatureItem(itemID, toy);
        if (!freedIDQueue.isEmpty()) {
            itemID = freedIDQueue.poll();
        } else {
            lastID++;
            itemID = lastID;
        }
        toysAssortment.add(itemID-1, item);
        return itemID;
    }
    /**
     * Метод вывода в консоль всей номенклатуры магазина игрушек
     */
    public void print() {
        System.out.println(String.format("\n\u001B[34m"+"В МАГАЗИНЕ %d ВИДОВ ИГРУШЕК. ОЗНАКОМЬТЕСЬ С АССОРТИМЕНТОМ: ", lastID));
        System.out.println(String.format("%9s", "ID ТОВАРА")+"        "+String.format("%-18s", "ID ИГРУШКИ"+
                "      "+String.format("%-33s", "ТИП ИГРУШКИ")+"       "+String.format("%-50s", "НАЗВАНИЕ ИГРУШКИ")+
                "       "+String.format("%-10s", "КОЛИЧЕСТВО")+"       "+String.format("%-5s", "ЦЕНА")+"\u001B[0m"));
        System.out.println("------------------------------------------------------------------------------------------"+
                "--------------------------------------------------------------------------------------------------");
        for (NomenclatureItem item: toysAssortment) {
            if (item!=null) System.out.println(item.toString());
        }
    }
}
