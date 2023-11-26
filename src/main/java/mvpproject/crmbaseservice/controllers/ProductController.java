package mvpproject.crmbaseservice.controllers;

import mvpproject.crmbaseservice.entities.Product;
import mvpproject.crmbaseservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class ProductController {

    @Autowired
    final private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product")
    public String getProducts() {
        return productService.getProduct();
    }

    @GetMapping("/product/{productId}")
    public String getProduct(@PathVariable("productId") Long productId) {
        return productService.getProductById(productId);
    }

    @GetMapping("/product/'{productName}'")
    public String getProduct(@PathVariable("productName") String productName) {
        return productService.getProductByName(productName);
    }

    @PostMapping("/product")
    public String create(@RequestBody Product product) {
        return productService.createClient(product);
    }

    @PutMapping("/product/{productId}/{newPrice}")
    public String update(@PathVariable Long productId, @PathVariable BigDecimal newPrice) {
        return productService.update(productId, newPrice);
    }

    @DeleteMapping("/product/{productId}")
    public void deleteById(@PathVariable Long productId) {
        productService.deleteById(productId);
    }
}