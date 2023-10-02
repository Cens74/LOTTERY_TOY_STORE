package exceptions;

import utils.Tuner;

public class LotteryCreationException extends RuntimeException {
    private static final String commonMessage = "LOTTERY CREATION EXCEPTION: \n";
    public LotteryCreationException (String message) {

        super(Tuner.ANSI_RED+commonMessage + "\n" + message+Tuner.ANSI_RESET);
    }
    public LotteryCreationException() {
        super(Tuner.ANSI_RED+commonMessage+Tuner.ANSI_RESET);
    }
}

