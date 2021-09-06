package webhosting.webhosting.hosting.ui;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import webhosting.webhosting.notification.service.NotificationService;

@ControllerAdvice
public class HostingControllerAdvice {

    private final NotificationService notificationService;

    public HostingControllerAdvice(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @ExceptionHandler({Exception.class})
    public String handleException(Exception exception) {
        notificationService.sendSlackExceptionMessage(exception.getMessage());
        return "/500.html";
    }
}
