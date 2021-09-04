package webhosting.webhosting.hosting.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HostingControllerAdvice {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<String> handleException(Exception exception) {
        return ResponseEntity.internalServerError().body(exception.getMessage());
    }
}
