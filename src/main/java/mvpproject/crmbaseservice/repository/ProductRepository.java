package mvpproject.crmbaseservice.repository;

import mvpproject.crmbaseservice.entity.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
