package mvpproject.crmbaseservice.error;

import lombok.Getter;
import lombok.Setter;
import mvpproject.crmbaseservice.error.exception.CustomException;

@Setter
@Getter
public class ClientCreateException extends RuntimeException {

        public ClientCreateException() {
    }

    public ClientCreateException(String message) {
        super(message);
    }

    public ClientCreateException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClientCreateException(Throwable cause) {
        super(cause);
    }

    public ClientCreateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}