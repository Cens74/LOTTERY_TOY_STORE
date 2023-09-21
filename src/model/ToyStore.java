package model;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class ToyStore {
    private int lastID;
    private final List<NomenclatureItem> toysAssortment; // список товарной номенклатуры
    private Queue<Integer> freedIDQueue; // список освободившихся ID (товар выведен из номенклатуры)

    public ToyStore() {
        this.lastID = 0;
        this.toysAssortment = new ArrayList<>();
        this.freedIDQueue = new PriorityQueue<>();
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
            newItem = new NomenclatureItem(toy);
            newItem.setItemID(id);
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
    public int addToyToAssortment (Toy toy) {
        NomenclatureItem item = new NomenclatureItem(toy);
        Integer itemID = 0;
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
        System.out.println("\u001B[33m"+"АССОРТИМЕНТ МАГАЗИНА ИГРУШЕК: "+"\u001B[0m");
        for (NomenclatureItem item: toysAssortment) {
            if (item!=null) System.out.println(item.toString());
        }
    }
}
