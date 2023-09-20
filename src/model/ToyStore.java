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
      * @param toy
     * @return
     */
    public int toyID (Toy toy) {
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
        int id = toyID(toy);
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
        int id = toyID(toy);
        if (id != 0) {
            int ind = id-1;
            freedIDQueue.add(id);
            toysAssortment.set(ind, null);
            if (id == lastID) {
                for (int i = ind-1; i >= 0 && toysAssortment.get(i) == null; i--) {
                    if (i == -1) {
                        lastID = 0;
                    }
                }
            }
        }
        int ind = id-1;

        if (id > 0) {                                           // Такая игрушка уже есть в номенклатуре
            newItem = new NomenclatureItem(toy);
            newItem.setItemID(id);
            int newQty = qty+toysAssortment.get(ind).getQuantity();
            double newPrice = (toysAssortment.get(ind).getPrice()*toysAssortment.get(ind).getQuantity() +
                    price*qty)/newQty;
            newItem.setQuantity(newQty);
            newItem.setPrice(newPrice);
        } else {                                                  // Такой игрушки еще нет в номенклатуре
            if (!freedIDList.isEmpty()) {
                id = freedIDList.poll();
            } else {
                ind = lastID++;
                id = lastID;
                newItem = new NomenclatureItem(id, toy, qty, price);
            }
        }
        toysAssortment.add(ind, newItem);
    }
    public int addToyToAssortment (Toy toy) {
        NomenclatureItem item = new NomenclatureItem(toy);
        Integer itemID = 0;
        if (!freedIDList.isEmpty()) {
            itemID = freedIDList.poll();
        } else {
            lastID++;
            itemID = lastID;
        }
        toysAssortment.add(itemID-1, item);
        return itemID;
    }
}
