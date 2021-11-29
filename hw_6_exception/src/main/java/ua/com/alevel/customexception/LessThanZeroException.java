package ua.com.alevel.customexception;

public final class LessThanZeroException extends Throwable {
    public LessThanZeroException() {
    }

    @Override
    public String toString() {
        return "RESULT IS LESS THAN ZERO";
    }
}
