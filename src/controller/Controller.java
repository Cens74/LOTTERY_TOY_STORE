package controller;

import exceptions.ErrorWhileWritingToFileException;
import exceptions.WrongInputDataException;
import exceptions.WrongPathToFileException;
import model.*;
import view.Viewer;
import utils.Tuner;

import java.io.*;
import java.nio.file.Path;
import java.util.*;

public class Controller {
    private final Viewer viewer;
    private final Tuner tuner;

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
        String storeFileName = viewer.getFileNameFromConsole (pathRequest, fileNameRequest, tuner.DEFAULT_PATH,
                tuner.DEFAULT_TOY_STORE_FILE_NAME);
        ToyStore store = new ToyStore(Path.of(new File(storeFileName).getCanonicalPath()));
        String answer = viewer.getUserInput("ХОТИТЕ ОЗНАКОМИТЬСЯ С НОМЕНКЛАТУРОЙ МАГАЗИНА ИГРУШЕК? \n"+
                "Введите цифру, соответствующую вашему выбору: \n1 - ХОЧУ,\n2 - НЕТ\n");
        if (answer.equals("1")) {
            String choice = viewer.getUserInput("ВЫ ХОТИТЕ ПОЛУЧИТЬ СПИСОК НОМЕНКЛАТУРЫ В КОНСОЛЬ ИЛИ В ФАЙЛ? \n"+
                    "Введите цифру, соответствующую вашему выбору: \n1 - В КОНСОЛЬ,\n2 - В ФАЙЛ\n");
            switch (choice) {
                case "1":
                    store.printToConsole();
                    break;
                case "2":
                    pathRequest = "ВВЕДИТЕ ПУТЬ К КАТАЛОГУ, В КОТОРЫЙ СЛЕДУЕТ ПОМЕСТИТЬ .TXT-ФАЙЛ С НОМЕНКЛАТУРОЙ.\n" +
                            "ЕСЛИ НАЖМЕТЕ ENTER, БУДЕТ СЧИТАТЬСЯ, ЧТО ЭТО ТЕКУЩИЙ РАБОЧИЙ КАТАЛОГ ";
                    fileNameRequest = "ВВЕДИТЕ НАЗВАНИЕ .TXT-ФАЙЛА (БЕЗ КАВЫЧЕК, МОЖНО БЕЗ РАСШИРЕНИЯ).\n" +
                            "ЕСЛИ НАЖМЕТЕ ENTER, БУДЕТ СЧИТАТЬСЯ, ЧТО ЭТО ФАЙЛ nomenclature.txt";
                    String nomenclatureFileName = viewer.getFileNameFromConsole(pathRequest, fileNameRequest,
                            tuner.DEFAULT_PATH, tuner.DEFAULT_NOMENCLATURE_FILE_NAME);
                    store.printToFile(nomenclatureFileName);
                    break;
                default:
                    throw new WrongInputDataException("Unexpected value: " + answer);
            }
        } else if (!answer.equals("2")) {
            throw new WrongInputDataException("Unexpected value: " + answer);
        }

