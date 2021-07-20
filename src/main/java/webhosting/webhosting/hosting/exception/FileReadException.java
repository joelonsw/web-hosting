package webhosting.webhosting.hosting.exception;

public class FileReadException extends RuntimeException {
    public FileReadException() {
        super("파일 조회에 실패했습니다.");
    }

    public FileReadException(String message) {
        super(message);
    }
}
