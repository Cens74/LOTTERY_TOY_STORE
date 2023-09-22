package exceptions;

public class ErrorWhileReadingTheFileException extends RuntimeException{
    private static final String commonMessage = "ERROR WHILE READING THE FILE: \n";
    public ErrorWhileReadingTheFileException(String message) {

        super(commonMessage + message);
    }
    public ErrorWhileReadingTheFileException() {
        super(commonMessage);
    }
}
