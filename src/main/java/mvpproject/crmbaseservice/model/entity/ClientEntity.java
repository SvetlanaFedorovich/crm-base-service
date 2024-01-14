package mvpproject.crmbaseservice.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clients")
@Schema(description = "Сущность клиента")
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientId;

    @Column(name = "full_name", nullable = false, length = 20)
    @Schema(description = "Наименование", example = "ООО Океан")
    private String clientName;

    @Column(name = "address", nullable = false, length = 100)
    @Schema(description = "Адрес")
    private String address;

    @Column(name = "payer_account_number", nullable = false, length = 40, unique = true)
    @Schema(description = "УНН", example = "53154714Т")
    private String payerAccountNumber;

    @Column(name = "bank_details", nullable = false, length = 100)
    @Schema(description = "Банковские реквизиты")
    private String bankDetails;
}
