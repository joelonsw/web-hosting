package webhosting.webhosting.login.exception;

public class NotLoggedInException extends IllegalStateException {
    public NotLoggedInException() {
        super("해당 사용자가 없습니다.");
    }

    public NotLoggedInException(String message) {
        super(message);
    }
}
