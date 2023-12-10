package mvpproject.crmbaseservice.model.dto;

import lombok.Data;

@Data
public class ClientDto {

    private String clientName;
    private String address;
    private String payerAccountNumber;
    private String bankDetails;
}
