package mvpproject.crmbaseservice.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mvpproject.crmbaseservice.service.ProductService;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Product controller", description = "Содержит эндпойнты для работы с товаром")
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
//
//    @Operation(
//            summary = "Прочитать все товары",
//            description = "Позволяет получить список всех товаров из базы данных"
//    )
//    @GetMapping("/product")
//    public String getProducts() {
//        return productService.getProduct();
//    }
//
//    @Operation(
//            summary = "Прочитать товар по id",
//            description = "Позволяет получить описание определенного товара по его id"
//    )
//    @GetMapping("/product/{productId}")
//    public String getProduct(@PathVariable("productId") @Parameter(description = "Идентификатор товара") Long productId) {
//        return productService.getProductById(productId);
//    }
//
//    @Operation(
//            summary = "Добавить товар",
//            description = "Позволяет добавить новый товар в базу данных"
//    )
//    @PostMapping("/product")
//    public String create(@RequestBody @Parameter(description = "Описание нового товара") Product product) {
//        return productService.createClient(product);
//    }
//
//    @Operation(
//            summary = "Обновить цену товара",
//            description = "Позволяет обновить цену определенного товара в базе данных по его id"
//    )
//    @PutMapping("/product/{productId}/{newPrice}")
//    public String update(@PathVariable @Parameter(description = "Идентификатор товара") Long productId,
//    @PathVariable @Parameter(description = "Новая цена") BigDecimal newPrice) {
//        return productService.update(productId, newPrice);
//    }
}
