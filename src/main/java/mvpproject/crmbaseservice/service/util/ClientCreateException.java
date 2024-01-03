package mvpproject.crmbaseservice.service.util;

public class ClientCreateException extends Exception {
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
