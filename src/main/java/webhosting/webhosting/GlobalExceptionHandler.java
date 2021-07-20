package webhosting.webhosting;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import webhosting.webhosting.hosting.exception.FileReadException;
import webhosting.webhosting.hosting.exception.FileSaveException;
import webhosting.webhosting.hosting.exception.UserNameEncodingException;
import webhosting.webhosting.login.exception.GoogleUserGenerationException;
import webhosting.webhosting.login.exception.NotLoggedInException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({FileReadException.class, FileSaveException.class,
            UserNameEncodingException.class, GoogleUserGenerationException.class})
    public String handleException() {
        return "redirect:/error";
    }

    @ExceptionHandler(NotLoggedInException.class)
    public ResponseEntity<String> handleNotLoggedIn(NotLoggedInException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
