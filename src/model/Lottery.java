package model;

import exceptions.DuplicatedLotException;
import exceptions.WrongInputDataException;

import java.io.*;
import java.util.*;

public class Lottery {
    private ToyStore store;                         // Магазин, в котором разыгрывается лотерея
//    public int lotsQty;                            // количество лотов
    private int qtyOfParticipants;                 // количество участников лотереи (минимальное возможное количество призов)
    private Integer qtyOfPrizes;                       // количество призов
//    private final int FACTOR = 3;                // Во сколько раз призов должно быть больше числа участников
    private Map<Integer,Lot> lots;                 // лоты, участвующие в лотерее: <ID лота> <Лот>
    private Map<Integer, Integer> lotsQuantities;  // количества лотов, требуемые для розыгрыша: <ID лота> <Кол-во>
    private PriorityQueue<Toy> prizes;             // очередь призов для выдачи участникам
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
        Lot lot = new Lot();
        for (i = 1; i < lots.size(); i++) {
            lot = lots.get(i);
            newQty = (int)Math.round(lot.getWeight()/weightsSum*qtyOfPrizes);
//            if (newQty == 0) ...
            if (newQty > store.getNomenclatureQtyInStore(lot.getItem())) {
                throw new WrongInputDataException(String.format("В магазине недостаточно игрушек: \n"+
                        " %s\n" +"для организации лотереи по Вашим параметрам",
                        store.getToyByNomenclatureID(lot.getItem()).toString()));
            };
            summedQty += newQty;
            lotsQuantities.put(i, newQty);
        }
        lotsQuantities.put(i, qtyOfPrizes-summedQty);
        this.prizes = createQueueOfPrizes();
//        createQueueOfPrizes();
        int num = 1;
        for (Toy elem : prizes) {
            System.out.println(num + elem.toString());
        }
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
            if (entry.getValue().getItem() == item.getItemID()) return true;
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
            if (entry.getValue().getItem() == item.getItemID()) return entry.getKey();
        }
        return 0;
    }

    /**
     * Метод для каждого лота вычисляет число его представителей, требующееся для участия в лотерее,
     * чтобы были соблюдены требования к "весу" лотов-участников
     */
    private void calculateLotsQuantities() {
        int sumOfWeights = 0;
        for (Map.Entry<Integer, Lot> entry : lots.entrySet()) {
            sumOfWeights += entry.getValue().getWeight();
        }
        for (Map.Entry<Integer, Lot> entry : lots.entrySet()) {
            this.lotsQuantities.put(entry.getKey(), Math.round(entry.getValue().getWeight()*qtyOfPrizes/sumOfWeights));
        }
        int sum = 0, i = 0;
        while (i < lots.size()-1) {
            sum += lotsQuantities.get(i);
            i++;
        }
        lotsQuantities.put(i, qtyOfPrizes - sum);
    }
    /**
     * Метод создает приоритетную очередь призов, где приоритет определяется случайным образом на основе функции Math.random()
     */
    private PriorityQueue<Toy> createQueueOfPrizes() {
        PriorityQueue<Toy> result = new PriorityQueue<>();
        int[] tempArray = new int[qtyOfPrizes];
        int currentLotQty;
        for (int i = 0; i < qtyOfPrizes; i++) {
            try {
                Integer lotID = Math.toIntExact(Math.round(Math.random()*(lots.size()-1)+1));
                currentLotQty = lotsQuantities.get(lotID);
                if (currentLotQty > 0) {
                    lotsQuantities.put(lotID, currentLotQty-1);
                    result.add(store.getToysAssortment().get(lotID).getToy());
                }
            } catch (ArithmeticException e) {
                throw new WrongInputDataException("ЗАДАННОЕ ЧИСЛО ПРИЗОВ ВЫХОДИТ ЗА ПРЕДЕЛЫ ДОПУСТИМОГО (БОЛЕЕ 2,147,483,647");
            }
        }
        return result;
    }

    /**
     * Метод выдачи очередного приза.
     * @return
     */
    public Toy getPrize (){
        if (this.prizes.isEmpty()) return null;
        else return this.prizes.poll();
    }
    public void printLots () {
        System.out.println("\u001B[34m"+String.format("%-10s%-12s%-10s", "ID ЛОТА", "ID ТОВАРА", "ВЕС")+"\u001B[0m");
        for (Map.Entry<Integer, Lot> entry : lots.entrySet()) {
            System.out.println(String.format("%-10d%-12d%-10d", entry.getKey(), entry.getValue().getItem(), entry.getValue().getWeight()));
        }
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
