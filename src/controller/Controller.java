package controller;

import exceptions.ErrorWhileWritingToFileException;
import exceptions.WrongInputDataException;
import exceptions.WrongPathToFileException;
import model.*;
import view.Viewer;
import utils.Tuner;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Controller {
    private final Viewer viewer;
    private final Tuner tuner;

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
        String fileNameRequest = String.format("ВВЕДИТЕ НАЗВАНИЕ .TXT-ФАЙЛА (БЕЗ КАВЫЧЕК, МОЖНО БЕЗ РАСШИРЕНИЯ) С НОМЕНКЛАТУРОЙ МАГАЗИНА\n" +
                "ЕСЛИ НАЖМЕТЕ ENTER, БУДЕТ СЧИТАТЬСЯ, ЧТО ЭТО ФАЙЛ %s", tuner.DEFAULT_TOY_STORE_FILE_NAME);
        String storeFileName = viewer.getFileNameFromConsole (pathRequest, fileNameRequest, tuner.DEFAULT_PATH, tuner.DEFAULT_TOY_STORE_FILE_NAME);
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
        int numberOfParticipants = viewer.getPositiveIntegerFromConsole("\nСКОЛЬКО ДЕТЕЙ БУДЕТ УЧАСТВОВАТЬ В РОЗЫГРЫШЕ? ", tuner.QTY_OF_ATTEMPTS);
        viewer.infoMessage(String.format("ОК. В НАШЕЙ ЛОТЕРЕЕ БУДЕТ %d УЧАСТНИКА(ОВ).", numberOfParticipants));
        int qtyOfPrizes = 0, iter = 0;
        while (qtyOfPrizes < numberOfParticipants && iter < tuner.QTY_OF_ATTEMPTS) {
            qtyOfPrizes = viewer.getPositiveIntegerFromConsole("СКОЛЬКО ПРИЗОВ БУДЕМ РАЗЫГРЫВАТЬ?" +
                    " КОЛИЧЕСТВО ПРИЗОВ ДОЛЖНО БЫТЬ БОЛЬШЕ ЧИСЛА УЧАСТНИКОВ. Попробуйте еще раз.", tuner.QTY_OF_ATTEMPTS);
            iter++;
        }
        if (iter == tuner.QTY_OF_ATTEMPTS) throw new WrongInputDataException("НЕ ПОЛУЧИТСЯ ПРОВЕСТИ ЛОТЕРЕЮ С ТАКИМИ ИСХОДНЫМИ ДАННЫМИ. ИЗВИНИТЕ ((");
        viewer.infoMessage(String.format("ОК. В НАШЕЙ ЛОТЕРЕЕ БУДЕТ РАЗЫГРАНО %d ПРИЗА(ОВ).", qtyOfPrizes));
        viewer.infoMessage(String.format("\n Теперь необходимо определить список лотов, которые будут принимать участие в розыгрыше,"));
        viewer.infoMessage(String.format("и для каждого из них указать вес, характеризующий относительную частоту появления этого товара."));
        String answer = viewer.getUserInput("Вы будете вводить лоты с клавиатуры или укажете имя файла, из которого следует брать данные? \n"+
                "Введите цифру, соответствующую вашему выбору: \n1 - c клавиатуры,\n2 - из файла\n");
        Map<Integer, Lot> lotsMap;
        switch (answer) {
            case "1":
                viewer.infoMessage(String.format("ФОРМАТ ВВОДА: <ID номенклатурной единицы> <Вес> - через пробел"+
                        " и на отдельной строке для каждого товара\n"+
                        String.format("ДЛЯ ЗАВЕРШЕНИЯ ВВЕДИТЕ \"%s\"", tuner.STANDARD_QUIT_STRING)));
                lotsMap = viewer.getLotsForLotteryFromConsole(tuner, tuner.STANDARD_QUIT_STRING);
                break;
            case "2":
                viewer.infoMessage(String.format("ФОРМАТ .txt-ФАЙЛА: <ID номенклатурной единицы> <Вес> - "+
                        "через пробел и на отдельной строке для каждого товара"));
                pathRequest = "ВВЕДИТЕ ПУТЬ К КАТАЛОГУ, В КОТОРОМ НАХОДИТСЯ .TXT-ФАЙЛ СО СПИСКОМ ЛОТОВ.\n" +
                        "ЕСЛИ НАЖМЕТЕ ENTER, БУДЕТ СЧИТАТЬСЯ, ЧТО ЭТО ТЕКУЩИЙ РАБОЧИЙ КАТАЛОГ ";
                fileNameRequest = "ВВЕДИТЕ НАЗВАНИЕ .TXT-ФАЙЛА (БЕЗ КАВЫЧЕК, МОЖНО БЕЗ РАСШИРЕНИЯ) СО СПИСКОМ ЛОТОВ.\n" +
                        "ЕСЛИ НАЖМЕТЕ ENTER, БУДЕТ СЧИТАТЬСЯ, ЧТО ЭТО ФАЙЛ lots.txt";
                String lotsFileName = viewer.getFileNameFromConsole (pathRequest, fileNameRequest, tuner.DEFAULT_PATH, tuner.DEFAULT_LOTS_FILE_NAME);;
                lotsMap = viewer.getLotsForLotteryFromFile(tuner, lotsFileName);
                break;
            default:
                throw new WrongInputDataException("Unexpected value: " + answer);
        }
        viewer.infoMessage("\nВсе необходимые данные для проведения розыгрыша получены. Запускаем лотерею...\n");
//        String quitString = "q";
//        Map<Integer, Lot> lotsMap = getLotsForLotteryFromConsole(STANDARD_QUIT_STRING);
        Lottery ourLottery = new Lottery (store, lotsMap, numberOfParticipants, qtyOfPrizes);
        answer = viewer.getUserInput("Куда вывести результаты? "+
                "Введите цифру, соответствующую вашему выбору: \n1 - в консоль,\n2 - в файл\n");
        switch (answer) {
            case "1":
                viewer.showResults(ourLottery.getPrizes(), numberOfParticipants);
                break;
            case "2":
                pathRequest = "ВВЕДИТЕ ПУТЬ К КАТАЛОГУ, В КОТОРЫЙ СЛЕДУЕТ ПОМЕСТИТЬ .TXT-ФАЙЛ С РЕЗУЛЬТАТАМИ РОЗЫГРЫША.\n" +
                        "ЕСЛИ НАЖМЕТЕ ENTER, БУДЕТ СЧИТАТЬСЯ, ЧТО ЭТО ТЕКУЩИЙ РАБОЧИЙ КАТАЛОГ ";
                fileNameRequest = "ВВЕДИТЕ НАЗВАНИЕ .TXT-ФАЙЛА (БЕЗ КАВЫЧЕК, МОЖНО БЕЗ РАСШИРЕНИЯ), В КОТОРЫЙ СЛЕДУЕТ ЗАПИСАТЬ РЕЗУЛЬТАТЫ.\n" +
                        "ЕСЛИ НАЖМЕТЕ ENTER, БУДЕТ СЧИТАТЬСЯ, ЧТО ЭТО ФАЙЛ lotteryResult.txt";
                String resultFileName = viewer.getFileNameFromConsole (pathRequest, fileNameRequest, tuner.DEFAULT_PATH, tuner.LOTTERY_RESULT_FILE_NAME);
                writeResultsToFile(resultFileName, ourLottery.getPrizes(), numberOfParticipants);
                break;
            default:
                throw new WrongInputDataException("Unexpected value: " + answer);
        }
    }

