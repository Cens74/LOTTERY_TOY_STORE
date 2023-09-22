package exceptions;

public class WrongDataFormatException extends RuntimeException{
    private static final String commonMessage = "WRONG DATA FORMAT EXCEPTION: \n";
    public WrongDataFormatException(String message) {
        super(commonMessage + message);
    }
    public WrongDataFormatException() {
        super(commonMessage);
    }
}
