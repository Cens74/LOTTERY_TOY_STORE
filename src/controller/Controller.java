package controller;

import exceptions.WrongInputDataException;
import exceptions.WrongPathToFileException;
import model.Lot;
import model.Lottery;
import model.Toy;
import model.ToyStore;
import view.Viewer;
import utils.Tuner;

import java.io.File;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Controller {
    private final Viewer viewer;
    private final Tuner tuner;
    private final String DEFAULT_PATH = ".";
    private final String DEFAULT_TOY_STORE_FILE_NAME = "toyStore.txt";
    private final int QTY_OF_ATTEMPTS = 3;
//    private final Lottery lottery;
//    private final ToyStore store;
//    private final Tuner tuner;

    public Controller(Viewer viewer, Tuner tuner, ToyStore store) {
        this.viewer = viewer;
//       this.lottery = lottery;
//       this.store = store;
        this.tuner = tuner;
    }
    public Controller(Viewer viewer, Tuner tuner) {
        this.tuner = tuner;
        this.viewer = viewer;
    }

    public void run() throws IOException {
        String message = "НАЧИНАЕМ ПОДГОТОВКУ К РОЗЫГРЫШУ В МАГАЗИНЕ ИГРУШЕК...";
        viewer.infoMessage(message);
        message = "НАЧИНАЕМ ПОДГОТОВКУ К РОЗЫГРЫШУ В МАГАЗИНЕ ИГРУШЕК...";
//        Toy toy = new Toy("1 Машинка Вольво-615");
//        System.out.println(toy.toString());
//        viewer.promptMessage(message);
//        Scanner scanner = new Scanner(System.in);
//        int i;
//        String input;
//        for (i = 0; i < QTY_OF_ATTEMPTS; i++) {
//            input = viewer.getUserInput(message);
//            try {
//                numberOfParticipants = Integer.parseInt(input);
//                i = QTY_OF_ATTEMPTS+1;
//            } catch (NumberFormatException e) {
//                if (i < QTY_OF_ATTEMPTS-1) System.out.printf("%d-я попытка: \n", i+2);
//            }
//        }
//        if (i == QTY_OF_ATTEMPTS) throw new WrongInputDataException("ОШИБКА ВВОДА. ПРОГРАММА ЗАВЕРШАЕТ СВОЮ РАБОТУ...");
        message = "ВВЕДИТЕ ПУТЬ К КАТАЛОГУ, В КОТОРОМ НАХОДИТСЯ .TXT-ФАЙЛ С НОМЕНКЛАТУРОЙ МАГАЗИНА\n" +
                "ЕСЛИ НАЖМЕТЕ ENTER, БУДЕТ СЧИТАТЬСЯ, ЧТО ЭТО ТЕКУЩИЙ РАБОЧИЙ КАТАЛОГ ";
        String path = viewer.getUserInput(message);
        if (path == "") {
            try {
                path = new File(".").getCanonicalPath();
            } catch (IOException e) {
                throw new WrongPathToFileException("НЕКОРРЕКТНЫЙ ПУТЬ К ФАЙЛУ С НОМЕНКЛАТУРОЙ МАГАЗИНА");
            }
        }
        System.out.println("Path = " + path);
        message = "ВВЕДИТЕ НАЗВАНИЕ .TXT-ФАЙЛА (БЕЗ КАВЫЧЕК, МОЖНО БЕЗ РАСШИРЕНИЯ), В КОТОРОМ ПЕРЕЧИСЛЕНА НОМЕНКЛАТУРА МАГАЗИНА\n" +
                "ЕСЛИ НАЖМЕТЕ ENTER, БУДЕТ СЧИТАТЬСЯ, ЧТО ЭТО ФАЙЛ toyStore.txt";
        String fileName = viewer.getUserInput(message);
        if (fileName != "") {
            fileName = fileName.strip();
            if (!fileName.endsWith(".txt")) fileName = fileName.concat(".txt");
        } else {
            fileName = DEFAULT_TOY_STORE_FILE_NAME;
        }
        String fullFileName = String.format("%s\\%s", path, fileName);
        System.out.printf("Полное имя файла с номенклатурой магазина игрушек = %s\n", fullFileName);
        ToyStore store = new ToyStore(Path.of(new File(fullFileName).getCanonicalPath()));
        store.print();
        int numberOfParticipants = getPositiveIntegerFromConsole("СКОЛЬКО ДЕТЕЙ БУДЕТ УЧАСТВОВАТЬ В РОЗЫГРЫШЕ? ");
        viewer.infoMessage(String.format("ОК. В НАШЕЙ ЛОТЕРЕЕ БУДЕТ %d УЧАСТНИКА(ОВ).", numberOfParticipants));
        int qtyOfPrizes = getPositiveIntegerFromConsole("СКОЛЬКО ПРИЗОВ БУДЕМ РАЗЫГРЫВАТЬ?");
        viewer.infoMessage(String.format("ОК. В НАШЕЙ ЛОТЕРЕЕ БУДЕТ РАЗЫГРАНО %d ПРИЗА(ОВ).", qtyOfPrizes));
        message = "ВВЕДИТЕ НОМЕРА ТОВАРОВ, КОТОРЫЕ БУДУТ УЧАСТВОВАТЬ В РОЗЫГРЫШЕ, И ДЛЯ КАЖДОГО ИЗ НИХ УКАЖИТЕ ВЕС. \n" +
                "ФОРМАТ ВВОДА: <ID номенклатурной единицы> <Вес>\n" +
                "<Вес> - это целое положительное число, характеризующее частоту выпадения товара в лотерее\n"+
                "ДЛЯ ЗАВЕРШЕНИЯ ВВОДА ВВЕДИТЕ - Q";
        viewer.infoMessage(message);
        String quitString = "q";
        Map<Integer, Lot> lotsMap = getLotsForLotteryFromConsole(quitString);
        Lottery ourLottery = new Lottery (store, lotsMap, numberOfParticipants, qtyOfPrizes);
    }

    private Map<Integer, Lot> getLotsForLotteryFromConsole (String quitString) {
        Map<Integer, Lot> result = new HashMap<>();
        int lotID = 1;
        String userInput;
        String message = String.format("Лот № %-2d: (введите ID товара и вес через пробел) ", lotID);
        userInput = viewer.getUserInput(message);
        while (userInput.compareToIgnoreCase(quitString) != 0) {
            result.put(lotID, tuner.parseLot(userInput));
            lotID++;
            userInput = viewer.getUserInput(String.format("Лот № %-2d: (введите ID товара и вес через пробел) ", lotID));
        }
//        System.out.println("РЕЗУЛЬТИРУЮЩИЙ CПИСОК ЛОТОВ: \n");
        return result;
    }
    private int getPositiveIntegerFromConsole(String promptMessage) {
        viewer.infoMessage(promptMessage);
        return viewer.getPositiveInteger(QTY_OF_ATTEMPTS);
    }

//        String fullFileName = viewer.getUserInput(message);
//        String fileNAme =
//        try {
//            toyStoreFileName = (File)scanner.nextLine());
//        } catch (IOException e) {
//
//        }
}

