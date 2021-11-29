package ua.com.alevel.customexception;

public final class WrongInputException extends Throwable {
    public WrongInputException() {
    }

    @Override
    public String toString() {
        return "WRONG INPUT";
    }
}
