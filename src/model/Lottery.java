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
        /******************************************************************/
        System.out.println(String.format("СУММА ВСЕХ ВЕСОВ РАВНА %d", weightsSum));
        System.out.println(String.format("ОБЩЕЕ ЧИСЛО ПРИЗОВ РАВНО %d", qtyOfPrizes));
        /*****************************************************************/
        int i = 1;
        int newQty;
        int summedQty = 0;
        Lot lot;
        for (i = 1; i < lots.size(); i++) {
            lot = new Lot();
            lot = lots.get(i);
            /*******************************************************/
            System.out.print((store.getToyByNomenclatureID(lot.getNomenclatureID())).hashCode());
            System.out.print(String.format("; ВЕС = %d", lot.getWeight()));
            System.out.print(String.format("; ВЕС/СУММУ ВЕСОВ = %-5.2f", ((double)lot.getWeight())/weightsSum));
            /*****************************************************/
            newQty = (int)Math.round(((double)lot.getWeight())/weightsSum*qtyOfPrizes);
            System.out.printf(" Quantity = %-5d\n", newQty);
//            if (newQty == 0) ...
            if (newQty > store.getNomenclatureQtyInStore(lot.getNomenclatureID())) {
                throw new WrongInputDataException(String.format("В магазине недостаточно игрушек: \n"+ "ID# %d, " +
                        " %s\n" +"для организации лотереи по Вашим параметрам\n"+"Требуется минимум %d.\n",
                        lot.getNomenclatureID(),
                        store.getToyByNomenclatureID(lot.getNomenclatureID()).toString(), newQty));
            };
            summedQty += newQty;
            lotsQuantities.put(i, newQty);
            System.out.println(String.format("Лот %d: %s", i, lotsQuantities.get(i)));
        }
        lot = new Lot();
        lot = lots.get(i);
        /*******************************************************/
        System.out.println(store.getToyByNomenclatureID(lot.getNomenclatureID()).hashCode());
        System.out.print(String.format("; ВЕС = %d", lot.getWeight()));
        System.out.print(String.format("; ВЕС/СУММУ ВЕСОВ = %-5.2f", ((double)lot.getWeight())/weightsSum));
        /*****************************************************/
        lotsQuantities.put(i, qtyOfPrizes-summedQty);
        System.out.println(String.format("Лот %d: %s", i, lotsQuantities.get(i)));
        System.out.println(lotsQuantities);
        this.prizes = createQueueOfPrizes();
//        createQueueOfPrizes();
//        int num = 1;
//        for (i = 0; i < qtyOfPrizes; i++) {
//            System.out.println(num + " приз:  " + prizes.poll().toString());
//            num++;
//        }
//        System.out.println(String.format("Queue size = %d", prizes.size()));
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
            if (entry.getValue().getNomenclatureID() == item.getItemID()) return true;
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
            if (entry.getValue().getNomenclatureID() == item.getItemID()) return entry.getKey();
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
    public PriorityQueue<Prize> createQueueOfPrizes() {
//        Comparator<Toy> toyComparator = new Comparator<Toy>() {
//            @Override
//            public int compare(Toy o1, Toy o2) {
//                return 0;
//            }
//        }
        Comparator<Prize> prizesComparator = new Comparator<Prize>() {
            @Override
            public int compare(Prize o1, Prize o2) {
                return o1.priority-o2.priority;
            }
        };
        PriorityQueue<Prize> result = new PriorityQueue<>(qtyOfPrizes, prizesComparator);
        System.out.println(String.format("qtyOfPrizes = %d", qtyOfPrizes));
        int[] tempArray = new int[qtyOfPrizes];
        int[] markArray = new int[qtyOfPrizes];

        System.out.println("**************************************************************************\n"+
                "В массив temArray в случайном порядкезаносим NomenclatureID товаров, которые в результате случайного отбора попали в список призов\n"+
                "**************************************************************************");

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

//            try {
//                Integer lotID = Math.toIntExact(Math.round(Math.random()*(lots.size()-1)+1));
//                System.out.printf("Выбран лот номер %d... ", lotID);
//                currentLotQty = lotsQuantities.get(lotID);
//                System.out.printf("Этих игрушек осталось %d штук.\n", currentLotQty);
//                if (currentLotQty > 0) {
//                    tempArray[i] = lots.get(lotID).getNomenclatureID();
//                    /* в массив temArray заносим NomenclatureID товаров, которые в результате случайного отбора попали в список призов */
//                    /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
////                    lotsQuantities.put(lotID, currentLotQty-1);
////                    result.add(store.getToysAssortment().get(lotID).getToy());
////                    System.out.printf("... добавили лот № %d в очередь на выдачу!\n", lotID);
//                }
//            } catch (ArithmeticException e) {
//                throw new WrongInputDataException("ЗАДАННОЕ ЧИСЛО ПРИЗОВ ВЫХОДИТ ЗА ПРЕДЕЛЫ ДОПУСТИМОГО (БОЛЕЕ 2,147,483,647");
//            }
            System.out.println(Arrays.toString(tempArray));
            System.out.println(Arrays.toString(markArray));

            System.out.println("**************************************************************************\n"+
                "Помещаем соответствующие игрушки в Priority Queue\n"+
                "*****************************************************************************************");
            for (int i = 0; i < qtyOfPrizes; i++) {
                Prize nextPrize = new Prize(i, store.getToyByNomenclatureID(tempArray[i]), qtyOfPrizes);
//                System.out.println(nextPrize);
                result.add(nextPrize);
            }
//        }
        System.out.println(String.format("В ЛОТЕРЕЕ БУДЕТ РАЗЫГРАНО %d ИГРУШЕК.", result.size()));
        return result;
    }
//    private Comparator<Toy> toyComparator = new Comparator<Toy>() {
//        @Override
//        public int compare(Toy o1, Toy o2) {
//            return compare(o1.hashCode();o2.hashCode());
//        }
//    }

    /**
     * Метод выдачи очередного приза.
     * @return
     */
    public Prize getPrize (){
        if (this.prizes.isEmpty()) return null;
        else return this.prizes.poll();
    }
    public void printLots () {
        System.out.println("\u001B[34m"+String.format("%-10s%-12s%-10s", "ID ЛОТА", "ID ТОВАРА", "ВЕС")+"\u001B[0m");
        for (Map.Entry<Integer, Lot> entry : lots.entrySet()) {
            System.out.println(String.format("%-10d%-12d%-10d", entry.getKey(), entry.getValue().getNomenclatureID(), entry.getValue().getWeight()));
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
