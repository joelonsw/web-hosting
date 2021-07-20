package webhosting.webhosting.login.exception;

public class NotLoggedInException extends IllegalStateException {
    public NotLoggedInException(String message) {
        super(message);
    }
}
