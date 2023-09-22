package view;

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
        System.out.println(String.format("%s%s%s%s",ANSI_YELLOW, prompt, ANSI_RESET, "\n"));
    }
    public void promptMessage(String prompt) {
        System.out.print(String.format("%s%s%s%s",ANSI_PURPLE, prompt, "===> ", ANSI_RESET));
    }
    public String getUserInput (String prompt) {
        System.out.print(String.format("%s%s%s", ANSI_PURPLE, prompt, "===> ", ANSI_RESET));
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }
}
