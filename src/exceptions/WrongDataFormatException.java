package exceptions;

import utils.Tuner;

public class WrongDataFormatException extends RuntimeException{
    private static final String commonMessage = "WRONG DATA FORMAT EXCEPTION: \n";
    public WrongDataFormatException(String message) {
        super(Tuner.ANSI_RED+ commonMessage + "\n" + message+ Tuner.ANSI_RESET);
    }
    public WrongDataFormatException() {
        super(Tuner.ANSI_RED+commonMessage+Tuner.ANSI_RESET);
    }
}