//    private Map<Integer, Lot> getLotsForLotteryFromConsole (Tuner tuner, String quitString) {
//        Map<Integer, Lot> result = new HashMap<>();
//        int lotID = 1;
//        String userInput;
//        String message = String.format("Лот № %-2d: (введите ID товара и вес через пробел) ", lotID);
//        userInput = viewer.getUserInput(message);
//        while (userInput.compareToIgnoreCase(quitString) != 0) {
//            result.put(lotID, this.tuner.parseLot(userInput));
//            lotID++;
//            userInput = viewer.getUserInput(String.format("Лот № %-2d: (введите ID товара и вес через пробел) ", lotID));
//        }
////        System.out.println("РЕЗУЛЬТИРУЮЩИЙ CПИСОК ЛОТОВ: \n");
//        return result;
//    }
//    private String getFileNameFromConsole (String pathRequest, String fileNameRequest,
//                                           String defaultPath, String defaultFileName) throws WrongPathToFileException {
//        String pathAsString = viewer.getUserInput(pathRequest);
//        String fullFileNameAsString;
//        if (pathAsString == "") {
//            try {
//                pathAsString = new File(defaultPath).getCanonicalPath();
//            } catch (IOException e) {
//                throw new WrongPathToFileException("НЕКОРРЕКТНЫЙ ПУТЬ К ФАЙЛУ С НОМЕНКЛАТУРОЙ МАГАЗИНА");
//            }
//        }  else {
//            try {
//                pathAsString = new File(pathAsString).getCanonicalPath();
//            } catch (IOException e) {
//                throw new WrongPathToFileException("НЕКОРРЕКТНЫЙ ПУТЬ К ФАЙЛУ С НОМЕНКЛАТУРОЙ МАГАЗИНА");
//            }
//        }
//        Path path = Paths.get(pathAsString);
//        if (!Files.exists(path)) throw new WrongPathToFileException("НЕКОРРЕКТНЫЙ ПУТЬ К ФАЙЛУ С НОМЕНКЛАТУРОЙ МАГАЗИНА!!!");
//        else {
//            System.out.println("Path = " + path.toString());
//            String fileName = viewer.getUserInput(fileNameRequest);
//            if (fileName != "") {
//                fileName = fileName.strip();
//                if (!fileName.endsWith(".txt")) fileName = fileName.concat(".txt");
//            } else {
//                fileName = defaultFileName;
//            }
//            fullFileNameAsString = String.format("%s\\%s", pathAsString, fileName);
//            System.out.printf("Полное имя файла = %s\n", fullFileNameAsString);
//            return fullFileNameAsString;
//        }
//    }
    private void writeResultsToFile (String fileName, PriorityQueue<Prize> results, int ParticipantsQty) throws ErrorWhileWritingToFileException {
        File fileWithResults = new File(fileName);
        Prize nextPrize;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileWithResults, false  ));) {
            for (int count = 1; count <= ParticipantsQty; count++) {
                nextPrize = results.poll();
                bw.write(nextPrize.toString()+"\n");
            }
        } catch (IOException e) {
            throw new ErrorWhileWritingToFileException(String.format("НЕВОЗМОЖНО ПРОИЗВЕСТИ ЗАПИСЬ РЕЗУЛЬТАТОВ В ФАЙЛ %s", fileName));
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

