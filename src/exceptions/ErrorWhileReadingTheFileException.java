package exceptions;

import utils.Tuner;

public class ErrorWhileReadingTheFileException extends RuntimeException{
    private static final String commonMessage = "ERROR WHILE READING THE FILE: \n";
    public ErrorWhileReadingTheFileException(String message) {

        super(Tuner.ANSI_RED+commonMessage + "\n" + message+Tuner.ANSI_RESET);
    }
    public ErrorWhileReadingTheFileException() {
        super(commonMessage);
    }
}
