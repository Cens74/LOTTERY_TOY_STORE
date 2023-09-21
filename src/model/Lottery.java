package model;

import exceptions.DuplicatedLotException;

import java.io.*;
import java.util.*;

public class Lottery {
    public int lotsQty;                            // количество лотов
    private int qtyOfParticipants;                 // количество участников лотереи (минимальное возможное количество призов)
    private int qtyOfPrizes;                       // количество призов
    private final int FACTOR = 3;                   // Во сколько раз призов должно быть больше числа участников
    private Map<Integer,Lot> lots;                 // лоты, участвующие в лотерее: <Номер лот>
    private int[] lotsQuantities;                  // количества лотов, участвующих в розыгрыше
    private PriorityQueue<Toy> prizes;             // очередь призов для выдачи участникам
    public Lottery(LinkedList<Lot> lotsList, int partQty) {
        this.lots = new HashMap<>();
        this.qtyOfParticipants = partQty;
        this.qtyOfPrizes = partQty*FACTOR;
        int id = 0;
        for (Lot elem: lotsList) {
            if (!lotsSetContainsNomenclatureItem (lots, elem.getItem())) {
                lots.put(id, elem);
                id++;
            } else throw new DuplicatedLotException(elem.getItem().getToy().getToyName());
        }
        lotsQty = lots.size();
        lotsQuantities = new int[id]; 
        prizes = new PriorityQueue<Toy>();
    }
//    public Lottery(int partQty) {
//        this.lots = new HashMap<>();
//        this.qtyOfParticipants = partQty;
//        lotsQuantities = new int[0];
//        prizes = new PriorityQueue<Toy>();
//    }
//    public Lottery() {
//        this.lots = new HashMap<>();
//        this.qtyOfParticipants = 0;
//        lotsQuantities = new int[0];
//        prizes = new PriorityQueue<Toy>();
//    }

    public int getQtyOfParticipants() {
        return qtyOfParticipants;
    }

    public void setQtyOfParticipants(int qtyOfParticipants) {
        this.qtyOfParticipants = qtyOfParticipants;
    }

    /**
     * Метод добавления лота для участия в лотерее
     */
//    public boolean addLot(Lot lot) {
//        boolean result = false;
//        if (!lotsSetContainsNomenclatureItem (this.lots, lot.getItem())) {
//            this.lotsQty++;
//            lots.put(lotsQty, lot);
//            result = true;
//        }
//        return result;
//    }

    /**
     * Метод удаления лота из множества лотов, участвующих в лотерее
     */
//    public boolean removeLot(Lot lot) {
//        boolean result = false;
//        if (lotsSetContainsNomenclatureItem (this.lots, lot.getItem())) {
//            this.lotsQty--;
//            lots.remove(NomenclatureItemIDinTheLotsSet(this.lots,lot.getItem()));
//            result = true;
//        }
//        return result;
//    }
    /**
     * Метод проверяет, присутствует ли номенклатурная единица в карте лотов, участвующих в лотерее
     */
    private boolean lotsSetContainsNomenclatureItem (Map<Integer,Lot> lots, NomenclatureItem item) {
        for (Map.Entry<Integer, Lot> entry : lots.entrySet()) {
            if (entry.getValue().getItem().getItemID() == item.getItemID()) return true;
        }
        return false;
    }

    /**
     * Метод возвращает ID записи, содаржащей заданную номенклатурную единицу товар в карте лотов.
     * Если такая запись отсутствует, возвращается 0.
     * @param lots
     * @param item
     * @return
     */
    private int NomenclatureItemIDinTheLotsSet (Map<Integer,Lot> lots, NomenclatureItem item) {
        for (Map.Entry<Integer, Lot> entry : lots.entrySet()) {
            if (entry.getValue().getItem().getItemID() == item.getItemID()) return entry.getKey();
        }
        return 0;
    }

    /**
     * Метод для каждого лота вычисляет число его представителей, требующееся для участия в лотерее,
     * чтобы были соблюдены требования к "весу" лотов-участников
     */
    private void calculateLotsQuantities () {
        int sumOfWeights = 0;
        for (Map.Entry<Integer, Lot> entry : lots.entrySet()) {
            sumOfWeights += entry.getValue().getWeight();
        }
        for (Map.Entry<Integer, Lot> entry : lots.entrySet()) {
            this.lotsQuantities[entry.getKey()] = Math.round(entry.getValue().getWeight()*qtyOfPrizes/sumOfWeights);
        }
        int sum = 0, i = 0;
        while (i < lots.size()-1) {
            sum += lotsQuantities[i];
            i++;
        }
        lotsQuantities[i] = qtyOfPrizes - sum;
    }
    /**
     * Метод создает приоритетную очередь призов, где приоритет определяется случайным образом на основе функции Math.random()
     */
    public void createQueueOfPrizes () {
        int[] tempArray = new int[lotsQuantities.length];
        System.arraycopy(lotsQuantities, 0, tempArray, 0, lotsQuantities.length);
        int indexOfToy;
        for (int i = 0; i < qtyOfPrizes; i++) {
            indexOfToy = (int)(Math.random()*qtyOfPrizes);
            if (tempArray[indexOfToy] != 0) {
                this.prizes.add(lots.get(indexOfToy).getItem().getToy());
                tempArray[indexOfToy]--;
            } else i--;
        }
    }

    /**
     * Метод выдачи очередного приза.
     * @return
     */
    public Toy getPrize (){
        if (this.prizes.isEmpty()) return null;
        else return this.prizes.poll();
    }

    /**
     * Формат файла, описывающего набор игрушек, участвующих в лотерее:
     * В одной строке через пробел перечислены данные игрушек-участников
     * На основании веса этих игрушек и числа участников лотереи вычисляется количество экземпляров каждой игрушки,
     * участвующее в розыгрыше, чтобы частота выпадения того или иного вида игрушек соответствовала весу.
     * @param fileName
     * @throws IOException
     */
//    public Lottery (String fileName) throws IOException {
//        this.lots = new HashSet<>();
//        File file = new File(fileName);
//
//        try (FileReader fr = new FileReader(file)) {
//            BufferedReader reader = new BufferedReader(fr);
//            String line = reader.readLine();
//            while (line != null) {
//                String[] toyFields = line.split(",");
//                Toy nextToy = new Toy(toyFields[]);
//                lines.add(line);
//            }
//            String[] toyParameters = line.split(",");
//        }
//        catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//
//             BufferedReader reader = new BufferedReader(fr);) {
//            String line = reader.readLine();
//            if (line != null) {
//                String[] toyParameters = line.split(",");
//                Toy nextToy = new Toy();
//                lines.add(line);
//            }
//            while (line != null) {
//                // считываем остальные строки в цикле
//                line = reader.readLine();
//                if (line != null) {
//                    lines.add(line);
//                }
//            }
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }

}
