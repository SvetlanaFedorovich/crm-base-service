package mvpproject.crmbaseservice.erorr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SalesNotFoundException extends ResponseStatusException {

    private static final Logger LOGGER = LoggerFactory.getLogger(SalesNotFoundException.class);
    public SalesNotFoundException(String reason) {
        super(HttpStatus.NOT_FOUND, reason);
        LOGGER.error("SalesNotFoundException: " + reason, new Throwable());
    }
}
