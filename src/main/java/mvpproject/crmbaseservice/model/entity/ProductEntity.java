package mvpproject.crmbaseservice.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "Сущность товара")
@Data
@Entity
@Table(name = "products")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long productId;

    @Column(name = "product_name", nullable = false, length = 100, unique = true)
    @Schema(description = "Наименование")
    private String productName;

    @Column(name = "price", nullable = false)
    @Schema(description = "Цена")
    private BigDecimal price;

    @Column(name = "quantity", nullable = false)
    @Schema(description = "Количество")
    private Integer quantity;

    @Column(name = "unit", nullable = false, length = 10)
    @Schema(description = "Единица измерения", example = "шт.")
    private String unit;
}
