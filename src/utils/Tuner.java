package utils;

import exceptions.WrongDataFormatException;
import exceptions.WrongInputDataException;
import model.Lot;

public class Tuner {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public final String DEFAULT_PATH = ".";
    public final String DEFAULT_TOY_STORE_FILE_NAME = "toyStore.txt";
    public final String DEFAULT_NOMENCLATURE_FILE_NAME = "nomenclature.txt";
    public final String LOTTERY_RESULT_FILE_NAME = "lotteryResult.txt";
    public final String DEFAULT_LOTS_FILE_NAME = "lots.txt";
    public final String STANDARD_QUIT_STRING = "q";
    public final int QTY_OF_ATTEMPTS = 3;

    public boolean isTypeOfToy (String str) {
        return str.matches("^[а-яА-Я., <>\\-()_]*$");
    }
    public boolean isNameOfToy (String str) {
        return str.matches("^[а-яА-Яa-zA-Z0-9\\- \",.<>()_]*$");
    }
    public boolean isID (String str) {
        return str.matches("^[0-9]*$")&& Integer.parseInt(str) > 0;
    }
    /**
     * Метод пытается получить число типа Double из строки
     * */
    public double parsePrice (String str) throws WrongDataFormatException {
        double result = 0.0;
        String helpStr = new String(str);
        String subStr = null;
        int roubles = 0;
        double kopecks = 0;
        if (helpStr.matches("^[0-9.,]*$")) {
            int ind = helpStr.indexOf(".");
            if (ind == -1) {
                ind = helpStr.indexOf(",");
                if (ind == -1) {
                    result = Integer.parseInt(helpStr);
                    return (double) result;
                }
            }
            subStr = helpStr.substring(0, ind);
            roubles = Integer.parseInt(subStr);
            subStr = helpStr.substring(ind + 1);
            if (subStr != null && subStr.length() > 0) {
                kopecks = Integer.parseInt(subStr);
                for (int i = 0; i < subStr.length(); i++) {
                    kopecks = kopecks*0.1;
                }
            }
            result = roubles + kopecks;
        } else {
            // введенная строка содержит символы, которых не может быть в вещественном числе
            throw new WrongDataFormatException(String.format("НЕВЕРНЫЙ ФОРМАТ ЦЕНЫ!!!"));
        }
        return result;
    }
    public int parseQty (String str) throws WrongDataFormatException, WrongInputDataException {
        int result = 0;
        if (str.matches("^[0-9]*$")) {
            result = Integer.parseInt(str);
            if (result > 0) return result;
            else throw new WrongInputDataException("КОЛИЧЕСТВО НЕ МОЖЕТ БЫТЬ НУЛЕВЫМ");
        } else {
            // введенная строка содержит символы, которых не может быть в целом числе
            throw new WrongDataFormatException("НЕВЕРНЫЙ ФОРМАТ ДАННЫХ (ID НОМЕНЛАТУРНОЙ ЕДИНИЦЫ)!!!");
        }
    }
    public int parseID (String str) throws WrongDataFormatException {
        int result = 0;
        if (str.matches("^[0-9]*$")) {
            result = Integer.parseInt(str);
            if (result > 0) return result;
            else throw new WrongInputDataException("ИДЕНТИФИКАТОР ДОЛЖЕН БЫТЬ ЦЕЛЫМ И НЕНУЛЕВЫМ");
        } else {
            // введенная строка содержит символы, которых не может быть в целом числе
            throw new WrongDataFormatException("НЕВЕРНЫЙ ФОРМАТ ДАННЫХ (ID НОМЕНЛАТУРНОЙ ЕДИНИЦЫ)!!!");
        }
    }

    public Lot parseLot (String str) throws WrongDataFormatException {
        Lot result = new Lot();
        String tempString = str.strip();
        String[] fields;
        fields = tempString.split(" ");
        if (fields.length != 2) throw new WrongDataFormatException("НЕВЕРНЫЙ ФОРМАТ ЗАДАНИЯ ЛОТА!!!");
        result.setItem(parseID(fields[0]));
        result.setWeight(parseID(fields[1]));
        return result;
    }
}
