package exceptions;

import utils.Tuner;

import java.io.IOException;

public class WrongPathToFileException extends IOException {
    private static final String commonMessage = "WRONG INPUT DATA: \n";
    public WrongPathToFileException(String message) {
        super(Tuner.ANSI_RED+commonMessage + "\n" + message+Tuner.ANSI_RESET);
    }
    public WrongPathToFileException() {
        super(Tuner.ANSI_RED+commonMessage+Tuner.ANSI_RESET);
    }
}
