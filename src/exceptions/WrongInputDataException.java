package exceptions;

import utils.Tuner;

public class WrongInputDataException extends RuntimeException {
    private static final String commonMessage = "WRONG INPUT DATA: \n";
    public WrongInputDataException(String message) {
        super(Tuner.ANSI_RED+commonMessage + "\n" + message+Tuner.ANSI_RESET);
    }
    public WrongInputDataException() {
        super(Tuner.ANSI_RED+commonMessage+Tuner.ANSI_RESET);
    }
}
