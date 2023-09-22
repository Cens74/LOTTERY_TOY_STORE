package exceptions;

import java.io.IOException;

public class WrongPathToFileException extends IOException {
    private static final String commonMessage = "WRONG INPUT DATA: \n";
    public WrongPathToFileException(String message) {
        super(commonMessage + message);
    }
    public WrongPathToFileException() {
        super(commonMessage);
    }
}
