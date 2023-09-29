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
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Controller {
    private final Viewer viewer;
    private final Tuner tuner;
    private final String DEFAULT_PATH = ".";
    private final String DEFAULT_TOY_STORE_FILE_NAME = "toyStore.txt";
    private final String LOTTERY_RESULT_FILE_NAME = "lotteryResult.txt";
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
        String pathRequest = "ВВЕДИТЕ ПУТЬ К КАТАЛОГУ, В КОТОРОМ НАХОДИТСЯ .TXT-ФАЙЛ С НОМЕНКЛАТУРОЙ МАГАЗИНА\n" +
                "ЕСЛИ НАЖМЕТЕ ENTER, БУДЕТ СЧИТАТЬСЯ, ЧТО ЭТО ТЕКУЩИЙ РАБОЧИЙ КАТАЛОГ ";
        String fileNameRequest = "ВВЕДИТЕ НАЗВАНИЕ .TXT-ФАЙЛА (БЕЗ КАВЫЧЕК, МОЖНО БЕЗ РАСШИРЕНИЯ), В КОТОРОМ ПЕРЕЧИСЛЕНА НОМЕНКЛАТУРА МАГАЗИНА\n" +
                "ЕСЛИ НАЖМЕТЕ ENTER, БУДЕТ СЧИТАТЬСЯ, ЧТО ЭТО ФАЙЛ toyStore.txt";
        String storeFileName = getFileNameFromConsole (pathRequest, fileNameRequest, DEFAULT_PATH, DEFAULT_TOY_STORE_FILE_NAME);
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

        ToyStore store = new ToyStore(Path.of(new File(storeFileName).getCanonicalPath()));
//        System.out.println("\n\u001B[34m"+"ОЗНАКОМЬТЕСЬ С АССОРТИМЕНТОМ МАГАЗИНА ИГРУШЕК: "+"\u001B[0m");
        store.print();
        int numberOfParticipants = getPositiveIntegerFromConsole("\nСКОЛЬКО ДЕТЕЙ БУДЕТ УЧАСТВОВАТЬ В РОЗЫГРЫШЕ? ");
        viewer.infoMessage(String.format("ОК. В НАШЕЙ ЛОТЕРЕЕ БУДЕТ %d УЧАСТНИКА(ОВ).", numberOfParticipants));
        int qtyOfPrizes = getPositiveIntegerFromConsole("\nСКОЛЬКО ПРИЗОВ БУДЕМ РАЗЫГРЫВАТЬ?");
        viewer.infoMessage(String.format("ОК. В НАШЕЙ ЛОТЕРЕЕ БУДЕТ РАЗЫГРАНО %d ПРИЗА(ОВ).", qtyOfPrizes));
        message = "Введите ID товаров, которые будут участвовать в розыгрыше, и для каждого из них через пробел задайте вес. \n" +
                "<Вес> - это целое положительное число, характеризующее частоту выпадения товара в лотерее\n"+
                "ФОРМАТ ВВОДА: <ID номенклатурной единицы> <Вес>\n" +
                "ДЛЯ ЗАВЕРШЕНИЯ ВВЕДИТЕ \"Q\" или \"q\"";
        viewer.infoMessage(message);
        String quitString = "q";
        Map<Integer, Lot> lotsMap = getLotsForLotteryFromConsole(quitString);
        Lottery ourLottery = new Lottery (store, lotsMap, numberOfParticipants, qtyOfPrizes);
        pathRequest = "ВВЕДИТЕ ПУТЬ К КАТАЛОГУ, В КОТОРЫЙ СЛЕДУЕТ ПОМЕСТИТЬ .TXT-ФАЙЛ С РЕЗУЛЬТАТАМИ РОЗЫГРЫША.\n" +
                "ЕСЛИ НАЖМЕТЕ ENTER, БУДЕТ СЧИТАТЬСЯ, ЧТО ЭТО ТЕКУЩИЙ РАБОЧИЙ КАТАЛОГ ";
        fileNameRequest = "ВВЕДИТЕ НАЗВАНИЕ .TXT-ФАЙЛА (БЕЗ КАВЫЧЕК, МОЖНО БЕЗ РАСШИРЕНИЯ), В КОТОРЫЙ СЛЕДУЕТ ЗАПИСАТЬ РЕЗУЛЬТАТЫ РОЗЫГРЫША.\n" +
                "ЕСЛИ НАЖМЕТЕ ENTER, БУДЕТ СЧИТАТЬСЯ, ЧТО ЭТО ФАЙЛ lotteryResult.txt";
        String resultFileName = getFileNameFromConsole (pathRequest, fileNameRequest, DEFAULT_PATH, LOTTERY_RESULT_FILE_NAME);
        System.out.println(ourLottery.getPrize());
        System.out.println(ourLottery.getPrize());
        System.out.println(ourLottery.getPrize());
        System.out.println(ourLottery.getPrize());
        System.out.println(ourLottery.getPrize());
        System.out.println(ourLottery.getPrize());
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
    private String getFileNameFromConsole (String pathRequest, String fileNameRequest,
                                           String defaultPath, String defaultFileName) throws WrongPathToFileException {
        String pathAsString = viewer.getUserInput(pathRequest);
        String fullFileNameAsString;
        if (pathAsString == "") {
            try {
                pathAsString = new File(defaultPath).getCanonicalPath();
            } catch (IOException e) {
                throw new WrongPathToFileException("НЕКОРРЕКТНЫЙ ПУТЬ К ФАЙЛУ С НОМЕНКЛАТУРОЙ МАГАЗИНА");
            }
        }  else {
            try {
                pathAsString = new File(pathAsString).getCanonicalPath();
            } catch (IOException e) {
                throw new WrongPathToFileException("НЕКОРРЕКТНЫЙ ПУТЬ К ФАЙЛУ С НОМЕНКЛАТУРОЙ МАГАЗИНА");
            }
        }
        Path path = Paths.get(pathAsString);
        if (!Files.exists(path)) throw new WrongPathToFileException("НЕКОРРЕКТНЫЙ ПУТЬ К ФАЙЛУ С НОМЕНКЛАТУРОЙ МАГАЗИНА!!!");
        else {
            System.out.println("Path = " + path.toString());
            String fileName = viewer.getUserInput(fileNameRequest);
            if (fileName != "") {
                fileName = fileName.strip();
                if (!fileName.endsWith(".txt")) fileName = fileName.concat(".txt");
            } else {
                fileName = defaultFileName;
            }
            fullFileNameAsString = String.format("%s\\%s", pathAsString, fileName);
            System.out.printf("Полное имя файла = %s\n", fullFileNameAsString);
            return fullFileNameAsString;
        }
    }

//        String fullFileName = viewer.getUserInput(message);
//        String fileNAme =
//        try {
//            toyStoreFileName = (File)scanner.nextLine());
//        } catch (IOException e) {
//
//        }
}

