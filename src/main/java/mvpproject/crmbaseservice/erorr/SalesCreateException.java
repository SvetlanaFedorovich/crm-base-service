package mvpproject.crmbaseservice.erorr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SalesCreateException extends ResponseStatusException {

    private static final Logger LOGGER = LoggerFactory.getLogger(SalesCreateException.class);
    private String fieldName;

    public SalesCreateException(String reason, String fieldName) {
        super(HttpStatus.BAD_REQUEST, reason);
        this.fieldName = fieldName;
        LOGGER.error("SalesCreateException: " + reason + ". Null field: " + fieldName, new Throwable());
    }

    public String getFieldName() {
        return fieldName;
    }
}
