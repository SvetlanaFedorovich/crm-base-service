package mvpproject.crmbaseservice.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
@Schema(description = "Сущность товара")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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


