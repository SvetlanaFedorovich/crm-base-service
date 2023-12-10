package mvpproject.crmbaseservice.service.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserErrorResponse {

    private String massage;
    private long timestamp;
}
