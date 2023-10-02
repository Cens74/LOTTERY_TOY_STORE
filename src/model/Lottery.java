package model;

import exceptions.DuplicatedLotException;
import exceptions.LotteryCreationException;
import exceptions.WrongInputDataException;

import java.io.*;
import java.util.*;

public class Lottery {
    private ToyStore store;                         // Магазин, в котором разыгрывается лотерея
    private int qtyOfParticipants;                 // количество участников лотереи (минимальное возможное количество призов)
    private Integer qtyOfPrizes;                       // количество призов
    private Map<Integer,Lot> lots;                 // лоты, участвующие в лотерее: <ID лота> <Лот>
    private Map<Integer, Integer> lotsQuantities;  // количества лотов, требуемые для розыгрыша: <ID лота> <Кол-во>
    private PriorityQueue<Prize> prizes;             // очередь призов для выдачи участникам

    public PriorityQueue<Prize> getPrizes() {
        return prizes;
    }

    public Lottery(ToyStore store, Map<Integer,Lot> lots, int participantsQty, int prizesQty) {
        this.store = store;
        this.lots = lots;
        this.qtyOfParticipants = participantsQty;
        this.qtyOfPrizes = prizesQty;
        
        this.lotsQuantities = new HashMap<>();
        printLots();
        Integer weightsSum = 0;
        for (Map.Entry<Integer, Lot> entry : lots.entrySet()) {
            weightsSum += entry.getValue().getWeight();
            lotsQuantities.put(entry.getKey(), 0);
        }
        int i = 1;
        int newQty;
        int summedQty = 0;
        Lot lot;
        for (i = 1; i < lots.size(); i++) {
            lot = new Lot();
            lot = lots.get(i);
            newQty = (int)Math.round(((double)lot.getWeight())/weightsSum*qtyOfPrizes);
            if (newQty > store.getNomenclatureQtyInStore(lot.getNomenclatureID())) {
                throw new LotteryCreationException(String.format("В магазине недостаточно игрушек: \n"+ "ID# %d, " +
                        " %s\n" +"для организации лотереи по Вашим параметрам\n"+"Требуется минимум %d.\n",
                        lot.getNomenclatureID(),
                        store.getToyByNomenclatureID(lot.getNomenclatureID()).toString(), newQty));
            };
            summedQty += newQty;
            lotsQuantities.put(i, newQty);
        }
        lot = new Lot();
        lot = lots.get(i);
        lotsQuantities.put(i, qtyOfPrizes-summedQty);
        this.prizes = createQueueOfPrizes();
    }

    /**
     * Метод создает приоритезированную очередь призов, где приоритет определяется случайным образом на основе функции Math.random()
     */
    public PriorityQueue<Prize> createQueueOfPrizes() {
        Comparator<Prize> prizesComparator = new Comparator<Prize>() {
            @Override
            public int compare(Prize o1, Prize o2) {
                return o1.priority-o2.priority;
            }
        };
        PriorityQueue<Prize> result = new PriorityQueue<>(qtyOfPrizes, prizesComparator);
        int[] tempArray = new int[qtyOfPrizes];
        int[] markArray = new int[qtyOfPrizes];

            for (Map.Entry<Integer, Integer> entry : lotsQuantities.entrySet()) {
                for (int i = 1; i <= entry.getValue(); i++) {
                    int j = (int)Math.round(Math.random()*qtyOfPrizes-0.5);
                    while (markArray[j] == 1) {
                        j = (int)Math.round(Math.random()*qtyOfPrizes-0.5);
                    }
                    tempArray[j] = lots.get(entry.getKey()).getNomenclatureID();
                    markArray[j] = 1;
                }
            }
            for (int i = 0; i < qtyOfPrizes; i++) {
                Prize nextPrize = new Prize(i, store.getToyByNomenclatureID(tempArray[i]), qtyOfPrizes);
                result.add(nextPrize);
            }
        System.out.println(String.format("В ЛОТЕРЕЕ БУДЕТ РАЗЫГРАНО %d ИГРУШЕК.", result.size()));
        return result;
    }

    /**
     * Метод выдачи очередного приза.
     * @return
     */
    public Prize getNextPrize (){
        if (this.prizes.isEmpty()) return null;
        else return this.prizes.poll();
    }
    public void printLots () {
        System.out.println("\u001B[34m"+String.format("%-10s%-12s%-10s", "ID ЛОТА", "ID ТОВАРА", "ВЕС")+"\u001B[0m");
        for (Map.Entry<Integer, Lot> entry : lots.entrySet()) {
            System.out.println(String.format("%-10d%-12d%-10d", entry.getKey(), entry.getValue().getNomenclatureID(), entry.getValue().getWeight()));
        }
    }
}
