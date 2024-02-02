package mvpproject.crmbaseservice.erorr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class InvalidFilterException extends ResponseStatusException {
    private static final Logger LOGGER = LoggerFactory.getLogger(InvalidFilterException.class);
    public InvalidFilterException(HttpStatusCode status, String reason, Throwable cause) {
        super(status, reason, cause);
        LOGGER.error("Invalid filter exception occurred: " + reason + ", Status: " + status, cause);
    }
}
