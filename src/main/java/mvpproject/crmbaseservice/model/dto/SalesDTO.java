package mvpproject.crmbaseservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesDTO {
    private Long id;
    private Long clientId;
    private Long productId;
    private Integer quantity;
    private LocalDate salesDate;

}
