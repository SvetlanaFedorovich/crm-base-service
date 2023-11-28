package mvpproject.crmbaseservice.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.Value;

@Schema(description = "Сущность клиента")
@Value
public class Client {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    Long clientId;
    @Schema(description = "Наименование", example = "ООО Океан")
    String clientName;
    @Schema(description = "Адрес")
    String address;
    @Schema(description = "УНН", example = "53154714Т")
    String payerAccountNumber;
    @Schema(description = "Банковские реквизиты")
    String bankDetails;
}
