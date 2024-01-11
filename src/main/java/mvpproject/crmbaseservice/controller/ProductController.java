package mvpproject.crmbaseservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mvpproject.crmbaseservice.model.dto.ProductDTO;
import mvpproject.crmbaseservice.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
@Tag(name = "Product controller", description = "Содержит эндпойнты для работы с товаром")
public class ProductController {

    private final ProductService productService;

    @Operation(
            summary = "Добавить товар",
            description = "Позволяет добавить новый товар в базу данных"
    )
    @PostMapping("/new")
    public ResponseEntity<Optional<ProductDTO>> create(@Parameter(description = "Описание нового товара")
                                                       @RequestBody ProductDTO product) {
        return ResponseEntity.ok(productService.create(product));
    }

    @Operation(
            summary = "Прочитать все товары",
            description = "Позволяет получить список всех товаров из базы данных"
    )
    @GetMapping("/list")
    public ResponseEntity<List<ProductDTO>> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

    @Operation(
            summary = "Прочитать товар по id",
            description = "Позволяет получить описание определенного товара по его id"
    )
    @GetMapping("{id}")
    public ResponseEntity<ProductDTO> getClient(@PathVariable Long id) {
        return productService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Обновить цену товара",
            description = "Позволяет обновить цену определенного товара в базе данных по его id"
    )
    @PutMapping("{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @RequestBody ProductDTO updateProduct) {
        return productService.update(id, updateProduct)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}