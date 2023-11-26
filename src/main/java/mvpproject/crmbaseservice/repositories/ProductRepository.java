package mvpproject.crmbaseservice.repositories;

import mvpproject.crmbaseservice.entities.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
