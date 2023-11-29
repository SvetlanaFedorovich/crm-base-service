package mvpproject.crmbaseservice.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;
@Schema(description = "Сущность товара")
@Value
public class Product {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    Long productId;
    @Schema(description = "Наименование")
    String productName;
    @Schema(description = "Цена")
    BigDecimal price;
    @Schema(description = "Количество")
    Integer quantity;
    @Schema(description = "Единица измерения", example = "шт.")
    String unit;
}