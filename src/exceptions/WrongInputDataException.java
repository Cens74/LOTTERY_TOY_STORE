package exceptions;

public class WrongInputDataException extends RuntimeException {
    private static final String commonMessage = "WRONG INPUT DATA: \n";
    public WrongInputDataException(String message) {
        super(commonMessage + message);
    }
    public WrongInputDataException() {
        super(commonMessage);
    }
}
