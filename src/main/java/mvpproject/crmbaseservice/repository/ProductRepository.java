package mvpproject.crmbaseservice.repository;

import mvpproject.crmbaseservice.model.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
