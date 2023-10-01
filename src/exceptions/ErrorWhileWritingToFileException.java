package exceptions;

public class ErrorWhileWritingToFileException extends RuntimeException{
    private static final String commonMessage = "ERROR WHILE READING THE FILE: \n";
    public ErrorWhileWritingToFileException(String message) {

        super(commonMessage + message);
    }
    public ErrorWhileWritingToFileException() {
        super(commonMessage);
    }
}