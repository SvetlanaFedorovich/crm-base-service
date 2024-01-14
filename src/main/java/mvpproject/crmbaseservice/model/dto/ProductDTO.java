package mvpproject.crmbaseservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {

    private String productName;
    private BigDecimal price;
    private Integer quantity;
    private String unit;
}
