package utils;

import exceptions.WrongDataFormatException;
import exceptions.WrongPathToFileException;

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

    public boolean isTypeOfToy (String str) {
        return str.matches("^[а-яА-Я., <>\\-()_]*$") && str.endsWith(">") && str.startsWith("<");
    }
    public boolean isNameOfToy (String str) {
        return str.matches("^[а-яА-Яa-zA-Z0-9\\-\",.<>()_]*$")&& str.startsWith("<") && str.endsWith(">");
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
//            System.out.println(String.format("ind = %d", ind));
            subStr = helpStr.substring(0, ind);
//            System.out.println(subStr);
            roubles = Integer.parseInt(subStr);
//            System.out.println();
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
    public int parseWeight (String str) throws WrongDataFormatException {
        int result = 0;
        if (str.matches("^[0-9]*$")) {
            result = Integer.parseInt(str);
            return result;
        } else {
            // введенная строка содержит символы, которых не может быть в целом числе
            throw new WrongDataFormatException(String.format("НЕВЕРНЫЙ ФОРМАТ ДАННЫХ (ВЕС НОМЕНЛАТУРНОЙ ЕДИНИЦЫ)!!!"));
        }
    }
    public int parseQty (String str) throws WrongDataFormatException {
        int result = 0;
        if (str.matches("^[0-9]*$")) {
            result = Integer.parseInt(str);
            return result;
        } else {
            // введенная строка содержит символы, которых не может быть в целом числе
            throw new WrongDataFormatException(String.format("НЕВЕРНЫЙ ФОРМАТ ДАННЫХ (ВЕС НОМЕНЛАТУРНОЙ ЕДИНИЦЫ)!!!"));
        }
    }
}
