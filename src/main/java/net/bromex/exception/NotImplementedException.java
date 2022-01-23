package net.bromex.exception;

public class NotImplementedException extends Exception {

    public NotImplementedException(final String message) {
        super(message);
    }

    public NotImplementedException(final String message, final Throwable t) {
        super(message, t);
    }
}
