package mvpproject.crmbaseservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientDTO {

    private String clientName;
    private String address;
    private String payerAccountNumber;
    private String bankDetails;
}