        int numberOfParticipants = viewer.getPositiveIntegerFromConsole("\nСКОЛЬКО ДЕТЕЙ БУДЕТ УЧАСТВОВАТЬ"+"" +
                " В РОЗЫГРЫШЕ? ", tuner.QTY_OF_ATTEMPTS);
        viewer.infoMessage(String.format("ОК. В НАШЕЙ ЛОТЕРЕЕ БУДЕТ %d УЧАСТНИКА(ОВ).", numberOfParticipants));
        int qtyOfPrizes = viewer.getPositiveIntegerFromConsole("СКОЛЬКО ПРИЗОВ БУДЕМ РАЗЫГРЫВАТЬ?" +
                " КОЛИЧЕСТВО ПРИЗОВ НЕ МОЖЕТ БЫТЬ МЕНЬШЕ ЧИСЛА УЧАСТНИКОВ", tuner.QTY_OF_ATTEMPTS);
        if (qtyOfPrizes < numberOfParticipants) throw new WrongInputDataException("НЕ ПОЛУЧИТСЯ ПРОВЕСТИ ЛОТЕРЕЮ С"+
                " ТАКИМИ ИСХОДНЫМИ ДАННЫМИ. ИЗВИНИТЕ ((");
        viewer.infoMessage(String.format("ОК. В НАШЕЙ ЛОТЕРЕЕ БУДЕТ РАЗЫГРАНО %d ПРИЗА(ОВ).", qtyOfPrizes));
        viewer.infoMessage(String.format("\n Теперь необходимо определить список лотов, которые будут принимать"+
                " участие в розыгрыше,"));
        viewer.infoMessage(String.format("и для каждого из них указать вес, характеризующий относительную частоту "+
                "выпадения этого лота."));
        answer = viewer.getUserInput("Вы будете вводить лоты с клавиатуры или укажете имя файла, "+
                "из которого следует брать данные? \n"+
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
                String lotsFileName = viewer.getFileNameFromConsole (pathRequest, fileNameRequest, tuner.DEFAULT_PATH,
                        tuner.DEFAULT_LOTS_FILE_NAME);;
                lotsMap = viewer.getLotsForLotteryFromFile(tuner, lotsFileName);
                break;
            default:
                throw new WrongInputDataException("Unexpected value: " + answer);
        }
        viewer.infoMessage("\nВсе необходимые данные для проведения розыгрыша получены. Запускаем лотерею...\n");
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
                fileNameRequest = "ВВЕДИТЕ НАЗВАНИЕ .TXT-ФАЙЛА (БЕЗ КАВЫЧЕК, МОЖНО БЕЗ РАСШИРЕНИЯ), В КОТОРЫЙ СЛЕДУЕТ "+
                        "ЗАПИСАТЬ РЕЗУЛЬТАТЫ.\n" +
                        "ЕСЛИ НАЖМЕТЕ ENTER, БУДЕТ СЧИТАТЬСЯ, ЧТО ЭТО ФАЙЛ lotteryResult.txt";
                try {
                    String resultFileName = viewer.getFileNameFromConsole(pathRequest, fileNameRequest, tuner.DEFAULT_PATH,
                            tuner.LOTTERY_RESULT_FILE_NAME);
                    writeResultsToFile(resultFileName, ourLottery.getPrizes(), numberOfParticipants);
                } catch (WrongPathToFileException e) {
                    throw new WrongPathToFileException();
                }
                break;
            default:
                throw new WrongInputDataException("UNEXPECTED VALUE: " + answer);
        }
    }
    private void writeResultsToFile (String fileName, PriorityQueue<Prize> results, int ParticipantsQty) throws ErrorWhileWritingToFileException {
        File fileWithResults = new File(fileName);
        Prize nextPrize;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileWithResults, false  ));) {
            bw.write(String.format("В ЛОТЕРЕЕ ПРИНИМАЛО УЧАСТИЕ %d ДЕТЕЙ.\n", ParticipantsQty));
            bw.write(String.format("БЫЛО РАЗЫГРАНО %d ПРИЗОВ.\n", results.size()));
            bw.write("ВОТ СПИСОК ПРИЗОВ, КОТОРЫЕ ДОСТАЛИСЬ УЧАСТНИКАМ: \n\n");
            for (int count = 1; count <= ParticipantsQty; count++) {
                nextPrize = results.poll();
                bw.write(String.format("%d. ", count)+nextPrize.toString()+"\n");
            }
        } catch (IOException e) {
            throw new ErrorWhileWritingToFileException(String.format("НЕВОЗМОЖНО ПРОИЗВЕСТИ ЗАПИСЬ РЕЗУЛЬТАТОВ В ФАЙЛ %s", fileName));
        }
    }
}

