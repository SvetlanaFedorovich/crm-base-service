package mvpproject.crmbaseservice.erorr;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
@Getter
public class ClientCreateException extends RuntimeException {

    public ClientCreateException() {
    }

    public ClientCreateException(String message) {
        super(message);
    }
}
