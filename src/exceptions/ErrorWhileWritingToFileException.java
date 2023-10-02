package exceptions;

import utils.Tuner;

public class ErrorWhileWritingToFileException extends RuntimeException{
    private static final String commonMessage = "ERROR WHILE READING THE FILE: \n";
    public ErrorWhileWritingToFileException(String message) {

        super(Tuner.ANSI_RED+commonMessage + "\n" + message+Tuner.ANSI_RESET);
    }
    public ErrorWhileWritingToFileException() {
        super(Tuner.ANSI_RED+commonMessage+Tuner.ANSI_RESET);
    }
}