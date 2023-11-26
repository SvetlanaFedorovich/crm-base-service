package mvpproject.crmbaseservice.services;

import mvpproject.crmbaseservice.entities.Product;
import mvpproject.crmbaseservice.repositories.ProductRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepositoryImpl productRepositoryImpl;

    public String getProduct() {
        return productRepositoryImpl.readAll();
    }

    public String getProductById(Long id) {
        String response = null;
        Optional<Product> byId = productRepositoryImpl.findById(id);
        if (byId.isEmpty()) {
            response = "Product ID does not exist";
        } else {
            response = "The client by id is found.";
        }
        return response;
    }

    public String getProductByName(String productName) {
        return productRepositoryImpl.findByName(productName);
    }

    public String createClient(Product product) {
        return productRepositoryImpl.create(product);
    }

    public String update(Long id, BigDecimal newPrice) {
        return productRepositoryImpl.updateById(id, newPrice);
    }

    public void deleteById(Long id) {
        productRepositoryImpl.deleteById(id);
    }
}
