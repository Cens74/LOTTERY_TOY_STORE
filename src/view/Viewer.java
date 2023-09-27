package view;

import exceptions.WrongInputDataException;
import model.Lot;

import java.util.Map;
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


}
