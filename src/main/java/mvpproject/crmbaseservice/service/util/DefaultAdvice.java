package mvpproject.crmbaseservice.service.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DefaultAdvice {

    @ExceptionHandler(ClientCreateException.class)
    public ResponseEntity<Response> handleException(ClientCreateException cce) {
        Response response = new Response(cce.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
