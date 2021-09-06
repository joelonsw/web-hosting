package webhosting.webhosting.hosting.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import webhosting.webhosting.hosting.exception.FileReadException;
import webhosting.webhosting.hosting.exception.FileSaveException;
import webhosting.webhosting.hosting.exception.FolderExistException;
import webhosting.webhosting.hosting.exception.UserNameEncodingException;
import webhosting.webhosting.notification.service.NotificationService;

@ControllerAdvice
public class HostingControllerAdvice {

    private final NotificationService notificationService;

    public HostingControllerAdvice(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @ExceptionHandler({FileReadException.class, FileSaveException.class, FolderExistException.class, UserNameEncodingException.class})
    public ResponseEntity<Void> handleCustomException() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler({Exception.class})
    public String handleException(Exception exception) {
        notificationService.sendSlackExceptionMessage(exception.getMessage());
        return "/500.html";
    }
}
