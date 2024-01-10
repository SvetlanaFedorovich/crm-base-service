package mvpproject.crmbaseservice.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DefaultAdvice {

    @ExceptionHandler(ClientCreateException.class)
    public ResponseEntity<ErrorResponse> handleException(ClientCreateException cce) {
        ErrorResponse errorResponse = new ErrorResponse(cce.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }
}
