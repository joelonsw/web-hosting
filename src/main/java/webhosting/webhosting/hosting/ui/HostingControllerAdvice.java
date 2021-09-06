package webhosting.webhosting.hosting.ui;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HostingControllerAdvice {

    @ExceptionHandler({Exception.class})
    public String handleException(Exception exception) {
        return "/500.html";
    }
}
