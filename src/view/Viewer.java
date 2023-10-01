package view;

import exceptions.ErrorWhileReadingTheFileException;
import exceptions.WrongDataFormatException;
import exceptions.WrongInputDataException;
import exceptions.WrongPathToFileException;
import model.Lot;
import model.Prize;
import utils.Tuner;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Viewer {
    private final String ANSI_RESET = "\u001B[0m";
    private final String ANSI_BLACK = "\u001B[30m";
    private final String ANSI_RED = "\u001B[31m";
    private final String ANSI_GREEN = "\u001B[32m";
    private final String ANSI_YELLOW = "\u001B[33m";
    private final String ANSI_BLUE = "\u001B[34m";
    private final String ANSI_PURPLE = "\u001B[35m";
    private final String ANSI_CYAN = "\u001B[36m";
    private final String ANSI_WHITE = "\u001B[37m";

    public void infoMessage(String prompt) {
        System.out.println(String.format("%s%s%s",ANSI_YELLOW, prompt, ANSI_RESET));
    }
    public void promptMessage(String prompt) {
        System.out.print(String.format("%s%s%s%s",ANSI_PURPLE, prompt, "===> ", ANSI_RESET));
    }
    public String getUserInput (String prompt) {
        System.out.print(String.format("%s%s%s", ANSI_PURPLE, prompt, "===> ", ANSI_RESET));
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }
    public int getPositiveIntegerFromConsole(String promptMessage, int qtyOfAttempts) {
        infoMessage(promptMessage);
        return getPositiveInteger(qtyOfAttempts);
    }
    public String getFileNameFromConsole (String pathRequest, String fileNameRequest,
                                           String defaultPath, String defaultFileName) throws WrongPathToFileException {
        String pathAsString = getUserInput(pathRequest);
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
            String fileName = getUserInput(fileNameRequest);
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

    public int getPositiveInteger(int qtyOfAttempts) {
        int i, result = 0;
        String input;
        for (i = 0; i < qtyOfAttempts; i++) {
            input = getUserInput("ВВЕДИТЕ ЦЕЛОЕ ПОЛОЖИТЕЛЬНОЕ ЧИСЛО ");
            try {
                result = Integer.parseInt(input);
                i = qtyOfAttempts + 1;
            } catch (NumberFormatException e) {
                if (i < qtyOfAttempts - 1) System.out.printf("%d-я попытка: \n", i + 2);
            }
        }
        if (i == qtyOfAttempts || result <= 0) throw new WrongInputDataException("ВЫ НЕ СПРАВИЛИСЬ С ВВОДОМ ЦЕЛОГО ПОЛОЖИТЕЛЬНОГО"+
                "ЧИСЛА\n. ПРОГРАММА ЗАВЕРШАЕТ СВОЮ РАБОТУ...");
        return result;
    }
    public Map<Integer, Lot> getLotsForLotteryFromFile(Tuner usedTuner, String fileWithLotsName) {
        File fileWithLots = new File(fileWithLotsName);
        String nextLine;
        Map<Integer, Lot> result = new HashMap<>();
        int lotID = 1;
        try (BufferedReader br = new BufferedReader(new FileReader(fileWithLotsName))) {
            nextLine = br.readLine();
            while (nextLine != null) {
                result.put(lotID, usedTuner.parseLot(nextLine));
                lotID++;
                nextLine = br.readLine();
            }
        } catch (FileNotFoundException e) {
            throw new ErrorWhileReadingTheFileException(String.format("НЕ НАЙДЕН ФАЙЛ С ИМЕНЕМ %s", fileWithLotsName));
        } catch (IOException e) {
            throw new ErrorWhileReadingTheFileException(String.format("НЕ МОГУ СЧИТАТЬ ДАННЫЕ ИЗ ФАЙЛА %s. ВОЗМОЖНО"+
                    ", ОНИ ИМЕЮТ НЕВЕРНЫЙ ФОРМАТ.", fileWithLotsName));
        }
        return result;
    }
    public Map<Integer, Lot> getLotsForLotteryFromConsole (Tuner usedTuner, String quitString) throws WrongDataFormatException {
        Map<Integer, Lot> result = new HashMap<>();
        int lotID = 1;
        String userInput;
        String message = String.format("Лот № %-2d: (введите ID товара и вес через пробел) ", lotID);
        userInput = getUserInput(message);
        while (userInput.compareToIgnoreCase(quitString) != 0) {
            result.put(lotID, usedTuner.parseLot(userInput));
            lotID++;
            userInput = getUserInput(String.format("Лот № %-2d: (введите ID товара и вес через пробел) ", lotID));
        }
//        System.out.println("РЕЗУЛЬТИРУЮЩИЙ CПИСОК ЛОТОВ: \n");
        return result;
    }
    public void showResults(PriorityQueue<Prize> prizes, int numberOfParticipants) {
        System.out.println("СПИСОК ПРИЗОВ В ПОРЯДКЕ ИХ ВЫПАДЕНИЯ: ");
        Prize nextPrize;
        for (int count = 1; count <= numberOfParticipants; count++){
            nextPrize = prizes.poll();
            System.out.println(nextPrize);
        }
    }

}
